package com.example.sigure.booksearch;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String url = "http://iss.ndl.go.jp/api/opensearch?isbn=";

        final WebView webView = (WebView)findViewById(R.id.sampleWeb);
        webView.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Trident/7.0; rv:11.0) like Gecko");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new ViewSourceClient());
        webView.addJavascriptInterface(this,"MainActivity");

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText)findViewById(R.id.saerchBox);
                Toast.makeText(MainActivity.this, url + editText.getText(), Toast.LENGTH_LONG).show();
                webView.loadUrl(url + editText.getText());

            }
        });

        webView.loadUrl("http://iss.ndl.go.jp/api/opensearch?isbn=9784339008807");

    }

    @JavascriptInterface
    public void viewSource(final String src) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d("JS2",src);
            }
        });
    }

    private static class ViewSourceClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            view.loadUrl("javascript:window.MainActivity.viewSource(document.documentElement.outerHTML);");
            Log.d("JS","呼ばれた気がする");
        }
    }

}
