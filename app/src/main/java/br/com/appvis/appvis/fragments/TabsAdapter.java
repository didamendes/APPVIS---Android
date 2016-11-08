package br.com.appvis.appvis.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.appvis.appvis.R;

/**
 * Created by Diogo on 08/09/2016.
 */
public class TabsAdapter extends FragmentPagerAdapter {

    private Context context;

    public TabsAdapter(Context context, FragmentManager fm){
        super(fm);
        this.context = context;
    }

    @Override
    public CharSequence getPageTitle(int position){
        if (position == 0){
            return context.getString(R.string.alimentos);
        } else if (position == 1){
            return context.getString(R.string.bebidas);
        } else if (position == 2){
            return context.getString(R.string.carnes);
        } else if (position == 3){
            return context.getString(R.string.frios);
        } else if (position == 4){
            return context.getString(R.string.frutas);
        } else if (position == 5){
            return context.getString(R.string.higiene);
        } else if (position == 6){
            return context.getString(R.string.limpeza);
        } else if (position == 7){
            return context.getString(R.string.padaria);
        }

        return context.getString(R.string.outros);

    }

    @Override
    public Fragment getItem(int position) {

        Fragment f = null;

        if(position == 0){
            f = ProdutosFragment.newInstance(R.string.alimentos);
        } else if(position == 1){
            f = ProdutosFragment.newInstance(R.string.bebidas);
        } else if(position == 2){
            f = ProdutosFragment.newInstance(R.string.carnes);
        } else if(position == 3){
            f = ProdutosFragment.newInstance(R.string.frios);
        } else if(position == 4){
            f = ProdutosFragment.newInstance(R.string.frutas);
        } else if(position == 5){
            f = ProdutosFragment.newInstance(R.string.higiene);
        } else if(position == 6){
            f = ProdutosFragment.newInstance(R.string.limpeza);
        } else if(position == 7){
            f = ProdutosFragment.newInstance(R.string.padaria);
        } else {
            f = ProdutosFragment.newInstance(R.string.outros);
        }

        return f;

    }

    @Override
    public int getCount() {
        return 9;
    }
}
