package com.amrtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kokteyl.amrtest.R;
import com.millennialmedia.AppInfo;
import com.millennialmedia.MMSDK;

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
    AdMostInterstitial video;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;

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

        AdMostConfiguration.Builder configuration = new AdMostConfiguration.Builder(this);

        configuration.initIds(AdMostAdNetwork.ADCOLONY, Statics.ADCOLONY_ID, Statics.ADCOLONY_REWARDED);
        configuration.initIds(AdMostAdNetwork.VUNGLE, Statics.VUNGLE_ID);
        configuration.initIds(AdMostAdNetwork.CHARTBOOST, Statics.CHARTBOOST_ID, Statics.CHARTBOOST_SIGNATURE);
        configuration.initIds(AdMostAdNetwork.INMOBI, Statics.INMOBI_ACCOUNT_ID);
        configuration.initIds(AdMostAdNetwork.FLURRY, Statics.FLURRY_API_KEY);
        configuration.initIds(AdMostAdNetwork.NATIVEX, Statics.NATIVEX_APP_ID);
        configuration.initIds(AdMostAdNetwork.UNITY, Statics.UNITY_ID);
        configuration.initIds(AdMostAdNetwork.NEXTAGE, Statics.NEXT_AGE_SITE_ID);

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

        preferences = getSharedPreferences("AMR_SP", Context.MODE_WORLD_READABLE);
        editor = preferences.edit();

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

        findViewById(R.id.switchDebugMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (preferences.getString("AMR_DEBUG_MODE","CLOSED").equals("OPEN")) {
                    editor.putString("AMR_DEBUG_MODE","CLOSED");
                    editor.apply();
                    Toast.makeText(getApplicationContext(),"Debug mode closed", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putString("AMR_DEBUG_MODE","OPEN");
                    editor.apply();
                    Toast.makeText(getApplicationContext(),"Debug mode open", Toast.LENGTH_SHORT).show();
                }
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
