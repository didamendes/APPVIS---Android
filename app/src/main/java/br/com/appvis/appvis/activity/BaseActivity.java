package br.com.appvis.appvis.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.appvis.appvis.R;
import br.com.appvis.appvis.fragments.ProdutosFragment;
import br.com.appvis.appvis.fragments.ProdutosTabFragment;


public class BaseActivity extends AppCompatActivity {
    protected DrawerLayout drawerLayout;

    // Configura a Toolbar
    protected void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    // Configura o Nav Drawer
    protected void setupNavDrawer() {
        // Drawer Layout
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Ícone do menu do nav drawer
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            if (navigationView != null && drawerLayout != null) {
                // Atualiza os dados do header do Navigation View
                setNavViewValues(navigationView, R.string.nav_drawer_username,
                        R.string.nav_drawer_email, R.mipmap.ic_launcher);

                // Trata o evento de clique no menu.
                navigationView.setNavigationItemSelectedListener(
                        new NavigationView.OnNavigationItemSelectedListener() {
                            @Override
                            public boolean onNavigationItemSelected(MenuItem menuItem) {
                                // Seleciona a linha
                                menuItem.setChecked(true);
                                // Fecha o menu
                                drawerLayout.closeDrawers();
                                // Trata o evento do menu
                                onNavDrawerItemSelected(menuItem);
                                return true;
                            }
                        });
            }
        }
    }

    // Atualiza os dados do header do Navigation View
    public static void setNavViewValues(NavigationView navView, int nome, int email, int img) {
        View headerView = navView.getHeaderView(0);
        TextView tNome = (TextView) headerView.findViewById(R.id.tNome);
        TextView tEmail = (TextView) headerView.findViewById(R.id.tEmail);
        ImageView imgView = (ImageView) headerView.findViewById(R.id.img);
        tNome.setText(nome);
        tEmail.setText(email);
        imgView.setImageResource(img);
    }

    // Trata o evento do menu lateral
    private void onNavDrawerItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_item_produtos_todos:

                break;
            case R.id.nav_item_produtos_alimentos:
                Intent intent = new Intent(getBaseContext(), ProdutosActivity.class);
                intent.putExtra("categoria", R.string.alimentos);
                startActivity(intent);
                break;
            case R.id.nav_item_produtos_bebidas:
                intent = new Intent(getBaseContext(), ProdutosActivity.class);
                intent.putExtra("categoria", R.string.bebidas);
                startActivity(intent);
                break;
            case R.id.nav_item_produtos_carnes:
                intent = new Intent(getBaseContext(), ProdutosActivity.class);
                intent.putExtra("categoria", R.string.carnes);
                startActivity(intent);
                break;
            case R.id.nav_item_produtos_frios:
                intent = new Intent(getBaseContext(), ProdutosActivity.class);
                intent.putExtra("categoria", R.string.frios);
                startActivity(intent);
                break;
            case R.id.nav_item_produtos_frutas:
                intent = new Intent(getBaseContext(), ProdutosActivity.class);
                intent.putExtra("categoria", R.string.frutas);
                startActivity(intent);
                break;
            case R.id.nav_item_produtos_higiene:
                intent = new Intent(getBaseContext(), ProdutosActivity.class);
                intent.putExtra("categoria", R.string.higiene);
                startActivity(intent);
                break;
            case R.id.nav_item_produtos_limpeza:
                intent = new Intent(getBaseContext(), ProdutosActivity.class);
                intent.putExtra("categoria", R.string.limpeza);
                startActivity(intent);
                break;
            case R.id.nav_item_produtos_padaria:
                intent = new Intent(getBaseContext(), ProdutosActivity.class);
                intent.putExtra("categoria", R.string.padaria);
                startActivity(intent);
                break;
            case R.id.nav_item_produtos_outros:
                intent = new Intent(getBaseContext(), ProdutosActivity.class);
                intent.putExtra("categoria", R.string.outros);
                startActivity(intent);
                break;
        }
    }


    // Adiciona o fragment no centro da tela
    protected void replaceFragment(Fragment frag) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Trata o clique no botão que abre o menu.
                if (drawerLayout != null) {
                    openDrawer();
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    // Abre o menu lateral
    protected void openDrawer() {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    // Fecha o menu lateral
    protected void closeDrawer() {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
}

