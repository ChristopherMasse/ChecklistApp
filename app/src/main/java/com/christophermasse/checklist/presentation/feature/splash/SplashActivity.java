package com.christophermasse.checklist.presentation.feature.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.christophermasse.checklist.presentation.feature.task_list.TaskListActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Shows a splash screen on app startup
 */
public class SplashActivity extends AppCompatActivity {

    private static int MIN_SPLASH_TIME = 1700;

    private Handler handler;

    private Runnable runnable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler();
        runnable = this::openMedicineList;

        handler.postDelayed(runnable, MIN_SPLASH_TIME);
    }

    private void openMedicineList() {
        Intent intent = new Intent(SplashActivity.this, TaskListActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && runnable != null){
            handler.removeCallbacks(runnable);
            handler = null;
            runnable = null;
        }
    }
}
