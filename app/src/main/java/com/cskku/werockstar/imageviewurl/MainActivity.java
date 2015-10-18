package com.cskku.werockstar.imageviewurl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ImageView imgJava;
    private ImageView imgPicasso;
    private ImageView imgGlide;

    String url = "https://snap-photos.s3.amazonaws.com/img-thumbs/960w/KZ5LJ0LTNN.jpg";
    private Button btnLoad;
    private Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialView();

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        new LoadImage().execute(url);
                        picassoLoad();
                        glideLoad();
                    }
                });

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgJava.setImageResource(R.mipmap.ic_launcher);
                imgPicasso.setImageResource(R.mipmap.ic_launcher);
                imgGlide.setImageResource(R.mipmap.ic_launcher);
            }
        });
    }

    void initialView() {
        imgGlide = (ImageView) findViewById(R.id.imgGlide);
        imgJava = (ImageView) findViewById(R.id.imgJava);
        imgPicasso = (ImageView) findViewById(R.id.imgPicasso);

        btnLoad = (Button) findViewById(R.id.btnLoad);
        btnReset = (Button) findViewById(R.id.btnReset);
    }

    void picassoLoad() {
        Picasso.with(getApplicationContext()).load(url).into(imgPicasso);
    }

    void glideLoad() {
        Glide.with(getApplicationContext()).load(url).into(imgGlide);
    }

    class LoadImage extends AsyncTask<String, String, Bitmap> {

        Bitmap bitmap;

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imgJava.setImageBitmap(bitmap);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage().toString());
            }
            return bitmap;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}

