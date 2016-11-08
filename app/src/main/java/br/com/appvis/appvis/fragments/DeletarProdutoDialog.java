package br.com.appvis.appvis.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Diogo on 12/09/2016.
 */
public class DeletarProdutoDialog extends DialogFragment {

    private Callback callback;

    public interface Callback{
        void onClickYes();
    }

    public static void show(FragmentManager fm, Callback callback){

        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("deletar_produto");

        if (prev != null){
            ft.remove(prev);
        }

        ft.addToBackStack(null);
        DeletarProdutoDialog frag = new DeletarProdutoDialog();
        frag.callback = callback;
        frag.show(ft, "deletar_produto");

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        if (callback != null){
                            // Deletar
                            callback.onClickYes();
                        }
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Deletar esse produto ? ");
        builder.setPositiveButton("Sim", dialogClickListener);
        builder.setNegativeButton("Não", dialogClickListener);

        return builder.create();

    }

}
