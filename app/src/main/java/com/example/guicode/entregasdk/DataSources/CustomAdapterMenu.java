package com.example.guicode.entregasdk.DataSources;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guicode.entregasdk.R;

import java.util.ArrayList;

public class CustomAdapterMenu extends BaseAdapter implements LoadImage
{
    private Context CONTEXT;
    private ArrayList<MenuItem> LIST;
    public CustomAdapterMenu(Context context,ArrayList<MenuItem> list)
    {
        this.CONTEXT = context;
        this.LIST = list;
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
            convertView=data.inflate(R.layout.menu_item_layout,null);
        }
        TextView name=(TextView)convertView.findViewById(R.id.menu_name_api);
        TextView price=(TextView)convertView.findViewById(R.id.menu_precio_api);
        TextView description=(TextView)convertView.findViewById(R.id.menu_description_api);
        ImageView img = (ImageView)convertView.findViewById(R.id.image_menu_api);

        name.setText(this.LIST.get(position).getName());
        price.setText(this.LIST.get(position).getPrice());
        description.setText(this.LIST.get(position).getDescription());

        ThreadImage threadImage = new ThreadImage();
        threadImage.setLoadImage(img,this);
        threadImage.execute(this.LIST.get(position).getMenuPhoto());

        return convertView;
    }

    @Override
    public void setLoadImage(ImageView container, Bitmap img) {
        container.setImageBitmap(img);
    }
}
