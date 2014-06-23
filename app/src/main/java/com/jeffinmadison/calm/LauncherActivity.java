package com.jeffinmadison.calm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.jeffinmadison.calm.R;

public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final boolean hasUserSeenIntro = true;

        final Class<? extends Activity> activityClass;
        if (hasUserSeenIntro)
            activityClass = SceneSelectorActivity.class;
        else
            activityClass = IntroductionActivity.class;

        Intent newActivity = new Intent(this, activityClass);
        startActivity(newActivity);
        finish();
    }
}
