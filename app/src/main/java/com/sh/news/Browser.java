package com.sh.news;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Browser extends AppCompatActivity {

    private ProgressBar progressBar;
    WebView webView;
    String url;
    String title;

    private static final String TAG = Browser.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        url = getIntent().getStringExtra("URL");
        title = getIntent().getStringExtra("TITLE");

        progressBar = (ProgressBar) findViewById(R.id.pb);

        webView = (WebView) findViewById(R.id.browser_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }
            @Override
            public void onPageFinished(WebView view, final String url) {
                progressBar.setVisibility(View.GONE);
            }
        });

        CheckURL checkURL = new CheckURL();
        checkURL.execute(url);
        
    }


    private class CheckURL extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... urls) {

            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                Log.i(TAG,"  Response " + conn.getResponseCode());
                if (conn.getResponseCode() == HttpsURLConnection.HTTP_NOT_FOUND || conn.getResponseCode() == HttpsURLConnection.HTTP_BAD_REQUEST) {
                   return false;
                }

            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException: " + e.getMessage());
                return false;
            } catch (ProtocolException e) {
                Log.e(TAG, "ProtocolException: " + e.getMessage());
                return false;
            } catch (IOException e) {
                Log.e(TAG, "IOException: " + e.getMessage());
                return false;
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
                return false;
            }
            return true;
        }



        @Override
        protected void onPostExecute(Boolean result) {
            if(result == true){
                update(url);
            }else{
                progressBar.setVisibility(View.GONE);
                notFound();
            }
        }
    }

    private void update(String url){
        webView.loadUrl(url);
    }

    private void notFound(){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        builder.setTitle("Page Not Found")
                .setMessage(":( Apologies for inconvenience")
                .setPositiveButton("READ RELATED ARTICLE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       update("https://www.google.com/search?q="+title);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        if(!this.isFinishing())
            dialog.show();
    }


}
