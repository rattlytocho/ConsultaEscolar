package mx.gob.jalisco.sej.consultaescolar;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class MainActivity extends Activity {
    private WebView mWebView;
    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            mWebView = new WebView(this);
            mWebView.loadUrl("http://edu.jalisco.gob.mx/consulta-escolar/");
            mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
            mWebView.getSettings().getAllowContentAccess();
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.getSettings().setDomStorageEnabled(true);
            mWebView.getSettings().setAllowFileAccess(true);
            mWebView.getSettings().setAllowContentAccess(true);

            mWebView.setWebViewClient(new WebViewClient() {
                @Override

                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }


            });

            this.setContentView(mWebView);



        //setContentView(R.layout.main);
    }


}