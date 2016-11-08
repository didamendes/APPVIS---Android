package br.com.appvis.appvis.fragments;

import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.appvis.appvis.R;
import livroandroid.lib.fragment.BaseFragment;

public class ProdutosTabFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_produtos_tab, container, false);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(8);
        viewPager.setAdapter(new TabsAdapter(getContext(), getChildFragmentManager()));

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);

        tabLayout.setupWithViewPager(viewPager);
        int cor = ContextCompat.getColor(getContext(), R.color.white);

        tabLayout.setTabTextColors(cor, cor);

        return view;

    }

}
