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

/***************************************************************************************************
 ***                                       激励视频操作类                                          ***
 ***************************************************************************************************
 *                                                                                                 *
 *-------------------------------------------------------------------------------------------------*
 * RewardVideo                                                                                     *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
class RewardVideo {
    /*
    ** 调试日志前缀
     */
    private static final String TAG = "RewardVideo";

    /*
    ** 激励视频core类
     */
    private ATRewardVideoAd mRewardVideoAd;

    /*
    ** 广告位ID
     */
    String mCodeId = null;

    /*
    **
     */
    MethodChannel _channel;

    /*
    **
     */
    Context context;

    /*
    **
     */
    Activity activity;

    /*
    ** SDK 初始化
    *  mCodeId: 广告位ID
     */
    void init() {
        mRewardVideoAd = new ATRewardVideoAd(activity, mCodeId);
        /*
        ** 判断广告组件是否可展示，否则执行加载方法
         */
        if(!mRewardVideoAd.isAdReady()){
            mRewardVideoAd.load();
        }
    }

    /*
    ** 获取广告信息
     */
    void showAd() {
        /*
        ** 设置激励视频广告回调监听
        *  将回调数据并发送给 flutter
         */
        mRewardVideoAd.setAdListener(new ATRewardVideoListener() {
            /*
            ** 广告加载成功回调
             */
            @Override
            public void onRewardedVideoAdLoaded() {
                // Log.i(TAG, "onRewardedVideoAdLoaded");
            }

            /*
            ** 广告加载失败回调
             */
            @Override
            public void onRewardedVideoAdFailed(AdError errorCode) {
                // Log.i(TAG, "onRewardedVideoAdFailed error:" + errorCode.printStackTrace());
                Map<String, Object> rewardVideoCallBack = new HashMap<>();
                rewardVideoCallBack.put("callType", "onRewardedVideoAdFailed");
                rewardVideoCallBack.put("rewardRes", errorCode.printStackTrace());
                _channel.invokeMethod("onRewardResponse", rewardVideoCallBack);
            }

            /*
            ** 广告刷新回调
             */
            @Override
            public void onRewardedVideoAdPlayStart(ATAdInfo entity) {
                // Log.i(TAG, "onRewardedVideoAdPlayStart:\n" + entity.toString());
                Map<String,Object> rewardVideoCallBack = new HashMap<>();
                rewardVideoCallBack.put("callType", "onRewardedVideoAdPlayStart");
                rewardVideoCallBack.put("rewardRes", entity.toString());
                _channel.invokeMethod("onRewardResponse", rewardVideoCallBack);
            }

            /*
            ** 广告播放结束回调
             */
            @Override
            public void onRewardedVideoAdPlayEnd(ATAdInfo entity) {
                // Log.i(TAG, "onRewardedVideoAdPlayEnd:\n" + entity.toString());
                Map<String,Object> rewardVideoCallBack = new HashMap<>();
                rewardVideoCallBack.put("callType", "onRewardedVideoAdPlayEnd");
                rewardVideoCallBack.put("rewardRes", entity.toString());
                _channel.invokeMethod("onRewardResponse", rewardVideoCallBack);
            }

            /*
            ** 广告播放失败回调
             */
            @Override
            public void onRewardedVideoAdPlayFailed(AdError errorCode, ATAdInfo entity) {
                // Log.i(TAG, "onRewardedVideoAdPlayFailed error:" + errorCode.printStackTrace());
                Map<String,Object> rewardVideoCallBack = new HashMap<>();
                rewardVideoCallBack.put("callType", "onRewardedVideoAdPlayFailed");
                rewardVideoCallBack.put("rewardRes", entity.toString());
                _channel.invokeMethod("onRewardResponse", rewardVideoCallBack);

            }

            /*
            ** 广告关闭回调
             */
            @Override
            public void onRewardedVideoAdClosed(ATAdInfo entity) {
                // Log.i(TAG, "onRewardedVideoAdClosed:\n" + entity.toString() );
                mRewardVideoAd.load();
                Map<String,Object> rewardVideoCallBack = new HashMap<>();
                rewardVideoCallBack.put("callType", "onRewardedVideoAdClosed");
                rewardVideoCallBack.put("rewardRes", entity.toString());
                _channel.invokeMethod("onRewardResponse", rewardVideoCallBack);
            }

            /*
            ** 广告点击回调
             */
            @Override
            public void onRewardedVideoAdPlayClicked(ATAdInfo entity) {
                // Log.i(TAG, "onRewardedVideoAdPlayClicked:\n" + entity.toString());
                Map<String,Object> rewardVideoCallBack = new HashMap<>();
                rewardVideoCallBack.put("callType", "onRewardedVideoAdPlayClicked");
                rewardVideoCallBack.put("rewardRes", entity.toString());
                _channel.invokeMethod("onRewardResponse", rewardVideoCallBack);
            }

            /*
            ** 下发激励的时候会回调
             */
            @Override
            public void onReward(ATAdInfo entity) {
                // Log.e(TAG, "onReward:\n" + entity.toString() );
                Map<String,Object> rewardVideoCallBack = new HashMap<>();
                rewardVideoCallBack.put("callType", "onReward");
                rewardVideoCallBack.put("rewardRes", entity.toString());
                _channel.invokeMethod("onRewardResponse", rewardVideoCallBack);
            }
        });

        /*
        ** 显示广告组件
         */
        mRewardVideoAd.show(activity);
    }
}
