package br.com.appvis.appvis.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.appvis.appvis.R;
import br.com.appvis.appvis.domain.Produto;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutosViewHolder> {
    protected static final String TAG = "appvis";
    private final List<Produto> produtos;
    private final Context context;
    private ProdutoOnClickListener produtoOnClickListener;

    public ProdutoAdapter(Context context, List<Produto> produtos, ProdutoOnClickListener
            produtoOnClickListener) {
        this.context = context;
        this.produtos = produtos;
        this.produtoOnClickListener = produtoOnClickListener;
    }

    @Override
    public int getItemCount() {
        return this.produtos != null ? this.produtos.size() : 0;
    }

    @Override
    public ProdutosViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_produto_adapter, viewGroup, false);
        ProdutosViewHolder holder = new ProdutosViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ProdutosViewHolder holder, final int position) {
        // Atualiza a view
        Produto p = produtos.get(position);
        holder.tNome.setText(p.nome);
        holder.progress.setVisibility(View.VISIBLE);
        // Faz o download da foto e mostra o ProgressBar
        Picasso.with(context).load(p.urlProduto).fit().into(holder.img,
                new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progress.setVisibility(View.GONE); // download ok
                    }

                    @Override
                    public void onError() {
                        holder.progress.setVisibility(View.GONE);
                    }
                });
        // Click
        if (produtoOnClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // A variável position é final
                    produtoOnClickListener.onClickProduto(holder.itemView, position);
                }
            });

        }



    }

    public interface ProdutoOnClickListener {
        void onClickProduto(View view, int idx);
    }

    // ViewHolder com as views
    public static class ProdutosViewHolder extends RecyclerView.ViewHolder {
        public TextView tNome;
        ImageView img;
        ProgressBar progress;
        CardView cardView;

        public ProdutosViewHolder(View view) {
            super(view);
            // Cria as views para salvar no ViewHolder
            tNome = (TextView) view.findViewById(R.id.text);
            img = (ImageView) view.findViewById(R.id.img);
            progress = (ProgressBar) view.findViewById(R.id.progressImg);
            cardView = (CardView) view.findViewById(R.id.card_view);
        }
    }
}


