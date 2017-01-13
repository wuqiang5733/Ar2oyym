package org.xuxiaoxiao.myyora.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import org.xuxiaoxiao.myyora.R;
import org.xuxiaoxiao.myyora.activities.BaseActivity;
import org.xuxiaoxiao.myyora.services.Contacts;
import org.xuxiaoxiao.myyora.views.ContactRequestAdapter;

public class PendingContactRequestsFragment extends BaseFragment {
    private View _progressFrame;
    private ContactRequestAdapter _adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_contact_requests, container, false);
        _progressFrame = view.findViewById(R.id.fragment_pending_contact_requests_progressFrame);
        _adapter = new ContactRequestAdapter((BaseActivity) getActivity());

        ListView listView = (ListView) view.findViewById(R.id.fragment_pending_contact_requests_list);
        listView.setAdapter(_adapter);

        bus.post(new Contacts.GetContactRequestsRequest(true));

        return view;
    }

    @Subscribe
    public void onGetContactRequests(Contacts.GetContactRequestsResponse response) {
        response.showErrorToast(getActivity());
        _progressFrame.animate()
                .alpha(0)
                .setDuration(250)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        _progressFrame.setVisibility(View.GONE);
                    }
                })
                .start();

        _adapter.clear();
        _adapter.addAll(response.Requests);
    }
}
