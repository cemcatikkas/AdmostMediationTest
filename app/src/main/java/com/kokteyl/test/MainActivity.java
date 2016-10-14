package com.kokteyl.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

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

        ad = new AdMostView(this, Statics.BANNER_ZONE, AdMostManager.getInstance().AD_MEDIUM_RECTANGLE, new AdMostViewListener() {
            @Override
            public void onLoad(String network, int position) {
                LinearLayout viewAd = (LinearLayout) findViewById(R.id.adLayout);
                viewAd.removeAllViews();
                viewAd.addView(ad.getView());
                ((TextView)findViewById(R.id.loadedNetwork)).setText(network);
            }
        }, binder);
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

        //calculateRequestProbabilities();


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

    private void calculateRequestProbabilities()  {
        ArrayList<Double> weights = new ArrayList();
        ArrayList<Double> requests = new ArrayList();
        ArrayList<Double> impressions = new ArrayList();
        ArrayList<Double> probability = new ArrayList();

        weights.add(30000.0);
        weights.add(6000.0);
        weights.add(3852.0);
        weights.add(3131.0);
        weights.add(2280.0);
        weights.add(1657.0);
        weights.add(1312.0);
        weights.add(1200.0);
        weights.add(664.0);
        weights.add(556.0);

        requests.add(1324733.0);
        requests.add(1082000.0);
        requests.add(780599.0);
        requests.add(488802.0);
        requests.add(508240.0);
        requests.add(610700.0);
        requests.add(407500.0);
        requests.add(401600.0);
        requests.add(341000.0);
        requests.add(614291.0);

        impressions.add(3143.0);
        impressions.add(342000.0);
        impressions.add(46071.0);
        impressions.add(218272.0);
        impressions.add(25450.0);
        impressions.add(121500.0);
        impressions.add(167370.0);
        impressions.add(136600.0);
        impressions.add(117500.0);
        impressions.add(31933.0);

        double totalWeight = 0;

        Log.i("PERMUT","started");

        ArrayList<Double> emptyRates = new ArrayList();
        for (int i=0; i<impressions.size(); i++) {
            totalWeight += weights.get(i);
            emptyRates.add(1.0 - (impressions.get(i)/requests.get(i)));
            probability.add(0.0);
        }

        final String represantator = "abcdefghjklmnopqrstuxwvyz".substring(0,impressions.size());
        Log.i("PERMUT",represantator);
        ArrayList<String> permutations = permutation(represantator);

        Log.i("PERMUT","size : " + permutations.size());
        int size = permutations.size();
        int itemCount = impressions.size();
        for (int i=0; i<size;i++)  {
            String repItem = permutations.get(i);
            double tempTotalWeight = totalWeight;
            double itemProbability = 1;
            for (int j=0;j<itemCount;j++)  {
                int placementIndex = represantator.indexOf(repItem.charAt(j));
                itemProbability = itemProbability * weights.get(placementIndex) / tempTotalWeight;
                tempTotalWeight = tempTotalWeight - weights.get(placementIndex);
            }

            double orderCallProbability = 1;
            for (int j=0;j<itemCount;j++)  {
                int placementIndex = represantator.indexOf(repItem.charAt(j));
                probability.set(placementIndex, probability.get(placementIndex) + itemProbability * orderCallProbability);
                orderCallProbability = orderCallProbability * emptyRates.get(placementIndex);
            }
        }

        for (int i=0; i<probability.size();i++)  {
            Log.i("PERMUT", i + " probability : " + probability.get(i));
        }


        //((TextView)findViewById(R.id.permutasyon)).setText(result);



    }

    /**
     * List permutation of a string
     *
     * @param s the input string
     * @return  the list of permutation
     */
    public static ArrayList<String> permutation(String s) {
        // The result
        ArrayList<String> res = new ArrayList<String>();
        // If input string's length is 1, return {s}
        if (s.length() == 1) {
            res.add(s);
        } else if (s.length() > 1) {
            int lastIndex = s.length() - 1;
            // Find out the last character
            String last = s.substring(lastIndex);
            // Rest of the string
            String rest = s.substring(0, lastIndex);
            // Perform permutation on the rest string and
            // merge with the last character
            res = merge(permutation(rest), last);
        }
        return res;
    }

    /**
     * @param list a result of permutation, e.g. {"ab", "ba"}
     * @param c    the last character
     * @return     a merged new list, e.g. {"cab", "acb" ... }
     */
    public static ArrayList<String> merge(ArrayList<String> list, String c) {
        ArrayList<String> res = new ArrayList<String>();
        // Loop through all the string in the list
        for (String s : list) {
            // For each string, insert the last character to all possible postions
            // and add them to the new list
            for (int i = 0; i <= s.length(); ++i) {
                String ps = new StringBuffer(s).insert(i, c).toString();
                res.add(ps);
            }
        }
        return res;
    }
}
