package com.zerga.carlos.desafiobelatrix;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.os.AsyncTask.Status.FINISHED;
import static android.os.AsyncTask.Status.RUNNING;


/**
 * @author Carlos Zerga <zerga.morales.carlos@gmail.com>
 * 2017-03-27
 *  Places or sites where I went to get some knowledge to solve the problem
 * https://github.com/vad-zuev/ImageDownloader
 * http://www.javaworld.com/article/2074938/core-java/too-many-parameters-in-java-methods-part-3-builder-pattern.html
 * https://jlordiales.me/2012/12/13/the-builder-pattern-in-practice/
 * https://github.com/square/picasso
 */
class ImageLoader extends AsyncTask<String, Void, Bitmap>{

    private static final String INTERNET_PERMISSION= "android.permission.INTERNET";

    private  ImageView imageView;

    private Context context;

    private Exception exception;

    @SuppressLint("StaticFieldLeak")
    private static ImageLoader singleton = null;

    private ImageLoader(ImageView imageView, Context context) {

        this.imageView=imageView;
        this.context=context;
    }

    private void showToast(String message)
    {
        Toast.makeText(this.context,message,Toast.LENGTH_SHORT).show();
    }

    private boolean isInternetPermissionGranted()
    {
        return this.context.checkCallingOrSelfPermission(INTERNET_PERMISSION)==PERMISSION_GRANTED;
    }
    @Override
    protected Bitmap doInBackground(String... params) {

        Bitmap bm = null;
        if(!this.isInternetPermissionGranted())
        {
            this.exception=new InternetPermission("You don't have INTERNET permission");
        }else{
            try {
                URL cleanUrl = new URL(params[0]);
                URLConnection connection = cleanUrl.openConnection();
                Log.d("d",connection.getContentType());
                connection.connect();
                String type=connection.getContentType();
                if(!type.split("/")[0].equals("image"))
                {
                    throw new InvalidUrl("File inside URI doesn't have image type");
                }
                InputStream inputStream = connection.getInputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                bm = BitmapFactory.decodeStream(bufferedInputStream);
                bufferedInputStream.close();
                inputStream.close();
            } catch (IOException | InvalidUrl e) {
                this.exception=e;
            }
        }
        return bm;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(this.exception!=null)
        {
            this.showToast(this.exception.getMessage());
        }
        else if(bitmap==null){
            this.showToast("There is no file to be downloaded");
        }else{
            this.imageView.setImageBitmap(bitmap);
        }
    }

    void load(String url)
    {
        synchronized (ImageLoader.class){
            if(this.getStatus()!=RUNNING)
            {
                this.execute(url);
            }
        }
    }

    public static ImageLoader with(ImageView imageView)
    {
        if (imageView == null) {
            throw new IllegalArgumentException("ImageView can't null");
        }
        synchronized (ImageLoader.class)
        {
            if (singleton == null || singleton.getStatus() == FINISHED) {
                singleton = new Builder(imageView).build();
            }
        }
        return  singleton;
    }

    private static class Builder {

        private ImageView imageView;


        Builder(ImageView imageView)
        {
            this.imageView=imageView;
        }

        ImageLoader build()  {
            return new ImageLoader(this.imageView,this.imageView.getContext().getApplicationContext());
        }
    }

    private class InvalidUrl extends Exception{
        InvalidUrl(String s) {
            super(s);
        }
    }

    private class InternetPermission extends Exception {
        InternetPermission(String s) {
            super(s);
        }
    }
}