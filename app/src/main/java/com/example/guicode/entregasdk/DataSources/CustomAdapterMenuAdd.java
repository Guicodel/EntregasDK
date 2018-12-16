package com.example.guicode.entregasdk.DataSources;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guicode.entregasdk.DashBoard;
import com.example.guicode.entregasdk.MenuRegisterActivity;
import com.example.guicode.entregasdk.OrderActivity;
import com.example.guicode.entregasdk.OwnerActivity;
import com.example.guicode.entregasdk.R;

import org.json.JSONException;

import java.util.ArrayList;

public class CustomAdapterMenuAdd extends BaseAdapter implements LoadImage
{
    private Context CONTEXT;
    private ArrayList<MenuItem> LIST;
    public CustomAdapterMenuAdd(Context context,ArrayList<MenuItem> list)
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            LayoutInflater data=(LayoutInflater)this.CONTEXT.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=data.inflate(R.layout.item_menu_customer,null);
        }
        final TextView name=(TextView)convertView.findViewById(R.id.name_menu);
        TextView price=(TextView)convertView.findViewById(R.id.precio_menu);
        ImageView img = (ImageView)convertView.findViewById(R.id.imageView_menu_add);

        name.setText(this.LIST.get(position).getName());
        price.setText(this.LIST.get(position).getPrice());
        //String customerId = CONTEXT.getIntent().getExtras().getString("customerId");

        Button addMenu = (Button)convertView.findViewById(R.id.button_add_menu);
        addMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CONTEXT,"Cargando datos ",Toast.LENGTH_SHORT).show();
                AlertDialog alertDialog = new AlertDialog.Builder(CONTEXT).create();
                //Toast.makeText(MenuRegisterActivity.this, "foto valida", Toast.LENGTH_LONG).show();

                String menuName;

                menuName = LIST.get(position).getName();
                String menuId = LIST.get(position).getID();
                String unityCost = LIST.get(position).getPrice();
                Intent objet = new Intent(CONTEXT, OrderActivity.class);
                objet.putExtra("menuId",menuId);
                objet.putExtra("menuName",menuName);
                objet.putExtra("unityCost",unityCost);
                CONTEXT.startActivity(objet);

            }
        });

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
