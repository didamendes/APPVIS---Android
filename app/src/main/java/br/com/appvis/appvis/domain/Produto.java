package br.com.appvis.appvis.domain;


@org.parceler.Parcel
public class Produto {

    private static final long serialVersionUID = 6601006766832473959L;
    public long id;
    public String nome;
    public String categoria;
    public long codigoBarra;
    public String descricao;
    public String precoVenda;
    public String fornecedor;
    public String urlProduto;

    @Override
    public String toString(){
        return "Produto{" + "nome='" + nome + '\'' + '}';
    }

}
