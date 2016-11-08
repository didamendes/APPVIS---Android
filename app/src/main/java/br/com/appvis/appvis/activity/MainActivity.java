package br.com.appvis.appvis.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.Locale;

import br.com.appvis.appvis.R;
import br.com.appvis.appvis.barcode.BarcodeCaptureActivity;
import br.com.appvis.appvis.domain.Produto;
import br.com.appvis.appvis.domain.ProdutoDB;

import static android.R.attr.data;
import static br.com.appvis.appvis.R.id.tNome;
import static com.google.android.gms.analytics.internal.zzy.s;
import static com.google.android.gms.analytics.internal.zzy.v;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int BARCODE_READER_REQUEST_CODE = 1;

    private TextView mResultTextView;
    String codigo;
    private String Codigo;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tts = new TextToSpeech(this, this);
        Button scanBarcodeButton = (Button) findViewById(R.id.scan_barcode_button);

            mResultTextView = (TextView) findViewById(R.id.result_textview);

        scanBarcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
            }
        });

        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivityProduto.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    Intent intent = new Intent(this, ResultadoProduto.class);
                    mResultTextView.setText(barcode.displayValue);
                    String codigo = mResultTextView.getText().toString();
                    intent.putExtra("codigo", codigo);
                    startActivity(intent);
                } else mResultTextView.setText(R.string.no_barcode_captured);
            } else Log.e(LOG_TAG, String.format(getString(R.string.barcode_error_format),
                    CommonStatusCodes.getStatusCodeString(resultCode)));
        } else super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS) {
            Locale locale = new Locale("pt", "BR");
            tts.setLanguage(locale);
            tts.speak("Seja bem vindo ao APPVIS, o aplicativo tem como objetivo ajudar deficientes visuais ter informação de um produto atraves do codigo de barra do produto ! ", TextToSpeech.QUEUE_FLUSH, null);
            tts.speak("Por favor click no meio da tela para acionar a camera para ler o codigo de barra ! ", TextToSpeech.QUEUE_ADD, null);
            tts.speak("Se ao ler o codigo de barra voltar para uma mensagem de bem vindo do APPVIS o produto não foi encontrado, se " +
                    " o nome do produto for lido o codigo foi encontrado com sucesso ! ", TextToSpeech.QUEUE_ADD, null);


        }
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
