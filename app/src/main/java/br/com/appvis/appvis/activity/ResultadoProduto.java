package br.com.appvis.appvis.activity;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;

import org.parceler.Parcels;

import java.util.List;
import java.util.Locale;

import br.com.appvis.appvis.R;
import br.com.appvis.appvis.barcode.BarcodeCaptureActivity;
import br.com.appvis.appvis.domain.Produto;
import br.com.appvis.appvis.domain.ProdutoDB;

import static android.R.attr.data;
import static br.com.appvis.appvis.R.id.tCodBar;
import static br.com.appvis.appvis.R.id.tDesc;
import static br.com.appvis.appvis.R.id.tNome;
import static br.com.appvis.appvis.R.id.tNomeProduto;
import static br.com.appvis.appvis.R.id.tpreVen;
import static br.com.appvis.appvis.R.string.produtos;
//import static com.google.android.gms.analytics.internal.zzy.i;
//import static com.google.android.gms.analytics.internal.zzy.t;

public class ResultadoProduto extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private String codigo;
    private Produto produto;
    private TextToSpeech ttsResultado;
    String nomeProdudo;
    String descProduto;
    String precoProduto;
    String codigoProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_produto);

        Bundle args = new Bundle();
        args.putParcelable("produto", Parcels.wrap(produto));

        ProdutoDB db = new ProdutoDB(getBaseContext());


        codigo = this.getIntent().getStringExtra("codigo");

        ttsResultado = new TextToSpeech(this, this);


            List<Produto> listProduto = db.findAllByCodigoBarra(codigo);

            if(listProduto.size() == 0){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }else {

                TextView tNomeProduto = (TextView) findViewById(R.id.tNomeProduto);
                tNomeProduto.setText(listProduto.get(0).nome);

                ttsResultado.speak(String.valueOf(tNomeProduto), TextToSpeech.QUEUE_FLUSH, null);
                TextView tDesc = (TextView) findViewById(R.id.tDesc);
                nomeProdudo = tNomeProduto.getText().toString();
                tDesc.setText(listProduto.get(0).descricao);
                descProduto = tDesc.getText().toString();
                TextView tpreVen = (TextView) findViewById(R.id.tpreVen);
                tpreVen.setText(listProduto.get(0).precoVenda);
                precoProduto = tpreVen.getText().toString();
                TextView tCodBar = (TextView) findViewById(R.id.tCodBar);
                tCodBar.setText(listProduto.get(0).fornecedor );
                codigoProduto = tCodBar.getText().toString();

            }
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS) {
            Locale locale = new Locale("pt", "BR");
            ttsResultado.setLanguage(locale);
            ttsResultado.speak("Nome do Produto ", TextToSpeech.QUEUE_FLUSH, null);
            ttsResultado.speak(nomeProdudo, TextToSpeech.QUEUE_ADD, null);
            ttsResultado.speak("Descrição do Produto ", TextToSpeech.QUEUE_ADD, null);
            ttsResultado.speak(descProduto, TextToSpeech.QUEUE_ADD, null);
            ttsResultado.speak("Preço de venda do Produto", TextToSpeech.QUEUE_ADD, null);
            ttsResultado.speak(precoProduto, TextToSpeech.QUEUE_ADD, null);
            ttsResultado.speak("Forncedor do Produto ", TextToSpeech.QUEUE_ADD, null);
            ttsResultado.speak(codigoProduto, TextToSpeech.QUEUE_ADD, null);
        }
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (ttsResultado != null) {
            ttsResultado.stop();
            ttsResultado.shutdown();
        }
        super.onDestroy();
    }
}
