package com.adtesting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import admost.sdk.AdMostInterstitial;
import admost.sdk.AdMostManager;
import admost.sdk.AdMostView;
import admost.sdk.base.AdMost;
import admost.sdk.base.AdMostAdNetwork;
import admost.sdk.base.AdMostConfiguration;
import admost.sdk.base.AdMostLog;
import admost.sdk.listener.AdMostAdListener;
import admost.sdk.listener.AdMostViewListener;

public class MainActivity extends Activity {

    final String FULLSCREEN_ZONE = "f99e409b-f9ab-4a2e-aa9a-4d143e6809ae";
    final String VIDEO_ZONE = "e270e78b-20f4-4782-9a81-73b2e2346ec0";
    final String BANNER_ZONE = "86644357-21d0-45a4-906a-37262461df65";

    final String INMOBI_ACCOUNT_ID = "4028cb8b2dbd0408012deb1bdda50431";
    final String FLURRY_API_KEY = "76DBB28TZHFF4GZ5TJ6Y";

    AdMostView ad;
    AdMostInterstitial interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdMostConfiguration.Builder configuration = new AdMostConfiguration.Builder(this);

        /*
        configuration.initIds(AdMostAdNetwork.NATIVEX, NATIVEX_APP_ID);
        configuration.initIds(AdMostAdNetwork.CHARTBOOST, CHARTBOOST_ID, CHARTBOOST_SIGNATURE);
        configuration.initIds(AdMostAdNetwork.VUNGLE, VUNGLE_ID);
        configuration.initIds(AdMostAdNetwork.ADCOLONY, ADCOLONY_ID, ADCOLONY_REWARDED, ADCOLONY_INTERSTITIAL);
*/

        configuration.initIds(AdMostAdNetwork.INMOBI, INMOBI_ACCOUNT_ID);
        configuration.initIds(AdMostAdNetwork.FLURRY, FLURRY_API_KEY);

        AdMostLog.setLogEnabled(true);
        AdMost.getInstance().init(configuration.build());

        ad = new AdMostView(this, BANNER_ZONE, AdMostManager.getInstance().AD_MEDIUM_RECTANGLE, new AdMostViewListener() {
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

                interstitial = new AdMostInterstitial(MainActivity.this, FULLSCREEN_ZONE, listener);
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
                ad = new AdMostView(MainActivity.this, BANNER_ZONE, AdMostManager.getInstance().AD_MEDIUM_RECTANGLE, new AdMostViewListener() {
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


        findViewById(R.id.otherPage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        AdMost.getInstance().onStart(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        AdMost.getInstance().onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        AdMost.getInstance().onPause(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        AdMost.getInstance().onStop(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AdMost.getInstance().onDestroy(this);

        if (interstitial != null) {
            interstitial.destroy();
        }
    }
}
