package com.application.sutdup.Library.ui;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;

public interface Vibrator {
    public default void vibrate(){}
}
