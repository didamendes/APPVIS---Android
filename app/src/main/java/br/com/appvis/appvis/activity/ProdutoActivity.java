package br.com.appvis.appvis.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import br.com.appvis.appvis.R;
import br.com.appvis.appvis.domain.Produto;
import br.com.appvis.appvis.fragments.ProdutoFragment;
import br.com.appvis.appvis.fragments.ProdutosFragment;

public class ProdutoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        setUpToolbar();

        Produto p = Parcels.unwrap(getIntent().getParcelableExtra("produto"));
        getSupportActionBar().setTitle(p.nome);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Imagem de header na action bar
        ImageView appBarImg = (ImageView) findViewById(R.id.appBarImg);
        Picasso.with(getBaseContext()).load(p.urlProduto).into(appBarImg);

        // Adiciona o fragment ao layout
        if (savedInstanceState == null){
            ProdutoFragment frag = new ProdutoFragment();
            frag.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction().add(R.id.ProdutoFragment,frag).commit();
        }

    }

}
