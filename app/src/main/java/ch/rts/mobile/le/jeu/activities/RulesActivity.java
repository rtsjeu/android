package ch.rts.mobile.le.jeu.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.rts.mobile.le.jeu.R;

/**
 * Created by npietri on 02.02.17.
 */

public class RulesActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_MainActivity);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setDisplayUseLogoEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    public static void start(Activity activity) {
        start(activity, null);
    }

    public static void start(Activity activity, Bundle options) {
        Intent intent = new Intent(activity, RulesActivity.class);
        ActivityCompat.startActivity(activity, intent, options);
    }

}
