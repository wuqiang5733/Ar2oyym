package org.xuxiaoxiao.myyora.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.otto.Subscribe;

import org.xuxiaoxiao.myyora.R;
import org.xuxiaoxiao.myyora.services.Account;

public class LoginFragment extends BaseFragment implements View.OnClickListener {
    // 从主界面（四个启动项的那个界面）的第一个启动项启动的那个 Fragment，不包括界面的头部
    private Button _loginButton;
    private Callbacks _callbacks;

    private View _progressBar;
    private EditText _userNameText;
    private EditText _passwordText;
    private String _defaultLoginButtonText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 第三个参数：false 是 attachToRoot ，好像已经自动 attach 了，如果你再 attach 一次就会报错
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        _loginButton = (Button) view.findViewById(R.id.fragment_login_loginButton);
        _loginButton.setOnClickListener(this);

        _defaultLoginButtonText = _loginButton.getText().toString();
        _userNameText = (EditText) view.findViewById(R.id.fragment_login_userName);
        _passwordText = (EditText) view.findViewById(R.id.fragment_login_password);
        _progressBar = view.findViewById(R.id.activity_login_progressBar);
        _progressBar.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == _loginButton) {
            _progressBar.setVisibility(View.VISIBLE);
            _loginButton.setText("");
            _loginButton.setEnabled(false);
            _userNameText.setEnabled(false);
            _passwordText.setEnabled(false);

            bus.post(new Account.LoginWithUserNameRequest(
                    _userNameText.getText().toString(),
                    _passwordText.getText().toString()));

        }
    }

    @Subscribe
    public void onLoginWithUserName(Account.LoginWithUserNameResponse response) {
        _progressBar.setVisibility(View.GONE);
        _loginButton.setText(_defaultLoginButtonText);
        _loginButton.setEnabled(true);
        _userNameText.setEnabled(true);
        _passwordText.setEnabled(true);

        response.showErrorToast(getActivity());
        _userNameText.setError(response.getPropertyError("userName"));
        _passwordText.setError(response.getPropertyError("password"));

        if (response.didSucceed())
//            _callbacks.onLoggedIn();
            bus.post(new Account.myinterface());  // 这个是我自己改的，因为 onAttach 不执行
    }

    @Override
    //import android.app.Fragment;  // 调用这个版本 onAttach 不执行，可能是25就不行了
    public void onAttach(Context context) {  // 这个不知道为什么不执行
        super.onAttach(context);
        _callbacks = (Callbacks) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _callbacks = null;
    }

    // Observer Pattern
    public interface Callbacks {
        void onLoggedIn();
    }
}
