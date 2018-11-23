package com.weifeng.wanandroid.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.weifeng.wanandroid.R;
import com.weifeng.wanandroid.model.ArticleBean;
import com.weifeng.wanandroid.model.ArticleContentItem;
import com.weifeng.wanandroid.repositiry.APIService;
import com.weifeng.wanandroid.repositiry.RetrofitClient;
import com.weifeng.wanandroid.repositiry.response.CollectArticlesInStationResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebViewActivity extends Activity {
    private WebView webView;
    private ProgressBar progressBar;
    private ImageView backImg;
    private ImageView collectImg;

    public static final String ARTICLE_EXTRA = "article";
    private ArticleContentItem articleContentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        backImg = findViewById(R.id.img_back_icon);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.this.finish();
            }
        });
        progressBar = findViewById(R.id.progressbar);//进度条
        collectImg = findViewById(R.id.img_collect_article);
        collectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(articleContentItem.collect){
                    cancelCollectArticle();
                }else {
                    collectArticle();
                }
            }
        });
        webView = findViewById(R.id.webview);
        webView.addJavascriptInterface(this, "android");//添加js监听 这样html就能调用客户端
        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js
        webSettings.setDomStorageEnabled(true);
        initIntent(this.getIntent());
        /**
         * LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
         * LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
         * LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
         * LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
         */
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//不使用缓存，只从网络获取数据.

        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);

        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setLoadWithOverviewMode(true);
        //不显示webview缩放按钮
//        webSettings.setDisplayZoomControls(false);
    }

    private void collectArticle() {
        RetrofitClient.getInstance().getService(APIService.class).collectStationArticle(articleContentItem.id).enqueue(new Callback<CollectArticlesInStationResponse>() {
            @Override
            public void onResponse(Call<CollectArticlesInStationResponse> call, Response<CollectArticlesInStationResponse> response) {
                if(response.code()==200) {
                    collectImg.setSelected(true);
                    articleContentItem.collect = true;
                }
            }

            @Override
            public void onFailure(Call<CollectArticlesInStationResponse> call, Throwable t) {

            }
        });
    }

    private void cancelCollectArticle() {
        RetrofitClient.getInstance().getService(APIService.class).cancelCollectStationArticle(articleContentItem.id).enqueue(new Callback<CollectArticlesInStationResponse>() {
            @Override
            public void onResponse(Call<CollectArticlesInStationResponse> call, Response<CollectArticlesInStationResponse> response) {
                if(response.code()==200) {
                    collectImg.setSelected(false);
                    articleContentItem.collect = false;
                }
            }

            @Override
            public void onFailure(Call<CollectArticlesInStationResponse> call, Throwable t) {

            }
        });
    }

    private void initIntent(Intent intent) {
        String url = intent.getStringExtra("url");
        articleContentItem = (ArticleContentItem) intent.getSerializableExtra(ARTICLE_EXTRA);
        if(articleContentItem == null){
            collectImg.setVisibility(View.GONE);
        }else {
            if (articleContentItem != null && articleContentItem.collect == true) {
                collectImg.setSelected(true);
            } else {
                collectImg.setSelected(false);
            }
        }
        webView.loadUrl(url);
    }


    //WebViewClient主要帮助WebView处理各种通知、请求事件
    private WebViewClient webViewClient;

    {
        webViewClient = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {//页面加载完成
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("ansen", "拦截url:" + url);
                if (url.equals("http://www.google.com/")) {
                    Toast.makeText(WebViewActivity.this, "国内不能访问google,拦截该url", Toast.LENGTH_LONG).show();
                    return true;//表示我已经处理过了
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();    //支持https
            }
        };
    }

    //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
    private WebChromeClient webChromeClient = new WebChromeClient() {
        //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
            localBuilder.setMessage(message).setPositiveButton("确定", null);
            localBuilder.setCancelable(false);
            localBuilder.create().show();

            //注意:
            //必须要这一句代码:result.confirm()表示:
            //处理结果为确定状态同时唤醒WebCore线程
            //否则不能继续点击按钮
            result.confirm();
            return true;
        }

        //获取网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            Log.i("ansen", "网页标题:" + title);
        }

        //加载进度回调
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setProgress(newProgress);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i("ansen", "是否有上一个页面:" + webView.canGoBack());
        if (webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) {//点击返回按钮的时候判断有没有上一页
            webView.goBack(); // goBack()表示返回webView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * JS调用android的方法
     *
     * @param str
     * @return
     */
    @JavascriptInterface //仍然必不可少
    public void getClient(String str) {
        Log.i("ansen", "html调用客户端:" + str);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //释放资源
        webView.destroy();
        webView = null;
    }
}
