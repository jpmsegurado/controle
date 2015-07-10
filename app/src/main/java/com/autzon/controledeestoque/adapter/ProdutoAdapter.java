package com.autzon.controledeestoque.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.autzon.controledeestoque.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;


public class ProdutoAdapter extends BaseAdapter {

    private Context ctx;
    private String[] id,nome,foto;

    public ProdutoAdapter(Context ctx){
        this.ctx = ctx;
        this.id = ctx.getResources().getStringArray(R.array.produtos_id);
        this.foto = ctx.getResources().getStringArray(R.array.produtos_foto);
        this.nome = ctx.getResources().getStringArray(R.array.produtos_nome);
    }

    @Override
    public int getCount() {
        return id.length;
    }

    @Override
    public Object getItem(int position) {

        return id[position];
    }

    @Override
    public long getItemId(int position) {
        return Integer.valueOf(id[position]);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ImageLoader loader = ImageLoader.getInstance();

        if(view == null){
            LayoutInflater inf = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.item_produto,parent,false);

            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true).cacheOnDisk(true)
                    .build();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(ctx)
                    .defaultDisplayImageOptions(defaultOptions)
                    .build();
            loader.init(config);

        }

        final ImageView image;
        image = (ImageView)view.findViewById(R.id.image);
        loader.displayImage("assets://"+foto[position],image);

        TextView txv1,txv2,id;

        id = (TextView)view.findViewById(R.id.id);
        txv1 = (TextView)view.findViewById(R.id.txv1);
        txv2 = (TextView)view.findViewById(R.id.txv2);

        txv2.setText("R$2,50");
        txv1.setText(nome[position]);
        id.setText(this.id[position]);


        return view;
    }

    public class Holder{
        ImageView image;
    }
}
