package org.xuxiaoxiao.myyora.activities;

import android.os.Bundle;

import org.xuxiaoxiao.myyora.R;
import org.xuxiaoxiao.myyora.fragments.LoginFragment;

public class LoginNarrowActivity extends BaseActivity implements LoginFragment.Callbacks {
// 手机屏幕的话，这是一个独立的界面，平板的话，是一部分
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activty_login_narrow);
    }

    @Override
    public void onLoggedIn() {
        setResult(RESULT_OK);
        finish();
    }
}
