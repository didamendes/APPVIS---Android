package br.com.appvis.appvis.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import br.com.appvis.appvis.R;
import br.com.appvis.appvis.fragments.AboutDialog;
import br.com.appvis.appvis.fragments.TabsAdapter;

public class MainActivityProduto extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_produto);

        setUpToolbar();
        setupNavDrawer();
        setupViewPagerTabs();

        //replaceFragment(new ProdutosTabFragment());

       /* findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityProduto.this, InsereDado.class);
                startActivity(intent);
            }
        });*/


    }

    // Configura as Tabs + ViewPager
    private void setupViewPagerTabs(){
        // ViewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new TabsAdapter(getBaseContext(), getSupportFragmentManager()));

        //Tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        //Cria as tabs com o mesmo adapter utilizado pelo ViewPager
        tabLayout.setupWithViewPager(viewPager);
        int cor = ContextCompat.getColor(getBaseContext(), R.color.white);

        tabLayout.setTabTextColors(cor, cor);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_about){
            AboutDialog.showAbout(getSupportFragmentManager());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
