package org.xuxiaoxiao.myyora.activities;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by WuQiang on 2017/1/5.
 */

public abstract class BaseAuthenticatedActivity extends BaseActivity {
    @Override  // 加了 final 也就是这的子类不能 Override 了，只能是在 onYoraCreate 当中做文章
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!application.getAuth().getUser().isLoggedIn()) {
            //下面是自动登陆的部分
            if (application.getAuth().hasAuthToken()) {
                Intent intent = new Intent(this, AuthenticationActivity.class);
                // 下面的 getClass().getName() 是为了在自动登陆之后，回到之前的 Activity
                intent.putExtra(AuthenticationActivity.EXTRA_RETURN_TO_ACTIVITY, getClass().getName());
                startActivity(intent);
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }

            finish();
            return;
        }
        onYoraCreate(savedInstanceState); //经典，这样，就只有登陆之后，才会在子类当中运行程序
    }

    protected abstract void onYoraCreate(Bundle savedInstanceState);
    // 这个也有助于写程序 ，因为继承自这个类，会让你 Override 这个方法
}
