package com.example.administrator.cropimageinside;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.cropimageinside.filter.GPUImageFilter;
import com.example.administrator.cropimageinside.gpuImageTools.GPUImage;
import com.example.administrator.cropimageinside.gpuImageTools.GPUImageView;
import com.example.administrator.cropimageinside.gpuImageTools.ImageFilterTools;

import java.util.ArrayList;

public class FilterActivity extends Activity {
    private GPUImageView mGPUImageView;
    private RecyclerView recyclerView;
    private TextView textView;

    private GPUImageFilter mFilter;
    private ArrayList<Filter> list = new ArrayList<Filter>();
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        mGPUImageView = (GPUImageView) findViewById(R.id.gpuimage);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        Intent intent = getIntent();
        if (intent.getAction().equals("new_file")) {
            String path = intent.getStringExtra("path");
             bitmap = BitmapFactory.decodeFile(path);
            mGPUImageView.setScaleType(GPUImage.ScaleType.CENTER_INSIDE);
            mGPUImageView.setImage(bitmap);

        }
        initList();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter( new RecyclerAdatper());
    }

    private void initList(){
        list.add(new Filter("NORMAL", null));
        list.add(new Filter("GRAYSCALE", ImageFilterTools.FilterType.GRAYSCALE));
        list.add(new Filter("SEPIA", ImageFilterTools.FilterType.SEPIA));
        list.add(new Filter("VIGNETTE", ImageFilterTools.FilterType.VIGNETTE));
        list.add(new Filter("TONE_CURVE", ImageFilterTools.FilterType.TONE_CURVE));
        list.add(new Filter("SKETCH", ImageFilterTools.FilterType.SKETCH));
        list.add(new Filter("SMOOTH_TOON", ImageFilterTools.FilterType.SMOOTH_TOON));
        list.add(new Filter("INVERT", ImageFilterTools.FilterType.INVERT));
        list.add(new Filter("FALSE_COLOR", ImageFilterTools.FilterType.FALSE_COLOR));
        list.add(new Filter("KUWAHARA", ImageFilterTools.FilterType.KUWAHARA));
    }


    class Filter{
        String name;
        ImageFilterTools.FilterType type;

        public Filter(String name, ImageFilterTools.FilterType type) {
            this.name = name;
            this.type = type;
        }
    }




    class RecyclerAdatper extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.filter_item_layout, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int i) {

            final Filter filter = list.get(i);
            viewHolder.mTextView.setText(filter.name);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GPUImageFilter imageFilter;
                    if (i == 0){
                         imageFilter = new GPUImageFilter();

                    }else {
                         imageFilter = ImageFilterTools.createFilterForType(FilterActivity.this,filter.type);
                    }


                    switchFilterTo(imageFilter);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }


    }



    class ViewHolder
            extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ImageView mImageView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.name);
            mImageView = (ImageView) v.findViewById(R.id.pic);
        }
    }


    private void switchFilterTo(final GPUImageFilter filter) {
        if (mFilter == null
                || (filter != null && !mFilter.getClass().equals(filter.getClass()))) {
            mFilter = filter;
            mGPUImageView.setFilter(mFilter);
            mGPUImageView.requestRender();
        }
    }



}
