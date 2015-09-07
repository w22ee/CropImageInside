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

import com.example.administrator.cropimageinside.filter.GPUImageColorInvertFilter;
import com.example.administrator.cropimageinside.filter.GPUImageFilter;

import java.util.HashMap;

public class FilterActivity extends Activity {
    private GPUImageView mGPUImageView;
    private RecyclerView recyclerView;

    private GPUImageFilter mFilter;
    private String[] names = { "lomo", "张柏芝", "张敏", "巩俐", "黄圣依", "赵薇", "莫文蔚", "如花" };
    private HashMap<String,Enum> hashMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        mGPUImageView = (GPUImageView) findViewById(R.id.gpuimage);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        Intent intent = getIntent();
        if (intent.getAction().equals("new_file")) {
            String path = intent.getStringExtra("path");
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            mGPUImageView.setScaleType(GPUImage.ScaleType.CENTER_INSIDE);
            mGPUImageView.setImage(bitmap);

        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter( new RecyclerAdatper());
    }


    class RecyclerAdatper extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.filter_item_layout, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {

            viewHolder.mTextView.setText(names[i]);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    GPUImageColorInvertFilter colorInvertFilter = new GPUImageColorInvertFilter();
                    switchFilterTo(colorInvertFilter);
                }
            });
        }

        @Override
        public int getItemCount() {
            return names.length;
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
