package com.snail.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.snail.widget.SlidePopupWindow;

public class SharePopupWindow extends SlidePopupWindow implements View.OnClickListener {

    private final Context mContext;
    Button mSendToFriends;
    Button mShareInTimeline;
    TextView mCancelShare;


    public SharePopupWindow(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public View onCreateView(Context context, ViewGroup container) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.window_share, container, false);
        mSendToFriends = contentView.findViewById(R.id.send_to_friends);
        mShareInTimeline = contentView.findViewById(R.id.share_in_timeline);
        mCancelShare = contentView.findViewById(R.id.cancel_share);

        mSendToFriends.setOnClickListener(this);
        mShareInTimeline.setOnClickListener(this);
        mCancelShare.setOnClickListener(this);
        return contentView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancel_share) {// 取消
            dismiss();
            return;
        }
        switch (v.getId()) {
            case R.id.send_to_friends:// 发送给朋友
                Toast.makeText(mContext,"发送给朋友", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share_in_timeline:// 分享到朋友圈
                Toast.makeText(mContext,"分享到朋友圈", Toast.LENGTH_SHORT).show();
                break;
        }
        dismiss();
    }
}
