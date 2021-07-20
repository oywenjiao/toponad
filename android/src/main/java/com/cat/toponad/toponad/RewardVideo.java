package com.cat.toponad.toponad;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import io.flutter.plugin.common.MethodChannel;

import com.anythink.core.api.ATAdConst;
import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.ATAdStatusInfo;
import com.anythink.core.api.AdError;
import com.anythink.rewardvideo.api.ATRewardVideoAd;
import com.anythink.rewardvideo.api.ATRewardVideoExListener;
import com.anythink.rewardvideo.api.ATRewardVideoListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    Activity activity;

    public void init() {
        mRewardVideoAd = new ATRewardVideoAd(activity, mCodeId);
        mRewardVideoAd.setAdListener(new ATRewardVideoListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
            }

            @Override
            public void onRewardedVideoAdFailed(AdError errorCode) {
            }

            @Override
            public void onRewardedVideoAdPlayStart(ATAdInfo entity) {
            }

            @Override
            public void onRewardedVideoAdPlayEnd(ATAdInfo entity) {
            }

            @Override
            public void onRewardedVideoAdPlayFailed(AdError errorCode, ATAdInfo entity) {
            }

            @Override
            public void onRewardedVideoAdClosed(ATAdInfo entity) {
            }

            @Override
            public void onReward(ATAdInfo entity) {
            }

            @Override
            public void onRewardedVideoAdPlayClicked(ATAdInfo entity) {
            }
        });

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
         ** 显示广告组件
         */
        mRewardVideoAd.show(activity);
    }
}