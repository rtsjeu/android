package ch.rts.mobile.le.jeu.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by npietri on 02.02.17.
 */

public class RulesActivity extends AppCompatActivity {

    public static void start(Activity activity) {
        start(activity, null);
    }

    public static void start(Activity activity, Bundle options) {
        Intent intent = new Intent(activity, RulesActivity.class);
        ActivityCompat.startActivity(activity, intent, options);
    }

}
