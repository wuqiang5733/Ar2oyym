package org.xuxiaoxiao.myyora.infrastructure;

import android.app.Application;
import android.util.Log;

import com.squareup.otto.Bus;

import org.xuxiaoxiao.myyora.services.Module;

/**
 * Created by WuQiang on 2017/1/5.
 *
 * 这个类，是可以写成 Singleton 的形式的 。 
 * 这是一个在整个APP运行期间都会存在的类，
 * 所以可以保存一些信息，比如：用户是否登陆
 * 这是整个程序的起点，比第一个 Activity 都要早
 *
 */

public class YoraApplication extends Application { // 继承自 Application ， 才能在 BaseActivity 当中 强制转换
    private Auth _auth;
    private Bus _bus;

// It's also correct; but because object has not been completely initiallized at this point,
// it may cause problem in Auth.
//    public YoraApplication() {
//        _auth = new Auth(this);
//    }

    @Override
    public void onCreate() {
        Log.e("YoraApplication","YoraApplication");
        super.onCreate();
        // 给 Auth 传入的是 Application 级的 Context
        // 而不是 Activity 级的
        _auth = new Auth(this);
        _bus = new Bus();
        Module.register(this);
    }

    public Auth getAuth() {
        return _auth;
    }
    public Bus getBus() {
        return _bus;
    }
}
