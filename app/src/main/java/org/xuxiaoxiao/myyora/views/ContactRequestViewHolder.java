package org.xuxiaoxiao.myyora.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.xuxiaoxiao.myyora.R;
import org.xuxiaoxiao.myyora.services.entities.ContactRequest;


public class ContactRequestViewHolder extends RecyclerView.ViewHolder {
    private final TextView _displayName;
    private final TextView _createdAt;
    private final ImageView _avatar;

    public ContactRequestViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.list_item_contact_request, parent, false));


        _displayName = (TextView) itemView.findViewById(R.id.list_item_contact_request_displayName);
        _createdAt = (TextView)  itemView.findViewById(R.id.list_item_contact_request_createdAt);
        _avatar = (ImageView)  itemView.findViewById(R.id.list_item_contact_request_avatar);
    }

    public void populate(Context context, ContactRequest request) {
        _displayName.setText(request.getUser().getDisplayName());
        Picasso.with(context)
               .load(request.getUser().getAvatarUrl())
               .into(_avatar);

        String dateText = DateUtils.formatDateTime(
                context,
                request.getCreatedAt().getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME);
        if (request.isFromUs()) {
            _createdAt.setText("Sent at " + dateText);
        } else {
            _createdAt.setText("Received at " + dateText);
        }
    }
}
