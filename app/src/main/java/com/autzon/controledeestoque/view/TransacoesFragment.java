package com.autzon.controledeestoque.view;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.autzon.controledeestoque.R;
import com.autzon.controledeestoque.adapter.TransacaoAdapter;
import com.autzon.controledeestoque.data.Contract;
import com.autzon.controledeestoque.data.Provider;

public class TransacoesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    public TransacoesFragment() {}

    public static final Uri TRANSACAO_URL = Uri.parse(Provider.URL+ Contract.transacao);
    private TextView txv;
    private ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transacoes, container, false);

        txv = (TextView)view.findViewById(R.id.txv);
        list = (ListView)view.findViewById(R.id.list);

        getLoaderManager().initLoader(1,null,this);

        return view;

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if(!hidden){
            getLoaderManager().restartLoader(1,null,this);
            MainActivity main = (MainActivity) getActivity();
            main.getSupportActionBar().setTitle("Todas as transações");
        }


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        switch(id){
            case 1:
                return new CursorLoader(
                        getActivity(),
                        TRANSACAO_URL,
                        null,
                        null,
                        null,
                        "data_hora DESC "
                );
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data.getCount() > 0){
            txv.setVisibility(View.GONE);
            list.setAdapter(new TransacaoAdapter(getActivity(),data,false));
            list.setVisibility(View.VISIBLE);
        }else{
            txv.setVisibility(View.VISIBLE);
            list.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
