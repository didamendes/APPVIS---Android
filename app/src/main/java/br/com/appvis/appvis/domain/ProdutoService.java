package br.com.appvis.appvis.domain;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.appvis.appvis.R;
import livroandroid.lib.utils.FileUtils;
import livroandroid.lib.utils.HttpHelper;
import livroandroid.lib.utils.XMLUtils;

import static br.com.appvis.appvis.R.string.produtos;
//import static com.google.android.gms.analytics.internal.zzy.n;
//import static com.google.android.gms.analytics.internal.zzy.p;
//import static com.google.android.gms.analytics.internal.zzy.t;

/**
 * Created by Diogo on 07/09/2016.
 */
public class ProdutoService {

    private static int categoriaProduto;
    private static final boolean LOG_ON = false;
    private static final String TAG = "ProdutoService";
    private static final String URL = "http://env-9266141.jelasticlw.com.br/Produtos/rest/produtos";

    public static List<Produto> getProdutos(Context context, String categoria) throws IOException{
        List<Produto> produtos = getProdutosFromWebSercice(context, categoria);
        /*List<Produto> produtos = getProdutosFromBanco(context, categoria);
        if(produtos != null && produtos.size() > 0){
            return produtos;
        }
        produtos = getProdutosFromWebSercice(context, categoria);
        */
        return produtos;
    }

    public static List<Produto> getProdutosFromWebSercice(Context context, String categoria) throws IOException {
        String url = categoria != null ? URL + "/categoria/" + categoria : URL;
        HttpHelper http = new HttpHelper();
        http.LOG_ON = true;
        String json = http.doGet(url);
        List<Produto> produtos = parserJSON(context, json);

        if (categoria == "alimentos"){
            categoriaProduto = 2131165291;
        } else if (categoria == "bebidas"){
            categoriaProduto = 2131165295;
        } else if (categoria == "carnes"){
            categoriaProduto = 2131165298;
        } else if (categoria == "frios"){
            categoriaProduto = 2131165314;
        } else if (categoria == "higiene"){
            categoriaProduto = 2131165317;
        } else if (categoria == "limpeza"){
            categoriaProduto = 2131165318;
        } else if (categoria == "padaria"){
            categoriaProduto = 2131165333;
        } else if (categoria == "outros"){
            categoriaProduto = 2131165332;
        } else if (categoria == "frutas"){
            categoriaProduto = 2131165315;
        }


        salvarProdutos(context, categoriaProduto, produtos);
        return produtos;
    }

    private static List<Produto> getProdutosFromBanco(Context context, int categoria) throws IOException {

        ProdutoDB db = new ProdutoDB(context);

        try{
            String categoriaString = getProduto(categoria);
            List<Produto> produtos = db.findAllByTipo(categoriaString);
            Log.d(TAG, "Retornando " + produtos.size() + " produtos [" + categoriaString + "] do banco");
            return  produtos;
        } finally {
            db.close();
        }
    }




    // Salva os produtos no banco de dados
    private static void salvarProdutos(Context context, int categoria, List<Produto> produtos){

        ProdutoDB db = new ProdutoDB(context);

        try {
            // Deleta os produtos antigos pela categoria para limpar o banco
            String categoriaString = getProduto(categoria);
            db.deleteCarrosByCategoria(categoriaString);
            // Salva todos os produtos
            for (Produto p : produtos){
                p.categoria = categoriaString;
                db.save(p);
            }
        } finally {
            db.close();
        }

    }

    private static String getProduto(int categoria) {
        if (categoria == R.string.alimentos) {
            return "Alimentos";
        } else if (categoria == R.string.bebidas) {
            return "Bebidas";
        } else if (categoria == R.string.carnes) {
            return "Carnes";
        } else if (categoria == R.string.frios) {
            return "Frios";
        } else if (categoria == R.string.frutas) {
            return "Frutas";
        } else if (categoria == R.string.higiene) {
            return "Higiene";
        }else if (categoria == R.string.limpeza) {
            return "Limpeza";
        }else if (categoria == R.string.padaria) {
            return "Padaria";
        }
        return "Outros";
    }

    //Faz a leitura do arquivo que está n pasta /res/raw
    private static String readFile(Context context, int categoria) throws IOException{
        if (categoria == R.string.alimentos){
            return FileUtils.readRawFileString(context, R.raw.produtos_alimentos, "UTF-8");
        } else if (categoria == R.string.bebidas){
            return FileUtils.readRawFileString(context, R.raw.produtos_bebidas, "UTF-8");
        } else if (categoria == R.string.carnes){
            return FileUtils.readRawFileString(context, R.raw.produtos_carnes, "UTF-8");
        } else if (categoria == R.string.frios){
            return FileUtils.readRawFileString(context, R.raw.produtos_frios, "UTF-8");
        } else if (categoria == R.string.frutas){
            return FileUtils.readRawFileString(context, R.raw.produtos_frutas, "UTF-8");
        } else if (categoria == R.string.higiene){
            return FileUtils.readRawFileString(context, R.raw.produtos_higiene, "UTF-8");
        } else if (categoria == R.string.limpeza){
            return FileUtils.readRawFileString(context, R.raw.produtos_limpeza, "UTF-8");
        } else if (categoria == R.string.padaria){
            return FileUtils.readRawFileString(context, R.raw.produtos_padaria, "UTF-8");
        }

        return FileUtils.readRawFileString(context, R.raw.produtos_outros, "UTF-8");
    }


    //Faz o parse do JSON e cria a lista de produtos
    private static List<Produto> parserJSON(Context context, String json) throws IOException{

        List<Produto> produtos = new ArrayList<Produto>();
        try{


            JSONArray jsonProdutos = new JSONArray(json);

            //Insere cada produto na lista
            for (int i = 0; i < jsonProdutos.length(); i++){
                JSONObject jsonProduto = jsonProdutos.getJSONObject(i);
                Produto p = new Produto();

                //Le as informações do Produto
                p.nome = jsonProduto.optString("nome");
                p.descricao = jsonProduto.optString("descricao");
                p.codigoBarra = jsonProduto.optLong("codigoBarra");
                p.precoVenda = jsonProduto.optString("precoVenda");
                p.fornecedor = jsonProduto.optString("fornecedor");
                //p.urlProduto = jsonProduto.optString("url_foto");

                if (LOG_ON){
                    Log.d(TAG, "Produto " + p.nome + " > " + p.urlProduto);
                }
                produtos.add(p);
            }
            if (LOG_ON){
                Log.d(TAG, produtos.size() + " encontrado .");
            }

        } catch (JSONException e){
            throw new IOException(e.getMessage(), e);
        }

        return produtos;

    }

    // Faz o parse do XML e cria a lista de produtos
    private static List<Produto> parserXML(Context context, String xml){

        List<Produto> produtos = new ArrayList<Produto>();
        Element root = XMLUtils.getRoot(xml, "UTF-8");

        //Le todas as tags
        List<Node> nodeProdutos = XMLUtils.getChildren(root, "produto");

        //Insere cada produto na lista
        for(Node node : nodeProdutos){
            Produto p = new Produto();

            p.nome = XMLUtils.getText(node, "nome");
            p.descricao = XMLUtils.getText(node, "desc");
            //p.codigoBarra = XMLUtils.getText(node, "codigoBarra");
            p.precoVenda = XMLUtils.getText(node, "precoVenda");
            p.fornecedor = XMLUtils.getText(node, "fornecedor");
            p.urlProduto = XMLUtils.getText(node, "url_foto");

            if (LOG_ON){
                Log.d(TAG, "Produto " + p.nome + " > " + p.urlProduto);
            }
            produtos.add(p);
        }
        if (LOG_ON){
            Log.d(TAG, produtos.size() + " encontrado .");
        }
        return produtos;
    }

}
