package com.cesar.weather.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cesar.weather.R;
import com.cesar.weather.domain.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 01/07/2017.
 */

public class ContactFragment extends Fragment {

    @BindView(R.id.webview) WebView webView;
    private ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        dialog = new ProgressDialog(getActivity());

        View view = inflater.inflate(R.layout.contact_fragment, container, false);
        ButterKnife.bind(this,view);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        dialog.setMessage("Loading..Please wait.");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        webView.loadUrl(Constants.ZONAAPP_URL);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }
}