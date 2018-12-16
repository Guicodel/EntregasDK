package com.example.guicode.entregasdk.DataSources;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guicode.entregasdk.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter implements LoadImage{
    private Context CONTEXT;
    private ArrayList<RestaurantList> LIST;
    public CustomAdapter(Context context,ArrayList<RestaurantList> list){
        this.CONTEXT=context;
        this.LIST=list;
    }

    @Override
    public int getCount() {
        return this.LIST.size();
    }

    @Override
    public Object getItem(int position) {
        return this.LIST.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            LayoutInflater data=(LayoutInflater)this.CONTEXT.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=data.inflate(R.layout.restaurant_item_layout,null);
        }
        TextView name=(TextView)convertView.findViewById(R.id.restaurant_name_api_layout);
        TextView phone=(TextView)convertView.findViewById(R.id.restaurant_phone_api_layout);
        TextView nit=(TextView)convertView.findViewById(R.id.restaurant_nit_api_layout);
        TextView street=(TextView)convertView.findViewById(R.id.restaurant_street_api_layout);


        phone.setText(this.LIST.get(position).getPhone());
        nit.setText(this.LIST.get(position).getNit());
        street.setText(this.LIST.get(position).getStreet());
        name.setText(this.LIST.get(position).getName());
        ImageView img = (ImageView)convertView.findViewById(R.id.ImageRestaurant_layout);
        ThreadImage threadImage = new ThreadImage();
        threadImage.setLoadImage(img,this);
        threadImage.execute(this.LIST.get(position).getImage());
        return convertView;
    }

    @Override
    public void setLoadImage(ImageView container, Bitmap img) {
        container.setImageBitmap(img);
    }
}
