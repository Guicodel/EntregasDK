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

public class CustomAdapterOrder extends BaseAdapter implements LoadImage {
    private Context CONTEXT;
    private ArrayList<Order> LIST;
    public CustomAdapterOrder(Context context,ArrayList<Order> list)
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
            convertView=data.inflate(R.layout.order_item_detail,null);
        }
        TextView title=(TextView)convertView.findViewById(R.id.tittle_menu_addIt);
        TextView price=(TextView)convertView.findViewById(R.id.unity_cost_menu_addit);
        TextView quantity=(TextView)convertView.findViewById(R.id.quantity_menu_addit);
        TextView payment=(TextView)convertView.findViewById(R.id.payment_menuAddit);

        title.setText(this.LIST.get(position).getMenuName());
        price.setText(this.LIST.get(position).getUnityCost());
        quantity.setText(this.LIST.get(position).getQuantity());
        payment.setText(this.LIST.get(position).getPayment());
        return convertView;
    }

    @Override
    public void setLoadImage(ImageView container, Bitmap img) {
        container.setImageBitmap(img);
    }
}
