
import 'dart:async';

import 'package:flutter/cupertino.dart';
import 'package:flutter/services.dart';
import 'package:toponad/toponad_response.dart';

MethodChannel _channel = MethodChannel("com.cat.topponad")
  ..setMethodCallHandler(_methodHandler);

StreamController<BaseToponadResponse> _toponadResponseEventHandlerController =
new StreamController.broadcast();

Stream<BaseToponadResponse> get toponadResponseEventHandler =>
    _toponadResponseEventHandlerController.stream;

Future<String> platformVersion() async {
  final String version = await _channel.invokeMethod('getPlatformVersion');
  return version;
}

Future<void> registerToponad({
  @required String appId,
  @required String appKey
}) async {
  return await _channel.invokeMethod("register", {
    "appid": appId,
    "appKey": appKey
  });
}

Future loadRewardAd({@required String mCodeId}) async {
  return await _channel.invokeMethod("loadRewardAd", {"mCodeId": mCodeId});
}

Future<void> showRewardAd() async {
  return await _channel.invokeMethod("showRewardAd");
}

Future _methodHandler(MethodCall methodCall) {
  var response =
  BaseToponadResponse.create(methodCall.method, methodCall.arguments);
  _toponadResponseEventHandlerController.add(response);
  return Future.value();
}