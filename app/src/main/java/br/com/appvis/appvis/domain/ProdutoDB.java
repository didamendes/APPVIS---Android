package br.com.appvis.appvis.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diogo on 11/09/2016.
 */
public class ProdutoDB extends SQLiteOpenHelper {

    private static final String TAG = "sql";

    public static final String NOME_BANCO = "appvis";
    private static final int VERSAO_BANCO = 6;

    public ProdutoDB(Context context){
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Criando a tabela produto");
        db.execSQL("create table if not exists produto (_id integer primary key autoincrement," +
                " nome text not null, categoria text not null, codigoBarra long not null, descricao text not null, precoVenda text not null," +
                " fornecedor text not null, urlProduto text not null);");
        Log.d(TAG, "Tabela produto criada com sucesso");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS produto " );
        onCreate(db);
    }


    // Insere um novo produto, ou atualiza se já existe
    public long save(Produto produto){
        long id = produto.id;
        SQLiteDatabase db = getWritableDatabase();

        try{
            ContentValues values = new ContentValues();
            values.put("nome", produto.nome);
            values.put("descricao", produto.descricao);
            values.put("categoria", produto.categoria);
            values.put("codigoBarra", produto.codigoBarra);
            values.put("precoVenda", produto.precoVenda);
            values.put("fornecedor", produto.fornecedor);
            values.put("urlProduto", produto.urlProduto);

            if (id != 0){
                String _id = String.valueOf(produto.id);
                String[] whereArgs = new String[]{_id};
                // update produto set values = ... where _id=?
                int count = db.update("produto", values, "_id=?", whereArgs);
                return count;
            }else{
                // insert into produto values (...)
                id = db.insert("produto", null, values);
                return id;
            }
        } finally {
            db.close();
        }

    }


    // Deleta o produto
    public int delete(Produto produto){
        SQLiteDatabase db = getWritableDatabase();
        try{
            // delete from produto where _id
            int count = db.delete("produto", "_id=?", new String[]{String.valueOf(produto.id)});
            Log.i(TAG, "Deletou [" + count + "] registro");
            return count;
        } finally {
            db.close();
        }
    }


    // Deleta os produtos do tipo fornecido
    public int deleteCarrosByCategoria(String categoria){
        SQLiteDatabase db = getWritableDatabase();
        try{
            // delete from produto where _id=?
            int count = db.delete("produto", "categoria=?", new String[]{categoria});
            Log.i(TAG, "Deletou [" + count + "] registro");
            return count;
        } finally {
            db.close();
        }
    }


    // Consulta a lista com todos os produtos
    public List<Produto> findAll(){
        SQLiteDatabase db = getWritableDatabase();
        try{
            // select * from produto
            Cursor c = db.query("produto", null, null, null, null, null, null, null);
            return toList(c);
        } finally {
            db.close();
        }
    }


    // Consulta o produto pelo categoria
    public List<Produto> findAllByTipo(String categoria){
        SQLiteDatabase db = getWritableDatabase();
        try{
            // select * from produto where categoria=?
            Cursor c = db.query("produto", null, " categoria= '" + categoria + "'", null, null, null, null);
            return toList(c);
        } finally {
            db.close();
        }
    }



    // Consulta o produto pelo nome
    public List<Produto> findAllByNome(String nome){
        SQLiteDatabase db = getWritableDatabase();
        try{
            // select * from produto where nome=?
            Cursor c = db.query("produto", null, "nome= '" + nome + "'", null, null, null, null);
            return toList(c);
        } finally {
            db.close();
        }
    }


    // Consulta o produto pelo codigoBarra
    public List<Produto> findAllByCodigoBarra(String codigoBarra){
        SQLiteDatabase db = getWritableDatabase();
        try{
            // select * from produto where codigoBarra=?
            Cursor c = db.query("produto", null, "codigoBarra= '" + codigoBarra + "'", null, null, null, null);
            return toList(c);
        } finally {
            db.close();
        }
    }



    // Le o cursos e cria a lista de produtos
    private List<Produto> toList(Cursor c){
        List<Produto> produtos = new ArrayList<Produto>();
        if (c.moveToFirst()){
            do{
                Produto produto = new Produto();
                produtos.add(produto);
                // recupera os atributos de produto
                produto.id = c.getLong(c.getColumnIndex("_id"));
                produto.nome = c.getString(c.getColumnIndex("nome"));
                produto.categoria = c.getString(c.getColumnIndex("categoria"));
                produto.descricao = c.getString(c.getColumnIndex("descricao"));
                produto.codigoBarra = c.getLong(c.getColumnIndex("codigoBarra"));
                produto.precoVenda = c.getString(c.getColumnIndex("precoVenda"));
                produto.fornecedor = c.getString(c.getColumnIndex("fornecedor"));
                produto.urlProduto = c.getString(c.getColumnIndex("urlProduto"));
            } while (c.moveToNext());
        }

        return produtos;
    }


    // Executa um SQL
    public void execSQL(String sql){
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.execSQL(sql);
        } finally {
            db.close();
        }
    }


    // Executa um SQL
    public void execSQL(String sql, Object[] args){
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.execSQL(sql, args);
        } finally {
            db.close();
        }
    }

}
