package br.com.appvis.appvis.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.List;

import br.com.appvis.appvis.activity.InsereDado;
import br.com.appvis.appvis.R;

import br.com.appvis.appvis.activity.ProdutoActivity;
import br.com.appvis.appvis.adapter.ProdutoAdapter;
import br.com.appvis.appvis.domain.Produto;
import br.com.appvis.appvis.domain.ProdutoService;


public class ProdutosFragment extends Fragment {

    private int categoria;
    protected RecyclerView recyclerView;
    private List<Produto> produtos;

    public static ProdutosFragment newInstance(int categoria){
        Bundle args = new Bundle();
        args.putInt("categoria", categoria);
        ProdutosFragment f = new ProdutosFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            this.categoria = getArguments().getInt("categoria");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_produtos, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        taskCarros();
    }

    private void taskCarros(){
        try {
            //Busca os produtos pelo tipo
            this.produtos = ProdutoService.getProdutos(getContext(), categoria);
            //Atualiza a lista
            recyclerView.setAdapter(new ProdutoAdapter(getContext(), produtos, onClickProduto()));
        } catch (IOException e) {
            Log.e("livro", e.getMessage(), e);
        }
    }

    private ProdutoAdapter.ProdutoOnClickListener onClickProduto(){
        return new ProdutoAdapter.ProdutoOnClickListener() {
            @Override
            public void onClickProduto(View view, int idx) {
                Produto p = produtos.get(idx);
                Intent intent = new Intent(getContext(), ProdutoActivity.class);
                intent.putExtra("produto", Parcels.wrap(p));
                startActivity(intent);
            }
        };
    }


}
