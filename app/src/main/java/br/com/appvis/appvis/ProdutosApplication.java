package br.com.appvis.appvis;

import android.app.Application;
import android.util.Log;

/**
 * Created by Diogo on 07/09/2016.
 */
public class ProdutosApplication extends Application {

    private static final String TAG = "ProdutosApplication";
    private static ProdutosApplication instance = null;

    public static ProdutosApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d(TAG, "ProdutosApplication.onCreate()");
        instance = this;
    }

    @Override
    public void onTerminate(){
        super.onTerminate();
        Log.d(TAG, "ProdutosApplication.onTerminate()");
    }

}
