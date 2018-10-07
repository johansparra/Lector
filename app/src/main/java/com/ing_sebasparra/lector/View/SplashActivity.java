package com.ing_sebasparra.lector.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ing_sebasparra.lector.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
