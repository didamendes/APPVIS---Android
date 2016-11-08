package br.com.appvis.appvis.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import br.com.appvis.appvis.R;
import br.com.appvis.appvis.domain.Produto;
import br.com.appvis.appvis.domain.ProdutoDB;

public class InsereDado extends BaseActivity {

    private Produto produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insere_dado);

        setUpToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button botao = (Button)findViewById(R.id.btCadastrar);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Produto produto = new Produto();
                ProdutoDB db = new ProdutoDB(getBaseContext());


                EditText tNome = (EditText)findViewById(R.id.tNome);
                Spinner categoria = (Spinner)findViewById(R.id.categoria);
                EditText codigoBarra = (EditText)findViewById(R.id.tCodigoBarra);
                EditText descricao = (EditText)findViewById(R.id.tDescricao);
                EditText precoVenda = (EditText)findViewById(R.id.tPrecoVenda);
                EditText fornecedor = (EditText)findViewById(R.id.tFornecedor);
                EditText urlfoto = (EditText)findViewById(R.id.tUrlFoto);

                //float precoVendaProduto = Float.parseFloat(pre);


                String categoriaProduto = String.valueOf(categoria.getSelectedItem());


                long codigoBarraProduto = Long.parseLong(String.valueOf(codigoBarra.getText()));
                //if (codigoBarraProduto == null  || String.valueOf(codigoBarra).trim().length() == 0){
                    //codigoBarra.setError("Informe o codigo de barra");
                    //return;
                 //}

                String nomeProduto = tNome.getText().toString();
                if (nomeProduto == null || nomeProduto.trim().length() == 0){
                    tNome.setError("Informe o nome");
                    return;
                }

                String descricaoProduto = descricao.getText().toString();
                if (descricaoProduto == null || descricaoProduto.trim().length() == 0){
                    descricao.setError("Informe a descricao");
                    return;
                }

                //  long codigoBarraProduto = Long.parseLong(String.valueOf(codigoBarra.getText()));


                String pre = precoVenda.getText().toString();
                if (pre == null || pre.trim().length() == 0){
                    precoVenda.setError("Informe o preço");
                    return;
                }

                String forncedorProduto = fornecedor.getText().toString();
                if (forncedorProduto == null || forncedorProduto.trim().length() == 0){
                    fornecedor.setError("Informe o fornecedor");
                    return;
                }

                String UrlFotoProduto = urlfoto.getText().toString();
                if (UrlFotoProduto == null || UrlFotoProduto.trim().length() == 0){
                    urlfoto.setError("Informe a url da imagem do produto");
                    return;
                }


                produto.nome = nomeProduto;
                produto.descricao = descricaoProduto;
                produto.categoria = categoriaProduto;
                produto.codigoBarra = codigoBarraProduto;
                produto.precoVenda = pre;
                produto.fornecedor = forncedorProduto;
                produto.urlProduto = UrlFotoProduto;


                db.save(produto);

                Intent intent = new Intent(getBaseContext(), MainActivityProduto.class);
                startActivity(intent);

            }
        });

    }
}
