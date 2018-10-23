import 'package:flutter/services.dart';

const MethodChannel _channel = MethodChannel('neokree.it/integration');

class BusinessLogic {
  const BusinessLogic();

  void initialize() async {
    _channel.setMethodCallHandler((MethodCall call) async {
      print('received call ${call.method} with arguments: ${call.arguments}');
      return 'Job done';
    });
    
    var response = await _channel.invokeMethod("onInit", null);
    print('response: ${response}');
  }

}
