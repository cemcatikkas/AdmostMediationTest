package com.kokteyl.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import admost.sdk.AdMostInterstitial;
import admost.sdk.AdMostManager;
import admost.sdk.AdMostView;
import admost.sdk.AdMostViewBinder;
import admost.sdk.base.AdMost;
import admost.sdk.base.AdMostConfiguration;
import admost.sdk.base.AdMostLog;
import admost.sdk.listener.AdMostAdListener;
import admost.sdk.listener.AdMostViewListener;

public class MainActivity extends Activity {

    AdMostView ad;
    AdMostInterstitial interstitial;
    AdMostInterstitial video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.customPageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustomTestActivity.class);
                startActivity(intent);
            }
        });

        AdMostConfiguration.Builder configuration = new AdMostConfiguration.Builder(this, Statics.AMR_APP_ID);
        AdMostLog.setLogEnabled(true);
        AdMost.getInstance().init(configuration.build());

        // This is just for your own style, left null if you want default layout style
        final AdMostViewBinder binder = new AdMostViewBinder.Builder(R.layout.custom_layout)
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
        // *******************************

        // Banner request implementation
        ad = new AdMostView(this, Statics.BANNER_ZONE, AdMostManager.getInstance().AD_MEDIUM_RECTANGLE, new AdMostViewListener() {
            @Override
            public void onLoad(String network, int position) {
                LinearLayout viewAd = (LinearLayout) findViewById(R.id.adLayout);
                viewAd.removeAllViews();
                viewAd.addView(ad.getView());
                ((TextView)findViewById(R.id.loadedNetwork)).setText(network);
            }
        }, null //,binder
        );
        ad.getView();
        // Banner implementation


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
                        if (ad.getView().getParent() != null && ad.getView().getParent() instanceof ViewGroup) {
                            ((ViewGroup)ad.getView().getParent()).removeAllViews();
                        }
                        viewAd.addView(ad.getView());
                        ((TextView)findViewById(R.id.loadedNetwork)).setText(network);
                    }
                }, binder);
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

                video = new AdMostInterstitial(MainActivity.this, Statics.VIDEO_ZONE, listener);
                video.refreshAd(true);
            }
        });

        findViewById(R.id.otherPage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.customPageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustomTestActivity.class);
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
        if (video != null) {
            video.destroy();
        }
    }

}
