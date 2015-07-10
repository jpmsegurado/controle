package com.autzon.controledeestoque.view;

import android.app.AlertDialog;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.autzon.controledeestoque.R;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {

    private TransacoesFragment tf;
    private EntradaFragment ef;
    private SaidaFragment sf;
    private FragmentManager fm;
    private DrawerLayout dl;
    private ActionBarDrawerToggle toggle;
    private String[] menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Todas as transações");

        fm = getSupportFragmentManager();
        tf = new TransacoesFragment();
        ef = new EntradaFragment();
        sf = new SaidaFragment();
        FragmentTransaction ft = fm.beginTransaction();

        ft.add(R.id.container,tf);
        ft.add(R.id.container,ef);
        ft.add(R.id.container,sf);

        ft.hide(ef);
        ft.hide(sf);

        ft.commit();

        dl = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this,dl,0,0){
            @Override
            public void onDrawerClosed(View drawerView) {

                super.onDrawerClosed(drawerView);

                if(getFragmentVisivel() == ef){
                    getSupportActionBar().setTitle("Registrar entrada");
                }else if(getFragmentVisivel() == sf){
                    getSupportActionBar().setTitle("Registrar saída");
                }else{
                    getSupportActionBar().setTitle("Todas as transações");
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Menu");
            }
        };

        dl.setDrawerListener(toggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.syncState();



        ListView list = (ListView)findViewById(R.id.list);
        ArrayAdapter<CharSequence> aa = ArrayAdapter.createFromResource(this,R.array.menu,android.R.layout.simple_list_item_1);
        list.setAdapter(aa);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final FragmentTransaction ft = fm.beginTransaction();
                ft.setCustomAnimations(R.animator.fade_up,R.animator.fadeout,R.animator.fade_up,R.animator.fadeout);
                switch(position){
                    case 0:
                        if(tf.isVisible()){
                            return;
                        }
                        ft.show(tf);
                        break;
                    case 1:
                        if(ef.isVisible()){
                            return;
                        }
                        ft.show(ef);
                        break;
                    case 2:
                        if(sf.isVisible()){
                            return;
                        }
                        ft.show(sf);
                        break;
                }
                ft.hide(getFragmentVisivel());
                ft.addToBackStack(null);
                dl.closeDrawers();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ft.commit();
                    }
                },300);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public Fragment getFragmentVisivel(){
        if(ef.isVisible() && tf.isHidden() && sf.isHidden()){
            return ef;
        }else if(sf.isVisible() && tf.isHidden() && ef.isHidden() ){
            return sf;
        }else{
            return tf;
        }
    }

    public AlertDialog.Builder alert(String msg){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(msg);
        return alert;
    }

    public void showTransacoes(){
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.animator.fade_up,R.animator.fadeout,R.animator.fade_up,R.animator.fadeout);
        ft.show(tf);
        ft.hide(getFragmentVisivel());
        ft.commit();
    }

    public TransacoesFragment getTf() {
        return tf;
    }

    public FragmentManager getFm() {
        return fm;
    }

    public EntradaFragment getEf() {
        return ef;
    }

}
