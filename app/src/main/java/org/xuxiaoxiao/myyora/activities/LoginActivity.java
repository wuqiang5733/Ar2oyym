package org.xuxiaoxiao.myyora.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.squareup.otto.Subscribe;

import org.xuxiaoxiao.myyora.R;
import org.xuxiaoxiao.myyora.fragments.LoginFragment;
import org.xuxiaoxiao.myyora.services.Account;

/*
E/YoraApplication: YoraApplication
E/Auth: Auth
E/BaseAuthenActivity: BaseAuthenticatedActivity
E/BaseActivity: BaseActivity

E/LoginActivity: LoginActivity
E/BaseActivity: BaseActivity

D/LoginActivity: 你登陆了 。。。
E/BaseAuthenActivity: BaseAuthenticatedActivity
E/BaseActivity: BaseActivity
E/MainActivity: MainActivity
 */


public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginFragment.Callbacks {
    private static final int REQUEST_NARROW_LOGIN = 1;
    private static final int REQUEST_REGISTER = 2;
    private static final int REQUEST_EXTERNAL_LOGIN = 3;

    private View _loginButton;
    private View _registerButton;
    private View _facebookLoginButton;
    private View _googleLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Log.e("LoginActivity", "LoginActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _loginButton = findViewById(R.id.activity_login_login);
        _registerButton = findViewById(R.id.activity_login_register);
        _facebookLoginButton = findViewById(R.id.activity_login_facebook);
        _googleLoginButton = findViewById(R.id.activity_login_google);
        if (_loginButton != null) {
            _loginButton.setOnClickListener(this);
        }
        _registerButton.setOnClickListener(this);
        _facebookLoginButton.setOnClickListener(this);
        _googleLoginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // 检测不同的按钮启动不同的 Activity
        // (LoginNarrowActivity，RegisterActivity，ExternalLoginActivity) ，
        // 然后它们返回不同的请求码
        // （REQUEST_NARROW_LOGIN，REQUEST_REGISTER，REQUEST_EXTERNAL_LOGIN）
        if (view == _loginButton) {
            startActivityForResult(new Intent(this, LoginNarrowActivity.class), REQUEST_NARROW_LOGIN);
        } else if (view == _registerButton) {
            startActivityForResult(new Intent(this, RegisterActivity.class), REQUEST_REGISTER);
        } else if (view == _facebookLoginButton) {
            doExternalLogin("Facebook");
        } else if (view == _googleLoginButton) {
            doExternalLogin("Google");
        }
    }

    private void doExternalLogin(String externalService) {
        Intent intent = new Intent(this, ExternalLoginActivity.class);
        intent.putExtra(ExternalLoginActivity.EXTRA_EXTERNAL_SERVICE, externalService);
        startActivityForResult(intent, REQUEST_EXTERNAL_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        if (requestCode == REQUEST_NARROW_LOGIN ||
                requestCode == REQUEST_REGISTER ||
                requestCode == REQUEST_EXTERNAL_LOGIN)
            finishLogin();
    }

    private void finishLogin() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    // 这个是在 LoginFragment 当中触发的
    public void onLoggedIn() {
        finishLogin();
    }

    @Subscribe // 这个是我自己改的，因为 onAttach 不执行
    public void casual(Account.myinterface wuqiang) {
        finishLogin();
    }
}
