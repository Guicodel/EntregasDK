package com.example.guicode.entregasdk.DataSources;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ThreadImage extends AsyncTask<String,String,Bitmap> {
     private LoadImage IMG;
     private ImageView CONTAINER;
      public void setLoadImage(ImageView container,LoadImage img)
      {
          IMG=img;
          CONTAINER=container;
      }
    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            InputStream stream = url.openConnection().getInputStream();
            Bitmap imageBitmap = BitmapFactory.decodeStream(stream);
            return imageBitmap;
            //img.setImageBitmap(imageBitmap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(Bitmap result)
    {
        IMG.setLoadImage(CONTAINER,result);
    }
}
