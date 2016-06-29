package mx.gob.jalisco.sej.consultaescolar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ViewPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_page);

        Bundle bundle = this.getIntent().getExtras();

        String URL = bundle.getString("URL");
        String TITLE = bundle.getString("TITLE");


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(TITLE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        WebView webview = (WebView) findViewById(R.id.viewPage);
        assert webview != null;
        webview.setWebChromeClient(new WebChromeClient());
        webview.setWebViewClient(new WebViewClient());
        webview.clearCache(true);
        webview.clearHistory();
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        webview.loadUrl(URL);
    }

}
