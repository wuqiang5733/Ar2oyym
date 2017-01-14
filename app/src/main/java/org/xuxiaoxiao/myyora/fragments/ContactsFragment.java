package org.xuxiaoxiao.myyora.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import org.xuxiaoxiao.myyora.R;
import org.xuxiaoxiao.myyora.activities.AddContactActivity;
import org.xuxiaoxiao.myyora.activities.BaseActivity;
import org.xuxiaoxiao.myyora.activities.ContactActivity;
import org.xuxiaoxiao.myyora.services.Contacts;
import org.xuxiaoxiao.myyora.services.entities.UserDetails;
import org.xuxiaoxiao.myyora.views.UserDetailsAdapter;


public class ContactsFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private UserDetailsAdapter _adapter;
    private View _progressFrame;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        _adapter = new UserDetailsAdapter((BaseActivity) getActivity());
        _progressFrame = view.findViewById(R.id.fragment_contacts_progressFrame);

        ListView listView = (ListView) view.findViewById(R.id.fragment_contacts_list);
        listView.setEmptyView(view.findViewById(R.id.fragment_contacts_emptyList));
        listView.setAdapter(_adapter);
        listView.setOnItemClickListener(this);

        bus.post(new Contacts.GetContactsRequest(false));

        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UserDetails details = _adapter.getItem(position);
        Intent intent = new Intent(getActivity(), ContactActivity.class);
        intent.putExtra(ContactActivity.EXTRA_USER_DETAILS, details);
        startActivity(intent);
    }

    @Subscribe
    public void onContactsResponse(final Contacts.GetContactsResponse response) {
        scheduler.invokeOnResume(Contacts.GetContactsResponse.class, new Runnable() {
            @Override
            public void run() {
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

                if (!response.didSucceed()) {
                    response.showErrorToast(getActivity());
                    return;
                }

                _adapter.clear();
                _adapter.addAll(response.Contacts);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // 注意在 onCreateView 当中 得有：setHasOptionsMenu(true);
        inflater.inflate(R.menu.fragment_contacts, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.fragment_contacts_menu_addContact) {
            startActivity(new Intent(getActivity(), AddContactActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
