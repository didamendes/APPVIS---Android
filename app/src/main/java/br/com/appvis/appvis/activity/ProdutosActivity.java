package br.com.appvis.appvis.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.appvis.appvis.R;
import br.com.appvis.appvis.fragments.ProdutosFragment;

public class ProdutosActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);
        setUpToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Titulo
        getSupportActionBar().setTitle(getString(getIntent().getIntExtra("categoria", 0)));
        //Adiciona o fragment com o mesmo Bundle
        if (savedInstanceState == null){
            ProdutosFragment frag = new ProdutosFragment();
            frag.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.container, frag).commit();
        }

    }
}
