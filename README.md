<p align="center">
<img src=https://github.com/oywenjiao/toponad/blob/master/showImage/1.png alt="drawing" width="700">
</p>

<h1 align="center">Toponad</h1>

<p>
<a href="https://www.npmjs.com/package/drone"><img src=https://img.shields.io/badge/license-MIT-brightgreen></a>
<a href="https://www.apple.com/lae/ios/ios-13/"><img src=https://img.shields.io/badge/platform-ios-lightgrey></a>
<a href="https://www.Android.com/package/drone"><img src=https://img.shields.io/badge/platform-Android-lightgrey></a>
<a href="https://www.dart.dev"><img src=https://img.shields.io/badge/Language-Dart-orange></a>
<a href="https://www.flutter.dev"><img src=https://img.shields.io/badge/Flutter-v1.12.13-informational></a>
<a href="https://www.dart.dev"><img src=https://img.shields.io/badge/Dart-v2.4.1-informational></a>
<a href="https://github.com/oywenjiao/toponad"><img src=https://img.shields.io/badge/Topon-v0.0.2-success></a>
</p>

## 前言
⚠️在使用本插件前请认真，仔细阅读[TopOn官方文档](https://docs.toponad.com/#/zh-cn/android/GetStarted/TopOn_Get_Started)。本插件将尽量保留SDK内容和各API相关内容，如出现在官方文档以外报错信息可以留言issue,或通过文末联系方式联系作者（注明来意）。

## 简介
Toponad是一款Flutter插件，集成了TopOn 广告聚合平台的Android和iOS的SDK，方便开发者直接在Flutter层面调用相关方法。

## 插件开发环境相关

### Flutter
```
Flutter (Channel stable, v1.12.13+hotfix.9)
```

### Dart
```
Dart VM version: 2.7.2 on "macos_x64"
```

### Platform
```
Xcode - develop for iOS and macOS (Xcode 11.2)
Android Studio (version 3.6)
```


## 安装
```yaml
# add this line to your dependencies
dependencies:
  toponad:
    git:
      url: https://github.com/oywenjiao/toponad.git
```

## 环境配置
使用前请确认您已根据TopOn的官方文档中的步骤进行了相应的依赖添加，权限获取以及参数配置

## Toponad集成
### Android
在Android端你可能需要简单的进行配置已导入TopOn SDK, 具体步骤已为你写好请戳👉
#### [Toponad Android集成手册](https://github.com/oywenjiao/toponad/blob/master/AndroidProfile.md)

## 开始使用
### 初始化（register）
调用TopOn SDK的第一步是对SDK的初始化

```dart
await Toponad.registerToponad(
        appId: "Your AppID",
        appKey: "Your AppKey",
    )
```

#### 参数说明
| 参数  | 描述  | 默认值 |
| :------------ |:---------------:| -----:|
| appId      | 在TopOn 聚合平台注册的自己的AppId | null |
| appKey  | 在TopOn 聚合平台注册的自己的AppKey       |    null |

#### 接入成功debug信息
* Android
```
********************************** Network Integration Status *************************************
----------------------------------------
NetworkName: Admob //聚合的广告平台的名字
SDK: VERIFIED //验证聚合SDK是否正确，正确则显示VERIFIED，否则显示NOT VERIFIED
Dependence Plugin: VERIFIED //验证广告平台依赖的插件是否存在，正确则显示VERIFIED，否则显示缺少的配置
Activities : VERIFIED //验证广告平台的Activity声明是否存在，正确则显示VERIFIED，否则显示缺少的配置
Providers : VERIFIED  //验证广告平台的Provider声明是否存在，正确则显示VERIFIED，否则显示缺少的配置
Status: Success //验证广告平台是否全部集成正确，正确则显示Success，否则显示Fail
----------------------------------------
NetworkName: Facebook
SDK: VERIFIED
Dependence Plugin: VERIFIED
Activities : VERIFIED
Services : VERIFIED
Providers : VERIFIED
Status: Success
********************************** Network Integration Status *************************************
```

⚠️进行下一步操作前请确认，TopOn已经成功接入并且检测正常。

### 加载激励视频
```dart
    await Toponad.loadRewardAd(
      mCodeId: "Your CodeId",
        );
```
#### 参数说明
| 参数  | 描述  | 默认值 |
| :------------ |:---------------:| -----:|
| mCodeId      | 自己的广告位id | null |


### 激励视频回调监听
在合适的位置注册你的监听，保证用户看完广告时接收到我给你的回调信息，并做下一步处理
```dart
Toponad.toponadResponseEventHandler.listen((value)
    {
      if(value is Toponad.OnRewardResponse)
        {
          if (value.callType == "onRewardedVideoAdClosed") {
            print('激励视频关闭回调');
          }
        }
      else
        {
          print("回调类型不符合");
        }
    });
```
#### 参数说明
| 参数  | 描述  | 默认值 |
| :------------ |:---------------:| -----:|
| callType      | 回调类别 | / |
| rewardRes  | 回调结果      |    / |

#### callType 回调类别参数说明
| 参数值 | 描述 | 回调信息 |
| :----: | :----: | :-----: |
| onRewardedVideoAdFailed | 广告加载失败 | AdError |
| onRewardedVideoAdPlayStart | 广告刷新回调 | ATAdInfo |
| onRewardedVideoAdPlayEnd | 广告播放结束 | ATAdInfo |
| onRewardedVideoAdPlayFailed | 广告播放失败 | ATAdInfo |
| onRewardedVideoAdClosed | 广告关闭回调 | ATAdInfo |
| onRewardedVideoAdPlayClicked | 广告点击回调 | ATAdInfo |
| onReward | 下发激励视频回调 | ATAdInfo |

#### ATAdInfo 回调参数说明
| 参数值 | 描述 |
| :----: | :----: |
| id | 获取 每次展示广告时生成的独立ID |
| publisher_revenue | 获取 展示收益 |
| currency | 获取 货币单位 |
| country | 获取 国家代码 |
| adunit_id | 获取 TopOn广告位ID |
| adunit_format | 获取 广告类型，包括："Native"、"RewardedVideo"、"Banner" 、"Interstitial"、"Splash" |
| precision | 获取 ECPM精度 |
| network_type | 获取 Network类型、"Network"：第三方广告平台、"Cross_Promotion"：交互推广、"Adx"：暂未支持 |
| network_placement_id | 获取 Network的广告位ID |
| ecpm_level | 获取 广告源的eCPM层级，头部竞价广告源默认为0 |
| segment_id | 获取 流量分组ID |
| scenario_id | 获取 广告场景ID，仅Rewarded Video&Interstitial支持 |
| scenario_reward_name | 获取 广告场景的激励名称，仅Rewarded Video支持 |
| scenario_reward_number | 获取 广告场景的激励数量，仅Rewarded Video支持 |
| channel | 获取渠道信息 |
| sub_channel | 获取 子渠道信息 |
| custom_rule | 获取 Placement+App维度的自定义规则的Json字符串 |
| network_firm_id | 获取 广告平台对应的ID，用于区分广告平台 |
| adsource_id | 获取 广告源ID |
| adsource_index | 获取 当前广告源在WaterFall中的排序（从0开始，0优先级最高) |
| adsource_price | 获取 eCPM |
| adsource_isheaderbidding | 是否为头部竞价的广告源，1：是，2：否 |




## 个人联系方式
* QQ:282229505<br/>
* Wechat:282229505<br/>