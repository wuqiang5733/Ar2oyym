package org.xuxiaoxiao.myyora.services;

import com.squareup.otto.Subscribe;

import org.xuxiaoxiao.myyora.infrastructure.YoraApplication;
import org.xuxiaoxiao.myyora.services.entities.Message;
import org.xuxiaoxiao.myyora.services.entities.UserDetails;

import java.util.ArrayList;
import java.util.Calendar;

public class InMemoryMessageService extends BaseInMemoryService {
    public InMemoryMessageService(YoraApplication application) {
        super(application);
    }

    @Subscribe
    public void deleteMessage(Messages.DeleteMessageRequest request) {
        Messages.DeleteMessageResponse response = new Messages.DeleteMessageResponse();
        response.MessageId = request.MessageId;
        postDelayed(response);
    }

    @Subscribe
    public void SearchMessages(Messages.SearchMessagesRequest request) {
        Messages.SeacrhMessagesResponse response = new Messages.SeacrhMessagesResponse();
        response.Messages = new ArrayList<>();

        UserDetails[] users = new UserDetails[10];

        for (int i = 0; i < users.length; i++) {
            String stringId = Integer.toString(i);
            users[i] = new UserDetails(
                    i,
                    true,
                    "User " + stringId,
                    "user_" + stringId,
                    "http://www.gravatar.com/avatar/" + stringId + "?d=identicon");
        }

        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, 0); // add(int field, int amount) 根据日历的规则，为给定的日历字段添加或减去指定的时间量。
//        date.add(Calendar.DATE, -100);

        for (int i = 0; i < 100; i++) {
            boolean isFromUs;

            if (request.IncludeReceivedMessages && request.IncludeSentMessages) {
                isFromUs = random.nextBoolean();
            } else {
                isFromUs = !request.IncludeReceivedMessages;
            }

//            可以使用下面三个方法把日历定到任何一个时间：
//
//            set(int year ,int month,int date)
//            set(int year ,int month,int date,int hour,int minute)
//            set(int year ,int month,int date,int hour,int minute,int second)

            date.set(Calendar.MINUTE, 0);
//            date.set(Calendar.MINUTE, random.nextInt(60 * 24));

            String numberString = Integer.toString(i);
            response.Messages.add(new Message(
                    i,                                      //    _id = id;
                    // clone()创建并返回此对象的一个副本
                    (Calendar) date.clone(),                // Calendar is a mutable reference type  （//  _createdAt = createdAt;）
                    "Short Message " + numberString,        //    _shortMessage = shortMessage;
                    "Long Message " + numberString,         //    _longMessage = longMessage;
                    "",                                     //    _imageUrl = imageUrl;
                    users[random.nextInt(users.length)],    //    _otherUser = otherUser;
                    isFromUs,                               //    _isFromUs = isFromUs;
                    //  头五个 设置为没有阅读
                    i > 4));                                //    _isRead = isRead;

            postDelayed(response, 2000);
        }
    }
    // Video 72
    @Subscribe
    public void sendMessage(Messages.SendMessageRequest request) {
        Messages.SendMessageResponse response = new Messages.SendMessageResponse();
        if (request.getMessage().equals("error")) {
            response.setOperationError("Something bad happened");
        } else if (request.getMessage().equals("error11")) {
            response.setPropertyError("message", "Invalid message");
        }
        postDelayed(response, 1500, 3000);
    }
    @Subscribe
    public void markMessageAsRead(Messages.MarkMessageAsReadRequest request) {
        postDelayed(new Messages.MarkMessageAsReadResponse());
    }

    @Subscribe
    public void getMessageDetails(Messages.GetMessageDetailsRequest request) {
        Messages.GetMessageDetailsResponse response = new Messages.GetMessageDetailsResponse();
        response.Message = new Message(
                1,
                Calendar.getInstance(),
                "Short Message",
                "Long Message",
                null,
                new UserDetails(1, true, "Display Name", "Username", ""),
                false,
                false);

        postDelayed(response);
    }
}
