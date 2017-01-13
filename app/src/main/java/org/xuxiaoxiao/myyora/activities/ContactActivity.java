package org.xuxiaoxiao.myyora.activities;

import android.os.Bundle;

import org.xuxiaoxiao.myyora.R;


public class ContactActivity extends BaseAuthenticatedActivity {
    public static final String EXTRA_USER_DETAILS = "EXTRA_USER_DETAILS";

    @Override
    protected void onYoraCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_contact);
    }
}
