package br.com.appvis.appvis.fragments;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.parceler.Parcels;

import br.com.appvis.appvis.R;
import br.com.appvis.appvis.domain.Produto;

public class EditarProdutoDialog extends DialogFragment {

    private Callback callback;
    private Produto produto;
    private TextView tNome;
    private TextView tDescricao;
    private TextView tCodigoBarra;
    private TextView tPrecoVenda;
    private TextView tFornecedor;
    private TextView tUrlFoto;

    //Interface para retornar o resultado
    public interface Callback{
        void onProdutoUpdated(Produto produto);
    }

    // Metodo utilitario para criar o dialog
    public static void show(FragmentManager fm, Produto produto, Callback callback){

        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("editar_produto");

        if (prev != null){
            ft.remove(prev);
        }

        ft.addToBackStack(null);
        EditarProdutoDialog frag = new EditarProdutoDialog();
        frag.callback = callback;
        Bundle args = new Bundle();
        // Passa o objeto produto por parametro
        args.putParcelable("produto", Parcels.wrap(produto));
        frag.setArguments(args);
        frag.show(ft, "editar_produto");

    }

    @Override
    public void onStart(){
        super.onStart();

        if (getDialog() == null){
            return;
        }

        // Atualiza o tamanho do dialog
        int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height);
        getDialog().getWindow().setLayout(width, height);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstamceState){

        View view = inflater.inflate(R.layout.activity_editar_produto_dialog, container, false);
        view.findViewById(R.id.btAtualizar).setOnClickListener(onClickAtualizar());

        tNome = (TextView) view.findViewById(R.id.tNome);
        tDescricao = (TextView) view.findViewById(R.id.tDescricao);
        tCodigoBarra = (TextView) view.findViewById(R.id.tCodigoBarra);
        tPrecoVenda = (TextView) view.findViewById(R.id.tPrecoVenda);
        tFornecedor = (TextView) view.findViewById(R.id.tFornecedor);
        tUrlFoto = (TextView) view.findViewById(R.id.tUrlFoto);

        this.produto = Parcels.unwrap(getArguments().getParcelable("produto"));

        if (produto != null){
            tNome.setText(produto.nome);
            tDescricao.setText(produto.descricao);
            tPrecoVenda.setText(produto.precoVenda);
            tFornecedor.setText(produto.fornecedor);
            tUrlFoto.setText(produto.urlProduto);
        }

        return view;

    }

    private View.OnClickListener onClickAtualizar() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String novoNome = tNome.getText().toString();
                if (novoNome == null || novoNome.trim().length() == 0){
                    tNome.setError("Informe o nome");
                    return;
                }

                String novoDescricao = tDescricao.getText().toString();
                if (novoDescricao == null || novoDescricao.trim().length() == 0){
                    tDescricao.setError("Informe a descricao");
                    return;
                }

                //  long codigoBarraProduto = Long.parseLong(String.valueOf(codigoBarra.getText()));


                String novoPrecoVenda = tPrecoVenda.getText().toString();
                if (novoPrecoVenda == null || novoPrecoVenda.trim().length() == 0){
                    tPrecoVenda.setError("Informe o preço");
                    return;
                }

                String novoFornecedor = tFornecedor.getText().toString();
                if (novoFornecedor == null || novoFornecedor.trim().length() == 0){
                    tFornecedor.setError("Informe o fornecedor");
                    return;
                }

                String novoURLFOTO = tUrlFoto.getText().toString();
                if (novoURLFOTO == null || novoURLFOTO.trim().length() == 0){
                    tUrlFoto.setError("Informe a url da imagem do produto");
                    return;
                }

                Context context = view.getContext();

                // Atualiza o banco de dados
                produto.nome = novoNome;
                produto.descricao = novoDescricao;
                produto.precoVenda = novoPrecoVenda;
                produto.fornecedor = novoFornecedor;
                produto.urlProduto = novoURLFOTO;

                if (callback != null){
                    callback.onProdutoUpdated(produto);
                }

                //Fecha o DIalog
                dismiss();
            }
        };

    }


}
