package br.com.appvis.appvis.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import br.com.appvis.appvis.R;
import br.com.appvis.appvis.activity.ProdutoActivity;
import br.com.appvis.appvis.domain.Produto;
import br.com.appvis.appvis.domain.ProdutoDB;
import livroandroid.lib.fragment.BaseFragment;


public class ProdutoFragment extends BaseFragment {

    private Produto produto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_produto, container, false);
        produto = Parcels.unwrap(getArguments().getParcelable("produto"));

        setHasOptionsMenu(true); // Contem menu

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        // Atualiza a view do fragment com os dados do produto
        setTextString(R.id.tDesc, produto.descricao);
        setTextString(R.id.tpreVen, produto.precoVenda);
        setTextString(R.id.tCodBar, String.valueOf(produto.codigoBarra));
        //final ImageView imgView = (ImageView) getView().findViewById(R.id.img);
        //Picasso.with(getContext()).load(produto.urlProduto).fit().into(imgView);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_frag_produto, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.action_edit){
            //toast("Editar: " + produto.nome);
            EditarProdutoDialog.show(getFragmentManager(), produto, new EditarProdutoDialog.Callback(){
                @Override
                public void onProdutoUpdated(Produto produto){
                    toast("Produto [" + produto.nome + "] atualizado");

                    // Salva o produto depois de fechar o dialog
                    ProdutoDB db = new ProdutoDB(getContext());
                    db.save(produto);

                    // Atualiza o titulo com o nome nome
                    ProdutoActivity a = (ProdutoActivity) getActivity();
                    a.setTitle(produto.nome);
                    a.setTitle(produto.descricao);
                    a.setTitle(produto.precoVenda);

                }
            });
            return true;
        } else if (item.getItemId() == R.id.action_delete){

            DeletarProdutoDialog.show(getFragmentManager(), new DeletarProdutoDialog.Callback() {
                @Override
                public void onClickYes() {
                    toast("Produto [" + produto.nome + "] deletado");
                    // Deleta o produto
                    ProdutoDB db = new ProdutoDB(getActivity());
                    db.delete(produto);
                    // Fecha a activity;
                    getActivity().finish();
                }
            });

            return true;
        }

        return super.onOptionsItemSelected(item);

    }

}
