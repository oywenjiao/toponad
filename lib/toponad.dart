
import 'dart:async';

import 'package:flutter/services.dart';

class Toponad {
  static const MethodChannel _channel =
      const MethodChannel('toponad');

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  /*
   ** 初始化及注册 TopOn SDK
   ** appid: TopOn 平台账号的apppid
   ** appKey: TopOn 平台账号的appKey
   */
  static Future<void> registerToponad({
    required String appId,
    required String appKey
  }) async {
    /*
     ** 通过隧道 指向匹配的方法并发送请求
     */
    return await _channel.invokeMethod("registerTopOn", {
      "appid": appId,
      "appKey": appKey
    });
  }

  /*
   ** 初始化并加载激励视频
   ** mCodeId: 各广告平台对应激励视频广告位ID
   */
  static Future loadRewardAd({
    required String mCodeId
  }) async {
    return await _channel.invokeMethod("loadRewardAd", {"mCodeId": mCodeId});
  }

  /*
   ** 读取并展示激励视频广告
   */
  static Future<void> showRewardAd() async {
    return await _channel.invokeMethod("showRewardAd");
  }

}
