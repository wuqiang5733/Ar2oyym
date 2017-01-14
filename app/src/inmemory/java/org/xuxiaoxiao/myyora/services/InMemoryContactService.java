package org.xuxiaoxiao.myyora.services;

import com.squareup.otto.Subscribe;

import org.xuxiaoxiao.myyora.infrastructure.YoraApplication;
import org.xuxiaoxiao.myyora.services.entities.ContactRequest;
import org.xuxiaoxiao.myyora.services.entities.UserDetails;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class InMemoryContactService extends BaseInMemoryService {
    public InMemoryContactService(YoraApplication application) {
        super(application);
    }

    @Subscribe
    public void getContactRequests(Contacts.GetContactRequestsRequest request) {
        Contacts.GetContactRequestsResponse response = new Contacts.GetContactRequestsResponse();
        response.Requests = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            response.Requests.add(new ContactRequest(i, request.FromUs, createFakeUser(i, false), new GregorianCalendar()));
        }
        postDelayed(response);
    }

    @Subscribe
    public void getContacts(Contacts.GetContactsRequest request) {
        Contacts.GetContactsResponse response = new Contacts.GetContactsResponse();
        response.Contacts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            response.Contacts.add(createFakeUser(i, true));
        }
        postDelayed(response);
//        postDelayed(response,5000); // 加数字，看效果
    }

    @Subscribe
    public void sendContactRequest(Contacts.SendContactRequestRequest request) {
        Contacts.SendContactRequestResponse response = new Contacts.SendContactRequestResponse();
        if (request.UserId == 2) {
            response.setOperationError("Something bad happened!");
        }
        postDelayed(response);
    }

    @Subscribe
    public void respondToContactsRequest(Contacts.RespondToContactRequestRequest request) {
        postDelayed(new Contacts.RespondToContactRequestResponse());
    }

    @Subscribe
    public void removeContact(Contacts.RemoveContactRequest request) {
        Contacts.RemoveContactResponse response = new Contacts.RemoveContactResponse();
        response.RemovedContactId = request.ContactId;
        postDelayed(response);
    }

    @Subscribe
    // Video67 搜索部分要用
    public void searchUsers(Contacts.SearchUsersRequest request) {
        Contacts.SearchUsersResponse response = new Contacts.SearchUsersResponse();
        response.Query = request.Query;
        response.Users = new ArrayList<>();

        for (int i = 0; i < request.Query.length(); i++) {
            response.Users.add(createFakeUser(i, false));
        }

        postDelayed(response, 2000, 3000);
    }

    private UserDetails createFakeUser(int id, boolean isContact) {
        String idString = Integer.toString(id);
        return new UserDetails(
                id,                     //    private int _id;
                isContact,              //    private boolean _isContact;
                "Contact " + idString,  //    private String _displayName;
                "Contact" + idString,   //    private String _username;
                "http://www.gravatar.com/avatar/" + idString + "?d=identicon&s=64"  //    private String _avatarUrl;
        );
    }
}

