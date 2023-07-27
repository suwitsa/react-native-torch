/**
 * Created by Ludo van den Boom <ludo@cubicphuse.nl> on 06/04/2017.
 */

package com.cubicphuse.RCTTorch;

import android.hardware.Camera;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class RCTTorchModule extends ReactContextBaseJavaModule {
    private final ReactApplicationContext myReactContext;
    private Boolean isTorchOn = false;
    private Camera camera;

    public RCTTorchModule(ReactApplicationContext reactContext) {
        super(reactContext);

        // Need access to reactContext to check for camera
        this.myReactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RCTTorch";
    }

    @ReactMethod
    public void switchState(Boolean newState, Callback successCallback, Callback failureCallback) {
        Camera.Parameters params;

        if (newState && !isTorchOn) {
            camera = Camera.open();
            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isTorchOn = true;
        } else if (isTorchOn) {
            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);

            camera.setParameters(params);
            camera.stopPreview();
            camera.release();
            isTorchOn = false;
        }
    }
}
