package com.arthursaveliev.messageview;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import java.util.List;

final class MessageView extends FrameLayout {

    private long slideOutAnimationDuration = 300;
    private Animation slideOutAnimation;

    private ViewGroup contentView;
    private ViewPager pager;
    private ViewPagerIndicator pagerIndicator;
    private ViewGroup messageView;

    private Long duration;
    private long animationDuration = 300;
    private int layoutGravity = Gravity.BOTTOM;
    private int[] animationIn;
    private int[] animationOut;
    private int backgroundColor;

    public Integer indicatorTint;
    public Integer indicatorSelectedTint;
    public Integer indicatorItemSize;
    public Float indicatorItemScale;


    public MessageView(@NonNull final Context context) {
        this(context, null);
    }

    public MessageView(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageView(@NonNull final Context context, @Nullable final AttributeSet attrs,
                  final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getLayoutGravity() {
        return layoutGravity;
    }

    private void initViews(@LayoutRes int rootView) {
        if (rootView != 0) {
            contentView = (ViewGroup) inflate(getContext(), rootView, this);
        } else {
            contentView = (ViewGroup) inflate(getContext(), R.layout.layout_default_messageview, this);
        }
        pager = contentView.findViewById(R.id.pager);
        pagerIndicator = contentView.findViewById(R.id.pager_indicator);
        messageView = contentView.findViewById(R.id.messageView);

        if (messageView != null) {
            if (backgroundColor != 0) messageView.setBackgroundColor(backgroundColor);
        }

        if (pagerIndicator != null) {
            if (indicatorTint != null) pagerIndicator.setmItemColor(indicatorTint);
            if (indicatorSelectedTint != null) pagerIndicator.setmItemSelectedColor(indicatorSelectedTint);
            if (indicatorItemScale != null) pagerIndicator.setmItemScale(indicatorItemScale);
            if (indicatorItemSize != null) pagerIndicator.setmItemSize(indicatorItemSize);
        }
    }

    public void setMessages(List<View> views){
      if (pager != null && views != null) {
        ViewAdapter adapter = new ViewAdapter(views);
        pager.setAdapter(adapter);

        if (views.size() > 1) {
            pagerIndicator.setVisibility(VISIBLE);
            pagerIndicator.setupWithViewPager(pager);
        }
        else pagerIndicator.setVisibility(GONE);
      }
    }

    public void setParams(final MessageBar.Params params) {
        duration = params.duration;
        layoutGravity = params.layoutGravity;
        animationDuration = params.animationDuration;
        animationIn = params.animationIn;
        animationOut = params.animationOut;
        backgroundColor = params.backgroundColor;
        indicatorTint = params.indicatorTint;
        indicatorSelectedTint = params.indicatorSelectedTint;
        indicatorItemSize = params.indicatorItemSize;
        indicatorItemScale = params.indicatorItemScale;

        initViews(params.customView);
        setMessages(params.messages);

        createInAnim();
        createOutAnim();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (layoutGravity == Gravity.TOP) {
            super.onLayout(changed, l, 0, r, contentView.getMeasuredHeight());
        } else {
            super.onLayout(changed, l, t, r, b);
        }
    }

    private void createInAnim() {
        Animation slideInAnimation = AnimationUtils.loadAnimation(getContext(), (layoutGravity == Gravity.BOTTOM) ? animationIn[0] : animationIn[1]);
        slideInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // no implementation
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (duration != null) {
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();
                        }
                    }, duration);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // no implementation
            }
        });
        slideInAnimation.setDuration(animationDuration);
        setAnimation(slideInAnimation);
    }

    private void createOutAnim() {
        slideOutAnimation = AnimationUtils.loadAnimation(getContext(), layoutGravity == Gravity.BOTTOM ? animationOut[0] : animationOut[1]);
        slideOutAnimation.setDuration(animationDuration);
        slideOutAnimationDuration = slideOutAnimation.getDuration();
        slideOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // no implementation
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // no implementation
            }
        });
    }

    public void dismiss() {
        dismiss(null);
    }

    public void dismiss(final DismissListener listener) {
        removeFromParent();

        slideOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(final Animation animation) {
                // no implementation
            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                if (listener != null) {
                    listener.onDismiss();
                }
                setVisibility(View.GONE);
                removeFromParent();
            }

            @Override
            public void onAnimationRepeat(final Animation animation) {
                // no implementation
            }
        });

        long speed = listener == null ? slideOutAnimationDuration : slideOutAnimationDuration / 2;
        slideOutAnimation.setDuration(speed);
        startAnimation(slideOutAnimation);
    }

    private void removeFromParent() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewParent parent = getParent();
                if (parent != null) {
                    MessageView.this.clearAnimation();
                    ((ViewGroup) parent).removeView(MessageView.this);
                }
            }
        }, 200);
    }


    private Animator.AnimatorListener getDestroyListener() {
        return new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // no implementation
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                removeFromParent();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // no implementation
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // no implementation
            }
        };
    }

    public interface DismissListener {
        void onDismiss();
    }
}
