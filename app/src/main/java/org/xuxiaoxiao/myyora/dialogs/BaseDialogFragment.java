package org.xuxiaoxiao.myyora.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;

import com.squareup.otto.Bus;

import org.xuxiaoxiao.myyora.infrastructure.ActionScheduler;
import org.xuxiaoxiao.myyora.infrastructure.YoraApplication;


public abstract class BaseDialogFragment extends DialogFragment {
    protected YoraApplication application;
    protected Bus bus;
    protected ActionScheduler scheduler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (YoraApplication) getActivity().getApplication();

        scheduler = new ActionScheduler(application);

        bus = application.getBus();

        bus.register(this);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }
    @Override
    public void onResume() {
        super.onResume();
        scheduler.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        scheduler.onPause();
    }
}
