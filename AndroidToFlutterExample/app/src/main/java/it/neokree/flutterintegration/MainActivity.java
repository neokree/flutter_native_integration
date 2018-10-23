package it.neokree.flutterintegration;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.view.FlutterMain;
import io.flutter.view.FlutterNativeView;
import io.flutter.view.FlutterRunArguments;


public class MainActivity extends AppCompatActivity {

    private static MethodChannel channel;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupFlutter(this);
    }

    private void setupFlutter(AppCompatActivity activity) {
        FlutterMain.startInitialization(activity.getApplicationContext());
        FlutterMain.ensureInitializationComplete(activity.getApplicationContext(), null);
        final FlutterNativeView nativeView = new FlutterNativeView(activity);

        final FlutterRunArguments arguments = new FlutterRunArguments();
        arguments.bundlePath = FlutterMain.findAppBundlePath(activity.getApplicationContext());
        arguments.entrypoint = "main";
        nativeView.runFromBundle(arguments);
        GeneratedPluginRegistrant.registerWith(nativeView.getPluginRegistry());

        channel = new MethodChannel(nativeView, "neokree.it/integration");
        channel.setMethodCallHandler(new MethodChannel.MethodCallHandler() {
            @Override
            public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
                Log.d(TAG, "methodCall : method=" + methodCall.method + " : arguments=" + methodCall.arguments);
                result.success("message arrived");

                // Flutter service started, invoke job call
                invokeJob();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void invokeJob() {
        channel.invokeMethod("doJob", null, new MethodChannel.Result() {
            @Override
            public void success(Object o) {
                String response = (String)o;
                Log.d(TAG, String.format("channel: %s", response));
            }

            @Override
            public void error(String errorCode, @Nullable String errorMessage, @Nullable Object errorDetails) {
                Log.e(TAG, String.format("channel : %s - %s", errorCode, errorMessage));
            }

            @Override
            public void notImplemented() {
                Log.wtf(TAG, "channel : Not implemented");
            }
        });
    }

}
