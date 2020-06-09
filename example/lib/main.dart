import 'dart:convert';

import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:toponad/toponad.dart' as Toponad;

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
    Toponad.toponadResponseEventHandler.listen((value) {
      if (value is Toponad.OnRewardResponse) {
        if (value.callType == "onRewardedVideoAdClosed") {
          print("点击关闭广告 回调");
        } else if (value.callType == "onRewardedVideoAdPlayEnd") {
          Map<String, dynamic> entity = jsonDecode(value.rewardRes);
          print("广告播放回调，广告类型: ${entity['adunit_format']}");
        }
      }
      else {
        print("回调类型不符合");
      }
    });
  }


  Future<void> initPlatformState() async {
    String platformVersion;

    try {
      platformVersion = await Toponad.platformVersion();
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });

    /// 官方配置项
    final String appId = "a5aa1f9deda26d";
    final String appKey = "4f7b9ac17decb9babec83aac078742c7";

    /// 初始化 SDK
    await Toponad.registerToponad(appId: appId, appKey: appKey).then((v) {
      _loadRewardAd();
    });
  }

  _loadRewardAd () async {
    String mCodeId = "b5b728e7a08cd4";
    await Toponad.loadRewardAd(mCodeId: mCodeId);
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            children: <Widget>[
              SizedBox(height: 50,),
              Text('Running on: $_platformVersion\n'),
              SizedBox(height: 50,),
              InkWell(
                onTap: () async {
                  await Toponad.showRewardAd();
                },
                child: Container(
                  decoration: BoxDecoration(
                    color: Colors.blueAccent,
                    borderRadius: BorderRadius.circular(10)
                  ),
                  padding: EdgeInsets.symmetric(horizontal: 15, vertical: 6),
                  child: Text('点击查看广告', style: TextStyle(color: Colors.white),),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
