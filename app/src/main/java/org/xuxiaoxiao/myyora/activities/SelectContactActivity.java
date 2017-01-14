package org.xuxiaoxiao.myyora.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import org.xuxiaoxiao.myyora.R;
import org.xuxiaoxiao.myyora.services.Contacts;
import org.xuxiaoxiao.myyora.services.entities.UserDetails;
import org.xuxiaoxiao.myyora.views.UserDetailsAdapter;

public class SelectContactActivity extends BaseAuthenticatedActivity implements AdapterView.OnItemClickListener {
    public static final String RESULT_CONTACT = "RESULT_CONTACT";

    private static final int REQUEST_ADD_CONTACT = 1;
    private UserDetailsAdapter _adapter;

    @Override
    protected void onYoraCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_select_contact);
        getSupportActionBar().setTitle("Select Contact");

        _adapter = new UserDetailsAdapter(this);
        ListView listView = (ListView) findViewById(R.id.activity_select_contact_listView);
        listView.setAdapter(_adapter);
        listView.setOnItemClickListener(this);

        bus.post(new Contacts.GetContactsRequest(true));
    }


    @Subscribe
    public void onContactsReceived(final Contacts.GetContactsResponse response) {
        scheduler.invokeOnResume(Contacts.GetContactsResponse.class, new Runnable() {
            @Override
            public void run() {
                response.showErrorToast(SelectContactActivity.this);
                _adapter.clear();
                _adapter.addAll(response.Contacts);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_select_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.activity_select_contact_menuAddContact) {
            startActivityForResult(new Intent(this, AddContactActivity.class), REQUEST_ADD_CONTACT);
            return true;
        }

        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ADD_CONTACT && resultCode == RESULT_OK) {
            UserDetails user = data.getParcelableExtra(AddContactActivity.RESULT_CONTACT);
            selectUser(user);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectUser(_adapter.getItem(position));
    }

    private void selectUser(UserDetails user) {
        Intent intent = new Intent();
        intent.putExtra(RESULT_CONTACT, user);
        setResult(RESULT_OK, intent);
        finish();
    }

//    @Subscribe
//    public void onContactsReceived(Contacts.GetContactsResponse response) {
//        response.showErrorToast(this);
//        _adapter.clear();
//        _adapter.addAll(response.Contacts);
//    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        UserDetails selectedContact = _adapter.getItem(position);
//        Intent intent = new Intent();
//        intent.putExtra(RESULT_CONTACT, selectedContact);
//        setResult(RESULT_OK, intent);
//        finish();
//    }
}
