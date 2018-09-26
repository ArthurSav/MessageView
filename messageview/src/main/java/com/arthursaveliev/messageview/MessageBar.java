package com.arthursaveliev.messageview;

import android.app.Activity;
import android.support.annotation.AnimRes;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageBar {
    private MessageView messageView;
    private final Activity context;

    public static Builder build(Activity activity) {
        return new MessageBar.Builder(activity);
    }

    public static void dismiss(Activity activity) {
        new MessageBar(activity, null);
    }

    private MessageBar(Activity context, Params params) {
        this.context = context;
        if(params == null) {
            //dismiss existing views
            dismiss();
            return;
        }

        messageView = new MessageView(context);
        messageView.setParams(params);
    }

    public void show() {
        if (messageView != null) {
            final ViewGroup decorView = (ViewGroup) context.getWindow().getDecorView();
            final ViewGroup content = decorView.findViewById(android.R.id.content);
            if (messageView.getParent() == null) {
                ViewGroup parent = messageView.getLayoutGravity() == Gravity.BOTTOM ?
                        content : decorView;
                addMessageView(parent, messageView);
            }
        }
    }

    private void dismiss() {
        final ViewGroup decorView = (ViewGroup) context.getWindow().getDecorView();
        final ViewGroup content = decorView.findViewById(android.R.id.content);

        removeFromParent(decorView);
        removeFromParent(content);
    }

    private void removeFromParent(ViewGroup parent) {
        int childCount = parent .getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if(child instanceof MessageView) {
                ((MessageView) child).dismiss();
                return;
            }
        }

    }

    private void addMessageView(final ViewGroup parent, final MessageView messageView) {
        if(messageView.getParent() != null) {
            return;
        }

        // if exists, remove existing view
        int childCount = parent .getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if(child instanceof MessageView) {
                ((MessageView) child).dismiss(new MessageView.DismissListener() {
                    @Override
                    public void onDismiss() {
                        parent.addView(messageView);
                    }
                });
                return;
            }
        }

        parent.addView(messageView);
    }

    public View getView() {
        return messageView;
    }

    public static class Builder {

        private final Params params = new Params();

        public final Activity context;

        public Builder(Activity activity) {
            this.context = activity;
        }

        public Builder setDuration(long duration) {
            params.duration = duration;
            return this;
        }

        public Builder setLayoutGravity(int layoutGravity) {
            params.layoutGravity = layoutGravity;
            return this;
        }

        public Builder setCustomView(@LayoutRes int customView) {
            params.customView = customView;
            return this;
        }

        public Builder setAnimationDuration(long duration) {
            params.animationDuration = duration;
            return this;
        }

        public Builder setAnimationIn(@AnimRes int[] animationIn) {
            params.animationIn = animationIn;
            return this;
        }

        public Builder setAnimationOut(@AnimRes int[] animationOut) {
            params.animationOut = animationOut;
            return this;
        }

        public Builder addMessage(View... messages){
            params.messages.addAll(Arrays.asList(messages));
            return this;
        }

        public Builder addMessages(List<View> messages){
            params.messages.addAll(messages);
            return this;
        }

        public Builder setBackgroundColor(int color){
            params.backgroundColor = color;
            return this;
        }

        public MessageBar create() {
            return new MessageBar(context, params);
        }

        public MessageBar show() {
            final MessageBar view = create();
            view.show();
            return view;
        }
    }

    final static class Params {
        public int backgroundColor;
        public Long duration;
        public long animationDuration = 300;
        public int layoutGravity = Gravity.TOP;
        public int customView;
        public int[] animationIn = new int[]{R.anim.slide_in_from_bottom, R.anim.slide_in_from_top};
        public int[] animationOut = new int[]{R.anim.slide_out_to_bottom, R.anim.slide_out_to_top};
        public List<View> messages = new ArrayList<>();
    }
}