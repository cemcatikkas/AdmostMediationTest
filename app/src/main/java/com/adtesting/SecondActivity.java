package com.adtesting;

import android.app.Activity;
import android.os.Bundle;
import android.test.suitebuilder.TestMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

import admost.sdk.AdMostInterstitial;
import admost.sdk.AdMostManager;
import admost.sdk.AdMostView;
import admost.sdk.base.AdMost;
import admost.sdk.base.AdMostAdNetwork;
import admost.sdk.base.AdMostConfiguration;
import admost.sdk.base.AdMostLog;
import admost.sdk.listener.AdMostAdListener;
import admost.sdk.listener.AdMostViewListener;

public class SecondActivity extends Activity {

    final String fullscreenZone = "b6fd7c7b-c6bd-42d2-b8f0-7f358ea02554";
    final String video = "e270e78b-20f4-4782-9a81-73b2e2346ec0";
    final String bannerZone = "a261abd8-04c1-4987-981e-1bc9acd8d77d";

    AdMostView ad;
    AdMostInterstitial interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.otherPage).setVisibility(View.GONE);

        ad = new AdMostView(this, bannerZone, AdMostManager.getInstance().AD_MEDIUM_RECTANGLE, new AdMostViewListener() {
            @Override
            public void onLoad(String network, int position) {
                LinearLayout viewAd = (LinearLayout) findViewById(R.id.adLayout);
                viewAd.removeAllViews();
                viewAd.addView(ad.getView());
                ((TextView)findViewById(R.id.loadedNetwork)).setText(network);
            }
        }, null);
        ad.getView();

        findViewById(R.id.showInterstitial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AdMostAdListener listener = new AdMostAdListener() {
                    @Override
                    public void onAction(int value) {
                        if (value == AdMostAdListener.LOADED) {
                            AdMostLog.log("LOADED");
                        } else if (value == AdMostAdListener.COMPLETED) {
                            AdMostLog.log("COMPLETED");
                        } else if (value == AdMostAdListener.FAILED) {
                            AdMostLog.log("FAILED");
                        } else if (value == AdMostAdListener.CLOSED) {
                            AdMostLog.log("CLOSED");
                        }
                    }
                };

                interstitial = new AdMostInterstitial(SecondActivity.this, fullscreenZone, listener);
                interstitial.refreshAd(true);
            }
        });


        findViewById(R.id.refreshBanner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LinearLayout) findViewById(R.id.adLayout)).removeAllViews();
                if (ad != null) {
                    ad.destroy();
                }
                ((TextView)findViewById(R.id.loadedNetwork)).setText("");
                ad = new AdMostView(SecondActivity.this, bannerZone, AdMostManager.getInstance().AD_MEDIUM_RECTANGLE, new AdMostViewListener() {
                    @Override
                    public void onLoad(String network, int position) {
                        LinearLayout viewAd = (LinearLayout) findViewById(R.id.adLayout);
                        viewAd.removeAllViews();
                        viewAd.addView(ad.getView());
                        ((TextView)findViewById(R.id.loadedNetwork)).setText(network);
                    }
                }, null);
                ad.getView();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        AdMost.getInstance().onActivityResume(SecondActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AdMost.getInstance().onActivityPause(SecondActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AdMost.getInstance().onActivityDestroy(SecondActivity.this);
        if (interstitial != null) {
            interstitial.destroy();
        }

    }
}
