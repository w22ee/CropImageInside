package com.example.administrator.cropimageinside;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CutImgActivity extends Activity {

    private ClipImageLayout mClipImageLayout;
    private Button cropButton;
    private File tempFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mClipImageLayout = (ClipImageLayout) findViewById(R.id.id_clipImageLayout);
        cropButton = (Button) findViewById(R.id.cutButton);
        cropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = mClipImageLayout.clip();
                cropButton.setClickable(false);
                BaseSaveTask baseSaveTask = new BaseSaveTask();
                baseSaveTask.execute(bitmap);
            }
        });
    }


    class BaseSaveTask extends AsyncTask<Bitmap,Void,Void> {
        String path;
        File tempFile;

        @Override
        protected Void doInBackground(Bitmap... bitmaps) {
            savePic(bitmaps[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            path = tempFile.getAbsolutePath();

            Intent intent = new Intent();
            intent.setAction("new_file");
            intent.putExtra("path", path);
            intent.setClass(CutImgActivity.this, FilterActivity.class);
            cropButton.setClickable(true);
            startActivity(intent);
        }


        private void savePic(Bitmap bitmap) {
            tempFile = new File(Environment.getExternalStorageDirectory(), "cut_image.jpg");
            if (tempFile.exists()) {
                tempFile.delete();
            }
            if (bitmap == null){
                System.out.println("bitmap null");
            }
            FileOutputStream fOut = null;
            try {
                tempFile.createNewFile();
                fOut = new FileOutputStream(tempFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, fOut);
            try {
                fOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
