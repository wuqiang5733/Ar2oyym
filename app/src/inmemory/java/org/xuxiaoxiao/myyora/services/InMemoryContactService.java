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
        for (int i=0; i< 3; i++) {
            response.Requests.add(new ContactRequest(i, request.FromUs, createFakeUser(i,false), new GregorianCalendar()));
        }
        postDelayed(response);
    }

    @Subscribe
    public void getContacts(Contacts.GetContactsRequest request){
        Contacts.GetContactsResponse response =  new Contacts.GetContactsResponse();
        response.Contacts = new ArrayList<>();
        for (int i=0;i <10; i++){
            response.Contacts.add(createFakeUser(i,true));
        }
        postDelayed(response);
//        postDelayed(response,5000); // 加数字，看效果
    }

    @Subscribe
    public void sendContactRequest(Contacts.SendContactRequestRequest request) {
        Contacts.SendContactRequestResponse response = new Contacts.SendContactRequestResponse();
        if (request.UserId==2) {
            response.setOperationError("Something bad happened!");
        }
        postDelayed(response);
    }

    @Subscribe
    public void respondToContactsRequest(Contacts.RespondToContactRequestRequest request){
        postDelayed(new Contacts.RespondToContactRequestResponse());
    }
    @Subscribe
    public void removeContact(Contacts.RemoveContactRequest request) {
        Contacts.RemoveContactResponse response = new Contacts.RemoveContactResponse();
        response.RemovedContactId = request.ContactId;
        postDelayed(response);
    }

    private UserDetails createFakeUser(int id, boolean isContact) {
        String idString = Integer.toString(id);
        return new UserDetails(
                id,
                isContact,
                "Contact " + idString,
                "Contact" + idString,
                "http://www.gravatar.com/avatar/" + idString + "?d=identicon&s=64"
        );
    }
}
