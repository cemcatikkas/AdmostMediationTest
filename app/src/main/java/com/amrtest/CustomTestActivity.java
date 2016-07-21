package com.amrtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kokteyl.amrtest.R;
import com.millennialmedia.InlineAd;
import com.millennialmedia.InterstitialAd;
import com.millennialmedia.MMException;

import admost.sdk.AdMostInterstitial;
import admost.sdk.AdMostManager;
import admost.sdk.AdMostView;
import admost.sdk.base.AdMost;
import admost.sdk.base.AdMostAdNetwork;
import admost.sdk.base.AdMostLog;
import admost.sdk.base.AdMostPreferences;
import admost.sdk.listener.AdMostAdListener;
import admost.sdk.listener.AdMostViewListener;
import admost.sdk.model.AdMostNetworkItem;

public class CustomTestActivity extends Activity {

    private String TAG = "MILLEN_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_test);

        loadAd();
    }

    private void loadAd() {
        try {
            final LinearLayout layout = new LinearLayout(this);
            layout.setLayoutParams(new LinearLayout.LayoutParams(AdMost.getDip(320), AdMost.getDip(50)));
            layout.setGravity(Gravity.CENTER);

            ViewGroup view = (ViewGroup)getWindow().getDecorView();
            view.addView(layout);

            com.millennialmedia.InlineAd inlineAd = com.millennialmedia.InlineAd.createInstance("203888", layout);

            inlineAd.setListener(new com.millennialmedia.InlineAd.InlineListener() {
                @Override
                public void onRequestSucceeded(com.millennialmedia.InlineAd inlineAd) {

                }

                @Override
                public void onRequestFailed(com.millennialmedia.InlineAd inlineAd, com.millennialmedia.InlineAd.InlineErrorStatus inlineErrorStatus) {

                }

                @Override
                public void onClicked(com.millennialmedia.InlineAd inlineAd) {

                }

                @Override
                public void onResize(com.millennialmedia.InlineAd inlineAd, int i, int i1) {
                }

                @Override
                public void onResized(com.millennialmedia.InlineAd inlineAd, int i, int i1, boolean b) {
                }

                @Override
                public void onExpanded(com.millennialmedia.InlineAd inlineAd) {
                }

                @Override
                public void onCollapsed(com.millennialmedia.InlineAd inlineAd) {
                }

                @Override
                public void onAdLeftApplication(com.millennialmedia.InlineAd inlineAd) {
                }
            });
            com.millennialmedia.InlineAd.InlineAdMetadata inlineAdMetadata = new com.millennialmedia.InlineAd.InlineAdMetadata().setAdSize(InlineAd.AdSize.BANNER);
            inlineAd.setRefreshInterval(0);
            inlineAd.request(inlineAdMetadata);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }




}
