package org.xuxiaoxiao.myyora.infrastructure;

import android.os.Handler;

import java.util.ArrayList;
import java.util.HashMap;

public class ActionScheduler {
    private final YoraApplication _application;
    private final Handler _handler;
    private final ArrayList<TimedCallback> _timedCallbacks;
    private final HashMap<Class, Runnable> _onResumeActions;
    private boolean _isPaused;


    public ActionScheduler(YoraApplication application) {
        _application = application;
        _handler = new Handler();
        _timedCallbacks = new ArrayList<>();
        _onResumeActions = new HashMap<>();
    }

    public void onPause() {
        _isPaused = true;
    }

    public void onResume() {
        _isPaused = false;

        for (TimedCallback callback : _timedCallbacks) {
            callback.schedule();
        }

        for (Runnable runnable : _onResumeActions.values()) {
            runnable.run();
        }
        _onResumeActions.clear();
    }

    public void invokeOnResume(Class cls, Runnable runnable) {
        if (!_isPaused) {
            runnable.run();
            return;
        }
        // Only the last runnable of a class should be stored and previous runnables should be
        // discarded. Using a HashMap helps us to achieve this behavior.
        _onResumeActions.put(cls, runnable);
    }

    public void postDelayed(Runnable runnable, long milliseconds){
        _handler.postDelayed(runnable, milliseconds);
    }

    public void invokeEveryMilliseconds(Runnable runnable, long milliseconds) {
        invokeEveryMilliseconds(runnable, milliseconds, true);
    }

    public void invokeEveryMilliseconds(Runnable runnable, long milliseconds, boolean runImmediately) {
        TimedCallback callback = new TimedCallback(runnable, milliseconds);
        _timedCallbacks.add(callback);
        if (runImmediately) {
            callback.run();
        } else {
            postDelayed(callback, milliseconds);
        }
    }

    public void postEveryMilliseconds(Object request, long milliseconds) {
        postEveryMilliseconds(request, milliseconds, true);

    }

    public void postEveryMilliseconds(final Object request, long milliseconds, boolean postImmediately) {
        invokeEveryMilliseconds(new Runnable() {
            @Override
            public void run() {
                _application.getBus().post(request);
            }
        }, milliseconds, postImmediately);
    }

    private class TimedCallback implements Runnable {
        private final Runnable _runnable;
        private final long _delay;

        public TimedCallback(Runnable runnable, long delay) {
            _runnable = runnable;
            _delay = delay;
        }

        @Override
        public void run() {
            if (_isPaused)
                return;
            _runnable.run();
            schedule();
        }

        public void schedule(){
            _handler.postDelayed(this, _delay);
        }
    }
}
/*

这是一种可以创建多线程消息的函数
使用方法：
1，首先创建一个Handler对象
Handler handler=new Handler();
2，然后创建一个Runnable对象
Runnable runnable=new Runnable(){
   @Override
   public void run() {
    // TODO Auto-generated method stub
    //要做的事情，这里再次调用此Runnable对象，以实现每两秒实现一次的定时器操作
    handler.postDelayed(this, 2000);
   }
};
3，使用PostDelayed方法，两秒后调用此Runnable对象
handler.postDelayed(runnable, 2000);
实际上也就实现了一个2s的一个定时器
4，如果想要关闭此定时器，可以这样操作
handler.removeCallbacks(runnable);

当然，你也可以做一个闹钟提醒延时的函数试试，比如，先用MediaPlayer播放闹钟声音，
如果不想起，被停止播放之后，下次就5分钟后再播放，再被停止的话，下次就4分钟后播放，
………………
只要更改延时的时间就可以实现了，用一个static对象的话会比较容易操作。

 */
