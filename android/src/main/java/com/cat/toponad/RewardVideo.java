package com.cat.toponad;

import android.app.Activity;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import io.flutter.Log;
import io.flutter.plugin.common.MethodChannel;

import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.AdError;
import com.anythink.rewardvideo.api.ATRewardVideoAd;
import com.anythink.rewardvideo.api.ATRewardVideoListener;

class RewardVideo {
    private static final String TAG = "RewardVideo";

    private ATRewardVideoAd mRewardVideoAd;

    String mCodeId = null;

    MethodChannel _channel;
    Context context;
    Activity activity;

    void init() {
        mRewardVideoAd = new ATRewardVideoAd(activity, mCodeId);
        if(!mRewardVideoAd.isAdReady()){
            mRewardVideoAd.load();
        }
    }

    void showAd() {
        mRewardVideoAd.setAdListener(new ATRewardVideoListener() {

            @Override
            public void onRewardedVideoAdLoaded() {
//                Log.i(TAG, "onRewardedVideoAdLoaded");
            }

            @Override
            public void onRewardedVideoAdFailed(AdError errorCode) {
//                Log.i(TAG, "onRewardedVideoAdFailed error:" + errorCode.printStackTrace());
                Map<String, Object> rewardVideoCallBack = new HashMap<>();
                rewardVideoCallBack.put("callType", "onRewardedVideoAdFailed");
                rewardVideoCallBack.put("rewardRes", errorCode.printStackTrace());
                _channel.invokeMethod("onRewardResponse", rewardVideoCallBack);
            }

            @Override
            public void onRewardedVideoAdPlayStart(ATAdInfo entity) {
//                Log.i(TAG, "onRewardedVideoAdPlayStart:\n" + entity.toString());
                Map<String,Object> rewardVideoCallBack = new HashMap<>();
                rewardVideoCallBack.put("callType", "onRewardedVideoAdPlayStart");
                rewardVideoCallBack.put("rewardRes", entity.toString());
                _channel.invokeMethod("onRewardResponse", rewardVideoCallBack);
            }

            @Override
            public void onRewardedVideoAdPlayEnd(ATAdInfo entity) {
//                Log.i(TAG, "onRewardedVideoAdPlayEnd:\n" + entity.toString());
                Map<String,Object> rewardVideoCallBack = new HashMap<>();
                rewardVideoCallBack.put("callType", "onRewardedVideoAdPlayEnd");
                rewardVideoCallBack.put("rewardRes", entity.toString());
                _channel.invokeMethod("onRewardResponse", rewardVideoCallBack);
            }

            @Override
            public void onRewardedVideoAdPlayFailed(AdError errorCode, ATAdInfo entity) {
//                Log.i(TAG, "onRewardedVideoAdPlayFailed error:" + errorCode.printStackTrace());
                Map<String,Object> rewardVideoCallBack = new HashMap<>();
                rewardVideoCallBack.put("callType", "onRewardedVideoAdPlayFailed");
                rewardVideoCallBack.put("rewardRes", entity.toString());
                _channel.invokeMethod("onRewardResponse", rewardVideoCallBack);

            }

            @Override
            public void onRewardedVideoAdClosed(ATAdInfo entity) {
                mRewardVideoAd.load();
//                Log.i(TAG, "onRewardedVideoAdClosed:\n" + entity.toString() );
                Map<String,Object> rewardVideoCallBack = new HashMap<>();
                rewardVideoCallBack.put("callType", "onRewardedVideoAdClosed");
                rewardVideoCallBack.put("rewardRes", entity.toString());
                _channel.invokeMethod("onRewardResponse", rewardVideoCallBack);
            }

            @Override
            public void onRewardedVideoAdPlayClicked(ATAdInfo entity) {
//                Log.i(TAG, "onRewardedVideoAdPlayClicked:\n" + entity.toString());
                Map<String,Object> rewardVideoCallBack = new HashMap<>();
                rewardVideoCallBack.put("callType", "onRewardedVideoAdPlayClicked");
                rewardVideoCallBack.put("rewardRes", entity.toString());
                _channel.invokeMethod("onRewardResponse", rewardVideoCallBack);
            }

            @Override
            public void onReward(ATAdInfo entity) {
//                Log.e(TAG, "onReward:\n" + entity.toString() );
                Map<String,Object> rewardVideoCallBack = new HashMap<>();
                rewardVideoCallBack.put("callType", "onReward");
                rewardVideoCallBack.put("rewardRes", entity.toString());
                _channel.invokeMethod("onRewardResponse", rewardVideoCallBack);
            }
        });

        mRewardVideoAd.show(activity);
    }
}
