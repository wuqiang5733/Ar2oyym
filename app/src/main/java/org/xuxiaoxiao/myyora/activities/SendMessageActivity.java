package org.xuxiaoxiao.myyora.activities;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.xuxiaoxiao.myyora.R;

public class SendMessageActivity extends BaseAuthenticatedActivity {
    public static final String EXTRA_IMAGE_PATH = "EXTRA_IMAGE_PATH";
    public static final String EXTRA_CONTACT = "EXTRAC_CONTACT";

    public static final int MAX_IMAGE_HEIGHT = 1280;

    @Override
    protected void onYoraCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_send_message);

        Uri imageUri = getIntent().getParcelableExtra(EXTRA_IMAGE_PATH);
        if (imageUri != null) {
            ImageView image = (ImageView) findViewById(R.id.activity_send_message_image);
            Picasso.with(this)
                   .invalidate(imageUri); // Force Picasso to reload image even if Uri not changed
            Picasso.with(this)
                   .load(imageUri)
                   .into(image);
        }
    }
}
