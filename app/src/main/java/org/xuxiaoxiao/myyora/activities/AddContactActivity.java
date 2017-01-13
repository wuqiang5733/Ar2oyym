package org.xuxiaoxiao.myyora.activities;


import android.os.Bundle;

import org.xuxiaoxiao.myyora.R;


public class AddContactActivity extends BaseAuthenticatedActivity {
    public static final String RESULT_CONTACT = "RESULT_CONTACT";

    @Override
    protected void onYoraCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_contact);
    }
}
