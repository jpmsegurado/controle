package com.autzon.controledeestoque.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.autzon.controledeestoque.R;

/**
 * Created by JP on 27/05/2015.
 */
public class TransacaoAdapter extends CursorAdapter {

    public TransacaoAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inf.inflate(R.layout.item_transacao,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        if(view != null){
            String id_prod = cursor.getString(cursor.getColumnIndex("id_produto"));
            String tipo = cursor.getString(cursor.getColumnIndex("tipo"));
            String data_hora = cursor.getString(cursor.getColumnIndex("data_hora"));
            String qtd = cursor.getString(cursor.getColumnIndex("qtd"));



            TextView txv1,txv2,txv3,txv4;

            txv1 = (TextView)view.findViewById(R.id.txv1);
            txv2 = (TextView)view.findViewById(R.id.txv2);
            txv3 = (TextView)view.findViewById(R.id.txv3);
            txv4 = (TextView)view.findViewById(R.id.txv4);

            String dia,ano,mes;

            ano = data_hora.substring(0,4);
            mes = data_hora.substring(5,7);
            dia = data_hora.substring(8,10);
            txv2.setText(dia+"/"+mes+"/"+ano+" "+data_hora.substring(10));

            String[] nomes = context.getResources().getStringArray(R.array.produtos_nome);

            txv3.setText(nomes[Integer.valueOf(id_prod) - 1 ]);

            txv4.setText(qtd+" unidades");

            if(tipo.equals("1")){
                try {
                    txv1.setTextColor(Color.parseColor("#12D65F"));
                    txv1.setText("Entrada");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else{
                try {
                    txv1.setTextColor(Color.parseColor("#D61D12"));
                    txv1.setText("Saída");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

    }
}
