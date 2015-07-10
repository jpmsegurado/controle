package com.autzon.controledeestoque.view;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.autzon.controledeestoque.R;
import com.autzon.controledeestoque.adapter.ProdutoAdapter;
import com.autzon.controledeestoque.data.Contract;
import com.autzon.controledeestoque.data.Provider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SaidaFragment extends Fragment {


    public SaidaFragment() {}


    private Spinner spinner;
    private EditText qtd;
    private Button btn;
    public static final Uri TRANSACAO_URL = Uri.parse(Provider.URL+ Contract.transacao);
    private MainActivity main;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entrada,container,false);

        spinner = (Spinner)view.findViewById(R.id.spinner);
        qtd = (EditText)view.findViewById(R.id.qtd);
        btn = (Button)view.findViewById(R.id.button);
        main = (MainActivity) getActivity();
        spinner.setAdapter(new ProdutoAdapter(getActivity()));

        int paddingLeft = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        int paddingRight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 110, getResources().getDisplayMetrics());
        int paddingTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        int paddingBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        qtd.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("tipo","0");
                values.put("qtd",qtd.getText().toString());
                View view = spinner.getSelectedView();
                TextView id = (TextView) view.findViewById(R.id.id);
                values.put("id_produto",id.getText().toString());
                values.put("data_hora",getDateTime());

                Uri uri = main.getContentResolver().insert(TRANSACAO_URL,values);
                Log.d("uri insert", uri.toString());
                if(uri != null){
                    AlertDialog.Builder alert = main.alert("Inserido com sucesso");
                    alert.setPositiveButton("ok",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            main.showTransacoes();
                        }
                    });
                    alert.create().show();
                }else{
                    AlertDialog.Builder alert = main.alert("Erro tente novamente.");
                    alert.setPositiveButton("ok",null);
                    alert.create().show();
                }
            }
        });


        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            MainActivity main = (MainActivity) getActivity();
            main.getSupportActionBar().setTitle("Registrar saída");
        }
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


}
