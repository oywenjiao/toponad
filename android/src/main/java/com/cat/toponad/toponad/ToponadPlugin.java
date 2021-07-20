package com.cat.toponad.toponad;

import androidx.annotation.NonNull;
import android.app.Activity;
import android.content.Context;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

import com.anythink.core.api.ATSDK;

/** ToponadPlugin */
public class ToponadPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;

  /*
   ** 预设定 应用上下文
   */
  private Context applicationContext;

  /*
   **  预定义 激励视频类
   */
  private RewardVideo rewardVideo;

  /*
   ** 预设定 活动（用户界面)
   */
  private Activity activity;

  /*
   ** Plugin 初始化
   */
  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    onAttachedToEngine(flutterPluginBinding.getApplicationContext(), flutterPluginBinding.getBinaryMessenger());
  }

  /*
   **
   */
  private void onAttachedToEngine(Context applicationContext, BinaryMessenger messenger) {
    this.applicationContext = applicationContext;
    channel = new MethodChannel(messenger, "toponad");
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    switch (call.method) {
      case "getPlatformVersion":
        result.success("Android " + android.os.Build.VERSION.RELEASE);
        break;
      case "registerTopOn":
        final String appId = call.argument("appid");
        final String appKey = call.argument("appKey");

        /*
         ** 开启调试
         */
        ATSDK.setNetworkLogDebug(true);
        /*
         ** 检测SDK配置是否成功
         */
        ATSDK.integrationChecking(applicationContext);
        /*
         ** SDK初始化设置
         */
        ATSDK.init(applicationContext, appId, appKey);
        result.success("success");
        break;
      case "loadRewardAd":
        String mCodeId = call.argument("mCodeId");
        /*
         ** 实例化 激励视频操作类
         */
        rewardVideo = new RewardVideo();
        rewardVideo._channel = channel;
        rewardVideo.activity = activity;
        rewardVideo.mCodeId = mCodeId;
        /*
         ** 执行激励视频初始化
         */
        rewardVideo.init();
        result.success("success");
        break;
      case "showRewardAd":
        rewardVideo.showAd();
        result.success("success");
        break;
      default:
        result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  /*
   **  activity 生命周期
   */
  @Override
  public void onAttachedToActivity(ActivityPluginBinding binding) {
    this.activity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {

  }

  @Override
  public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {

  }

  @Override
  public void onDetachedFromActivity() {

  }
}
