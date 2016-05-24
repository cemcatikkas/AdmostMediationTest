package com.drawpath;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.masomo.drawpath.R;

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

    AdMostView ad;
    AdMostInterstitial interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdMostConfiguration.Builder configuration = new AdMostConfiguration.Builder(this);

        /*
        configuration.initIds(AdMostAdNetwork.NATIVEX, NATIVEX_APP_ID);
        */

        configuration.initIds(AdMostAdNetwork.ADCOLONY, Statics.ADCOLONY_ID, Statics.ADCOLONY_REWARDED);
        configuration.initIds(AdMostAdNetwork.VUNGLE, Statics.VUNGLE_ID);
        configuration.initIds(AdMostAdNetwork.CHARTBOOST, Statics.CHARTBOOST_ID, Statics.CHARTBOOST_SIGNATURE);
        configuration.initIds(AdMostAdNetwork.INMOBI, Statics.INMOBI_ACCOUNT_ID);
        configuration.initIds(AdMostAdNetwork.FLURRY, Statics.FLURRY_API_KEY);

        AdMostLog.setLogEnabled(true);
        AdMost.getInstance().init(configuration.build());

        ad = new AdMostView(this, Statics.BANNER_ZONE, AdMostManager.getInstance().AD_MEDIUM_RECTANGLE, new AdMostViewListener() {
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

                if (interstitial == null) {
                    AdMostAdListener listener = new AdMostAdListener() {
                        @Override
                        public void onAction(int value) {
                            if (value == AdMostAdListener.LOADED) {
                                AdMostLog.log(value + " MainActivity LOADED");
                            } else if (value == AdMostAdListener.COMPLETED) {
                                AdMostLog.log(value + " MainActivity COMPLETED");
                            } else if (value == AdMostAdListener.FAILED) {
                                AdMostLog.log(value + " MainActivity FAILED");
                            } else if (value == AdMostAdListener.CLOSED) {
                                AdMostLog.log(value + " MainActivity CLOSED");
                            }
                        }
                    };

                    interstitial = new AdMostInterstitial(MainActivity.this, Statics.FULLSCREEN_ZONE, listener);
                }
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
                ad = new AdMostView(MainActivity.this, Statics.BANNER_ZONE, AdMostManager.getInstance().AD_MEDIUM_RECTANGLE, new AdMostViewListener() {
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


        findViewById(R.id.showVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AdMostAdListener listener = new AdMostAdListener() {
                    @Override
                    public void onAction(int value) {
                        if (value == AdMostAdListener.LOADED) {
                            AdMostLog.log(value + " MainActivity LOADED");
                        } else if (value == AdMostAdListener.COMPLETED) {
                            AdMostLog.log(value + " MainActivity COMPLETED");
                        } else if (value == AdMostAdListener.FAILED) {
                            AdMostLog.log(value + " MainActivity FAILED");
                        } else if (value == AdMostAdListener.CLOSED) {
                            AdMostLog.log(value + " MainActivity CLOSED");
                        }
                    }
                };

                interstitial = new AdMostInterstitial(MainActivity.this, Statics.VIDEO_ZONE, listener);
                interstitial.refreshAd(true);
            }
        });

        findViewById(R.id.otherPage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });





        int TYPE = AdMostManager.getInstance().AD_BANNER;
// AD_LEADERBOARD for 90dp height, AD_MEDIUM_RECTANGLE for big banners (height : 250dp)

// You can use the following layouts by default, or define other layouts for your own designs
        int layout = admost.sdk.R.layout.admost_native;

            layout = admost.sdk.R.layout.admost_native_50;

        AdMostLog.setLogEnabled(true);
        AdMostViewBinder binder = new AdMostViewBinder.Builder(layout)
                .titleId(admost.sdk.R.id.ad_headline)
                .textId(admost.sdk.R.id.ad_body)
                .callToActionId(admost.sdk.R.id.ad_call_to_action)
                .iconImageId(admost.sdk.R.id.ad_app_icon)
                .mainImageId(admost.sdk.R.id.ad_image)
                .backImageId(admost.sdk.R.id.ad_back)
                .attributionId(admost.sdk.R.id.ad_attribution)
                .isFixed(false)
                .isCloseable(false)//(type & AD_CLOSEABLE) != 0
                .build();

// ZONE_ID : Your ZONE_ID defined on admost mediation panels.
        AdMostView ADMOST_MEDIATION_VIEW = new AdMostView(this, "", TYPE,  new AdMostViewListener() {
            @Override
            public void onLoad(String network, int position) {
                if (network.equals(AdMostAdNetwork.NO_NETWORK)) {
                    // No Banner Found
                } else {
                    // Ad Loaded, You can get ad view by calling ADMOST_MEDIATION_VIEW.getView(postion)
                    // Calling ADMOST_MEDIATION_VIEW.getView method multiple times will not cause any side effect.
                }
            }
        }, binder);

// Add the following line to get an ad from the admost mediation system.
        ADMOST_MEDIATION_VIEW.getView(0);

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
