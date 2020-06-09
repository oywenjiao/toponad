package com.cat.toponad;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.webkit.WebView;

import androidx.annotation.NonNull;

import io.flutter.Log;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import com.anythink.core.api.ATSDK;

/** ToponadPlugin */
public class ToponadPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
  private static final String TAG = "com.cat.topponad.plugin";

  private MethodChannel methodChannel;
  private Context applicationContext;
  private Activity activity;

  private RewardVideo rewardVideo;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    onAttachedToEngine(flutterPluginBinding.getApplicationContext(), flutterPluginBinding.getBinaryMessenger());
  }

  private void onAttachedToEngine(Context applicationContext, BinaryMessenger messenger) {
    this.applicationContext = applicationContext;
    methodChannel = new MethodChannel(messenger, "com.cat.topponad");
    methodChannel.setMethodCallHandler(this);
  }

  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "com.cat.topponad");
    channel.setMethodCallHandler(new ToponadPlugin());
  }


  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    switch (call.method) {
      case "getPlatformVersion":
        result.success("Android " + Build.VERSION.RELEASE);
        break;
      case "register":
        Log.i(TAG, "ad init success");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
          String processName = getProcessName();
          if (applicationContext.getPackageName().equals(processName)) {
            WebView.setDataDirectorySuffix(processName);
          }
        }
        final String appId = call.argument("appid");
        final String appKey = call.argument("appKey");
        ATSDK.setNetworkLogDebug(true);
        ATSDK.integrationChecking(applicationContext);
        ATSDK.init(applicationContext, appId, appKey);
        result.success("success");
        break;
      case "loadRewardAd":
        String mCodeId = call.argument("mCodeId");

        rewardVideo = new RewardVideo();
        rewardVideo._channel = methodChannel;
        rewardVideo.activity = activity;
        rewardVideo.context = applicationContext;
        rewardVideo.mCodeId = mCodeId;
        rewardVideo.init();
        result.success("success");
        break;
      case "showRewardAd":
        rewardVideo.showAd();
        result.success("success");
        break;
      default:
        result.notImplemented();
        break;
    }
  }

  @TargetApi(Build.VERSION_CODES.CUPCAKE)
  private String getProcessName() {
    final int pid = Process.myPid();
    String processName = "";
    ActivityManager manager = (ActivityManager) applicationContext.getSystemService(Context.ACTIVITY_SERVICE);
    assert manager != null;
    for (ActivityManager.RunningAppProcessInfo appProcessInfo : manager.getRunningAppProcesses()) {
      if (appProcessInfo.pid == pid) {
        processName = appProcessInfo.processName;
        break;
      }
    }
    return processName;
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
  }

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
