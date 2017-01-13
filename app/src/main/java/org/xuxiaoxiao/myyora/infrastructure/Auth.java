package org.xuxiaoxiao.myyora.infrastructure;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import org.xuxiaoxiao.myyora.activities.LoginActivity;

/**
 * Created by WuQiang on 2017/1/5.
 */

public class Auth {
    private static final String AUTH_PREFERENCES = "AUTH_PREFERENCES";
    private static final String AUTH_PREFERENCES_TOKEN = "AUTH_PREFERENCES_TOKEN";

    private final Context _context;
    private final SharedPreferences _preferences;

    private User _usre;
    private String _authToken;

    public Auth(Context context) {
        Log.e("Auth", "Auth");
        // 给 Auth 传入的是 Application 级的 Context
        // 而不是 Activity 级的
        this._context = context;
        this._usre = new User();
        // 名字
        _preferences = context.getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE);
        // 如果找不到 返回 null
        _authToken = _preferences.getString(AUTH_PREFERENCES_TOKEN, null);
    }

    public User getUser() {
        return _usre;
    }

    public String getAuthToken() {
        return _authToken;
    }

    public boolean hasAuthToken() {
        return _authToken != null && !_authToken.isEmpty();
    }

    public void setAuthToken(String authToken) {
        _authToken = authToken;

        SharedPreferences.Editor editor = _preferences.edit();
        editor.putString(AUTH_PREFERENCES_TOKEN, authToken);
        editor.commit();
    }

    public void logout() {
        setAuthToken(null);

        Intent loginIntent = new Intent(_context, LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(loginIntent);
    }
}
