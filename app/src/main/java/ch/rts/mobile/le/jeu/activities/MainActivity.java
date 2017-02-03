package ch.rts.mobile.le.jeu.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ch.rts.mobile.le.jeu.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.play)
    Button play;
    @BindView(R.id.rules)
    Button rules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_MainActivity);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.play)
    public void onPlayClick() {
        GameActivity.start(this);
    }

    @OnClick(R.id.rules)
    public void onRulesClick() {
        RulesActivity.start(this);
    }

}
