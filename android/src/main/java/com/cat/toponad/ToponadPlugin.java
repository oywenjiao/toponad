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

/***************************************************************************************************
 ***                                   Flutter Plugin Core                                       ***
 ***************************************************************************************************
 *
 *                FlutterPlugin:
 *
 *                MethodCallHandler: 隧道调用方法
 *
 *                ActivityAware:
 *
 *-------------------------------------------------------------------------------------------------*
 * ToponadPlugin
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
public class ToponadPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
  /*
  ** 配置日志前缀
   */
  private static final String TAG = "com.cat.topponad.plugin";

  /*
  ** 预设定 隧道方法
   */
  private MethodChannel methodChannel;

  /*
  ** 预设定 应用上下文
   */
  private Context applicationContext;

  /*
  ** 预设定 活动（用户界面)
   */
  private Activity activity;

  /*
  **  预定义 激励视频类
   */
  private RewardVideo rewardVideo;

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
    methodChannel = new MethodChannel(messenger, "com.cat.topponad");
    methodChannel.setMethodCallHandler(this);
  }

  /*
  ** 兼容旧版本 plugin 初始化方法
   */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "com.cat.topponad");
    channel.setMethodCallHandler(new ToponadPlugin());
  }

  /*
  ** 隧道方法
   */
  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    switch (call.method) {
      /*
      ** 获取操作系统版本
       */
      case "getPlatformVersion":
        result.success("Android " + Build.VERSION.RELEASE);
        break;
      /*
      ** 注册 SDK
       */
      case "register":
        /*
        ** Android 9 需做特殊处理
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
          String processName = getProcessName();
          if (applicationContext.getPackageName().equals(processName)) {
            WebView.setDataDirectorySuffix(processName);
          }
        }
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
      /*
      ** 初始化激励视频
       */
      case "loadRewardAd":
        String mCodeId = call.argument("mCodeId");
        /*
        ** 实例化 激励视频操作类
         */
        rewardVideo = new RewardVideo();
        rewardVideo._channel = methodChannel;
        rewardVideo.activity = activity;
        rewardVideo.context = applicationContext;
        rewardVideo.mCodeId = mCodeId;
        /*
        ** 执行激励视频初始化
         */
        rewardVideo.init();
        result.success("success");
        break;
      /*
      ** 获取广告
       */
      case "showRewardAd":
        rewardVideo.showAd();
        result.success("success");
        break;
      default:
        result.notImplemented();
        break;
    }
  }

  /*
  ** 获取进程信息
   */
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
