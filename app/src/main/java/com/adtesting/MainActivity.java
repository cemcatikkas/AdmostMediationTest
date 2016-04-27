package com.adtesting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.chartboost.sdk.Chartboost;
import com.jirbo.adcolony.AdColony;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.vungle.publisher.VunglePub;

import java.util.HashMap;

import admost.sdk.AdMostInterstitial;
import admost.sdk.AdMostManager;
import admost.sdk.AdMostView;
import admost.sdk.AdMostViewBinder;
import admost.sdk.base.AdMost;
import admost.sdk.base.AdMostAdNetwork;
import admost.sdk.base.AdMostConfiguration;
import admost.sdk.base.AdMostLog;
import admost.sdk.listener.AdMostAdListener;
import admost.sdk.listener.AdMostViewListener;

public class MainActivity extends Activity {

    final String fullscreenZone = "b6fd7c7b-c6bd-42d2-b8f0-7f358ea02554";
    final String video = "e270e78b-20f4-4782-9a81-73b2e2346ec0";
    final String bannerZone = "a261abd8-04c1-4987-981e-1bc9acd8d77d";

    AdMostView ad;
    AdMostInterstitial interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HashMap map = new HashMap();
        map.put(AdMostAdNetwork.INMOBI, "4028cb8b2dbd0408012deb1bdda50431");
        map.put(AdMostAdNetwork.FLURRY, "CH2PCJMWFS2VHBXD757P");

        AdMostConfiguration.Builder configuration = new AdMostConfiguration.Builder(this);
        configuration.initIds(map);

        AdMostLog.setLogEnabled(true);
        AdMost.getInstance().init(configuration.build());

        ad = new AdMostView(this, bannerZone, AdMostManager.getInstance().AD_MEDIUM_RECTANGLE, new AdMostViewListener() {
            @Override
            public void onLoad(String network, int position) {
                LinearLayout viewAd = (LinearLayout) findViewById(R.id.adLayout);
                viewAd.removeAllViews();
                viewAd.addView(ad.getView());
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

                interstitial = new AdMostInterstitial(MainActivity.this, fullscreenZone, listener);
                interstitial.refreshAd(true);
            }
        });


        findViewById(R.id.refreshBanner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad = new AdMostView(MainActivity.this, bannerZone, AdMostManager.getInstance().AD_MEDIUM_RECTANGLE, new AdMostViewListener() {
                    @Override
                    public void onLoad(String network, int position) {
                        LinearLayout viewAd = (LinearLayout) findViewById(R.id.adLayout);
                        viewAd.removeAllViews();
                        viewAd.addView(ad.getView());
                    }
                }, null);
                ad.getView();
            }
        });



    }
}
