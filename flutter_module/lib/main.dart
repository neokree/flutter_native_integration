import 'package:flutter/material.dart';
import 'src/business_logic.dart';

var _businessLogic = BusinessLogic();
void main() {
  print('main called');

  _businessLogic.initialize();
  print('business logic initialized');

  // The app seems to be required to keep the server active
  runApp(MyApp());
  print('app started');
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return Text('nothing to see here', textDirection: TextDirection.ltr,);
  }
}
