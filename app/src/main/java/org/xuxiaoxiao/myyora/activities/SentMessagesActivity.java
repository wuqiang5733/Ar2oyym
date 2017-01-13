package org.xuxiaoxiao.myyora.activities;

import android.os.Bundle;

import org.xuxiaoxiao.myyora.R;
import org.xuxiaoxiao.myyora.views.MainNavDrawer;

public class SentMessagesActivity extends BaseAuthenticatedActivity {
    @Override
    protected void onYoraCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sent_message);
        setNavDrawer(new MainNavDrawer(this));
    }
}
