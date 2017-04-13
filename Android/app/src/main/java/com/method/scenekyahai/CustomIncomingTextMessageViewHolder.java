package com.method.scenekyahai;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.method.scenekyahai.models.Message;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

/**
 * Created by piyush0 on 12/04/17.
 */

public class CustomIncomingTextMessageViewHolder extends MessagesListAdapter.IncomingMessageViewHolder<Message> {
    private TextView tvTitle;
    private TextView tvSource;
    private ImageView ivImage;
    private LinearLayout desc;

    public CustomIncomingTextMessageViewHolder(View itemView) {
        super(itemView);

        tvTitle = (TextView) itemView.findViewById(R.id.resultTitle);
        tvSource = (TextView) itemView.findViewById(R.id.resultSource);
        ivImage = (ImageView) itemView.findViewById(R.id.resultImage);
        desc = (LinearLayout) itemView.findViewById(R.id.description);
    }

    @Override
    public void onBind(Message message) {
        super.onBind(message);

        if (message.getResult() != null) {
            desc.setVisibility(View.VISIBLE);
            tvTitle.setText(message.getResult().getTitle());
            tvSource.setText(message.getResult().getSource());

            super.getImageLoader().loadImage(ivImage, message.getResult().getImageURL());

        }
    }
}