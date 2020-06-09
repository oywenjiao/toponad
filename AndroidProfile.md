<h1 align="center">Toponad Android集成手册</h1>

## 在AndroidManifest.xml里增加配置

### 1. 增加xml

```dart
    <application
      ...
      android:networkSecurityConfig="@xml/network_security_config"
    >
```

### 2. 增加xml文件

> res/xml/network_security_config.xml

```dart
    <?xml version="1.0" encoding="utf-8"?>
    <network-security-config>
        <base-config cleartextTrafficPermitted="true" />
    </network-security-config>
```

### 3. 增加配置内容

```dart
    <application>
      ...
      <uses-library android:name="org.apache.http.legacy" android:required="false"/>
      ...
    </application>
```


## 使用腾讯广告时需增加的配置

### 1. 增加xml文件

> res/xml/anythink_bk_gdt_file_path.xml

```dart
<paths xmlns:android="http://schemas.android.com/apk/res/android">
    <external-path name="gdt_sdk_download_path" path="GDTDOWNLOAD" />
    <root-path name="root" path="" />
</paths>
```

### 2. 增加配置内容

```dart
    <application>
      ...
      <service
          android:name="com.qq.e.comm.DownloadService"
          android:exported="false" />

      <activity
          android:name="com.qq.e.ads.ADActivity"
          android:configChanges="keyboard|keyboardHidden|orientation|screenSize" />

      <activity
          android:name="com.qq.e.ads.PortraitADActivity"
          android:configChanges="keyboard|keyboardHidden|orientation|screenSize"
          android:screenOrientation="portrait" />
      <activity
          android:name="com.qq.e.ads.LandscapeADActivity"
          android:configChanges="keyboard|keyboardHidden|orientation|screenSize"
          android:screenOrientation="landscape" />

      <provider
          android:name="com.anythink.network.gdt.GDTATFileProvider"
          android:authorities="${applicationId}.fileprovider"
          android:exported="false"
          android:grantUriPermissions="true">
          <meta-data
              android:name="android.support.FILE_PROVIDER_PATHS"
              android:resource="@xml/anythink_bk_gdt_file_path" />
      </provider>
      ...
    </application>
```

## 使用穿山甲广告需增加的配置

### 1. 增加xml 文件

> res/xml/anythink_bk_tt_file_path.xml

```dart
    <?xml version="1.0" encoding="utf-8"?>
    <paths>
        <external-files-path name="external_files_path" path="Download" />
        <external-path name="tt_external_download" path="Download" />
        <files-path name="tt_internal_file_download" path="Download" />
        <cache-path name="tt_internal_cache_download" path="Download" />
        <external-path name="tt_external_root" path="." />
        <root-path name="root" path="" />
    </paths>
```

### 2. 增加配置内容

```dart
    <application>
      ...
      <provider
          android:name="com.bytedance.sdk.openadsdk.multipro.TTMultiProvider"
          android:authorities="${applicationId}.TTMultiProvider"
          android:exported="false" />
      <provider
          android:name="com.bytedance.sdk.openadsdk.TTFileProvider"
          android:authorities="${applicationId}.TTFileProvider"
          android:exported="false"
          android:grantUriPermissions="true">
          <meta-data
              android:name="android.support.FILE_PROVIDER_PATHS"
              android:resource="@xml/anythink_bk_tt_file_path" />
      </provider>
      ...
    </application>
```

## 使用百度广告需要增加的配置

### 1. 增加 xml 文件

> res/xml/anythink_bk_baidu_file_path.xml

```dart
    <paths xmlns:android="http://schemas.android.com/apk/res/android">
        <external-files-path
            name="bdpath"
            path="bddownload/" />
        <external-path
            name="bdpathsd "
            path="bddownload/" />
    </paths>
```

### 2. 增加配置内容

```dart
    <application>
      ...
      <activity
          android:name="com.baidu.mobads.AppActivity"
          android:configChanges="screenSize|keyboard|keyboardHidden|orientation"
          android:theme="@android:style/Theme.Translucent.NoTitleBar" />

      <provider
          android:name="com.baidu.mobads.openad.BdFileProvider"
          android:authorities="${applicationId}.bd.provider"
          android:exported="false"
          android:grantUriPermissions="true">
          <meta-data
              android:name="android.support.FILE_PROVIDER_PATHS"
              android:resource="@xml/anythink_bk_baidu_file_path" />
      </provider>
      ...
    </application>
```