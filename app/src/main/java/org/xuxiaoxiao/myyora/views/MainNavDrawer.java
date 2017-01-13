package org.xuxiaoxiao.myyora.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import org.xuxiaoxiao.myyora.R;
import org.xuxiaoxiao.myyora.activities.BaseActivity;
import org.xuxiaoxiao.myyora.activities.ContactsActivity;
import org.xuxiaoxiao.myyora.activities.MainActivity;
import org.xuxiaoxiao.myyora.activities.ProfileActivity;
import org.xuxiaoxiao.myyora.activities.SentMessagesActivity;
import org.xuxiaoxiao.myyora.infrastructure.User;
import org.xuxiaoxiao.myyora.services.Account;

/**
 * Created by WuQiang on 2017/1/6.
 */

public class MainNavDrawer extends NavDrawer {

    private final TextView _displayNameText;//NavDrawer 上的头像与头像旁边的文字
    private final ImageView _avatarImage;

    public MainNavDrawer(final BaseActivity activity) {
        super(activity);
        addItem(new ActivityNavDrawerItem(MainActivity.class, activity.getString(R.string.ActivityInbox).toString(), null, R.drawable.ic_action_unread, R.id.include_main_nav_drawer_topItems));
        addItem(new ActivityNavDrawerItem(SentMessagesActivity.class, activity.getString(R.string.ActivitySentMessages).toString(), null, R.drawable.ic_action_send_now, R.id.include_main_nav_drawer_topItems));
        addItem(new ActivityNavDrawerItem(ContactsActivity.class, activity.getString(R.string.ActivityContacts).toString(), null, R.drawable.ic_action_group, R.id.include_main_nav_drawer_topItems));
        addItem(new ActivityNavDrawerItem(ProfileActivity.class, activity.getString(R.string.ActivityProfile).toString(), null, R.drawable.ic_action_person, R.id.include_main_nav_drawer_topItems));
        addItem(new BasicNavDrawerItem(activity.getString(R.string.ActivityLogout).toString(), null, R.drawable.ic_action_backspace, R.id.include_main_nav_drawer_bottomItems) {
            @Override
            public void onClick(View v) {
                activity.getYoraApplication().getAuth().logout();
                navDrawer.setOpen(false);
            }
        });

        _displayNameText = (TextView) navDrawerView.findViewById(R.id.include_main_nav_drawer_displayName);
        _avatarImage = (ImageView) navDrawerView.findViewById(R.id.include_main_nav_drawer_avatar);

        User loggedInUser = activity.getYoraApplication().getAuth().getUser();
        _displayNameText.setText(loggedInUser.getDisplayName());
        // 从 User 类当中提取文字，然后在 NavDrawer 上面显示
        // 这个文字可以在 LoginFragment 当中的 onClick 当中设置

        // TODO: change avatar image to avatarUrl from loggedInUser
    }
    @Subscribe
    public void UserDetailsUpdated(Account.UserDetailsUpdatedEvent event){
        // TODO update avatar URL
        _displayNameText.setText(event.User.getDisplayName());
    }
}
