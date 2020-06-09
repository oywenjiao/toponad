
import 'dart:async';

import 'package:flutter/cupertino.dart';
import 'package:flutter/services.dart';
import 'package:toponad/toponad_response.dart';

/*****************************************************************************
 ***           Flutter 与 原生通信操作                                       ***
 *---------------------------------------------------------------------------*
 * Functions
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

/*
**	设置隧道信息 并配置操作方法 打通隧道
*/
MethodChannel _channel = MethodChannel("com.cat.topponad")
  ..setMethodCallHandler(_methodHandler);

/*
**  Flutter 接收原生发送的数据
 */
StreamController<BaseToponadResponse> _toponadResponseEventHandlerController =
new StreamController.broadcast();

Stream<BaseToponadResponse> get toponadResponseEventHandler =>
    _toponadResponseEventHandlerController.stream;

/*
**  获取系统版本号
 */
Future<String> platformVersion() async {
  final String version = await _channel.invokeMethod('getPlatformVersion');
  return version;
}

/*
**  初始化及注册 TopOn SDK
**  appid: TopOn 平台账号的apppid
**  appKey: TopOn 平台账号的appKey
 */
Future<void> registerToponad({
  @required String appId,
  @required String appKey
}) async {
  /*
  ** 通过隧道 指向匹配的方法并发送请求
   */
  return await _channel.invokeMethod("register", {
    "appid": appId,
    "appKey": appKey
  });
}

/*
** 初始化并加载激励视频
** mCodeId: 各广告平台对应激励视频广告位ID
 */
Future loadRewardAd({@required String mCodeId}) async {
  return await _channel.invokeMethod("loadRewardAd", {"mCodeId": mCodeId});
}

/*
** 读取并展示激励视频广告
 */
Future<void> showRewardAd() async {
  return await _channel.invokeMethod("showRewardAd");
}

/*
** 监听原生发送的数据
 */
Future _methodHandler(MethodCall methodCall) {
  var response =
    BaseToponadResponse.create(methodCall.method, methodCall.arguments);
  _toponadResponseEventHandlerController.add(response);
  return Future.value();
}