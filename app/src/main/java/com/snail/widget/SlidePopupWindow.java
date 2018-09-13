package com.snail.widget;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.snail.sample.R;

public abstract class SlidePopupWindow extends PopupWindow {

    private final Context mContext;
    private FrameLayout mContainer;
    // 透明遮罩，接收窗体外部的点击事件用
    private FrameLayout mMaskLayout;

    public SlidePopupWindow(Context context) {
        super(context);
        mContext = context;
        setAnimationStyle(R.style.pop_anim);
        // 收起时渐进恢复背景，从半透明-
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                ValueAnimator animator = ValueAnimator.ofFloat(0.5f, 1f).setDuration(500);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        setBackgroundAlpha((Float) animation.getAnimatedValue());
                    }
                });
                animator.start();
            }
        });

        mContainer = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.window_slide_popup, null);

        // 添加窗体
        View contentView = onCreateView(context, mContainer);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.BOTTOM;
        contentView.setLayoutParams(layoutParams);
        mContainer.addView(contentView, layoutParams);
        setContentView(mContainer);

        // 宽度必须设置MATCH_PARENT,否则不显示
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 高度MATCH_PARENT，contentView铺满全屏，点击内容区域外时即点击透明遮罩mMaskLayout
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置popupWindow背景透明,否则PopupWindow整体是灰色背景
        setBackgroundDrawable(null);
        // 点击返回时收起弹窗
        setBackCancel();
        mMaskLayout = mContainer.findViewById(R.id.mask_layout);
        // 点击窗体外部的透明遮罩时收起弹窗
        mMaskLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public abstract View onCreateView(Context context, ViewGroup container);

    /**
     * 点返回键时收起弹窗
     */
    private void setBackCancel() {
        // 返回键窗体消失必须设置
        setFocusable(true);
        setOutsideTouchable(true);
        // 设置contentView能够监听事件，设置点击返回键窗体消失有必要
        mContainer.setFocusable(true);
        mContainer.setFocusableInTouchMode(true);
        // 设置点击返回键窗体消失
        mContainer.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 显示弹窗，过程中伴随背景逐渐变暗。
     * 不要改变MaskLayout的背景色，否则会是灰色背景的框框整体向上升起的效果。
     */
    public void show(View parent) {
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        ValueAnimator animator = ValueAnimator.ofFloat(1, 0.5f).setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setBackgroundAlpha((Float) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    /**
     * 设置PopupWindow下面的内容的透明度
     */
    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

}
