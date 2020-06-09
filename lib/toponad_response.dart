
const String _errCode = "errCode";
const String _errStr = "errStr";

typedef BaseToponadResponse _ToponadResponseInvoker(Map argument);

/*
** 映射原生发送的数据给响应池
 */
Map<String, _ToponadResponseInvoker> _nameAndResponseMapper = {
  "onRewardResponse": (Map argument) =>
      OnRewardResponse.fromMap(argument),
};

class BaseToponadResponse {
  final int errCode;
  final String errStr;

  bool get isSuccessful => errCode == 0;

  BaseToponadResponse._(this.errCode, this.errStr);

  /// 创建响应池
  factory BaseToponadResponse.create(String name, Map argument) =>
      _nameAndResponseMapper[name](argument);
}

class OnRewardResponse extends BaseToponadResponse {
  final String callType;
  final String rewardRes;

  OnRewardResponse.fromMap(Map map)
      : callType = map["callType"],
        rewardRes = map["rewardRes"],
        super._(map[_errCode], map[_errStr]);
}