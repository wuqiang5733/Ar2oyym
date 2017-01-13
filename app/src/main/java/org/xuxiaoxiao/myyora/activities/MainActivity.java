package org.xuxiaoxiao.myyora.activities;

import android.os.Bundle;
import android.util.Log;

import org.xuxiaoxiao.myyora.R;
import org.xuxiaoxiao.myyora.views.MainNavDrawer;

public class MainActivity extends BaseAuthenticatedActivity {


    @Override
    protected void onYoraCreate(Bundle savedInstanceState) {
        Log.i("MainActivity","刚刚进入onYoraCreate，调用setContentView之前");
        setContentView(R.layout.activity_main);
        Log.i("MainActivity","onYoraCreate，执行完setContentView之后");
        getSupportActionBar().setTitle(getString(R.string.ActivityInbox));
        setNavDrawer(new MainNavDrawer(this));
    }
}
