package com.arthursaveliev.messageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import java.util.List;

public final class MessageView extends FrameLayout {

  private ViewGroup contentView;
  private ViewPager pager;
  private ViewPagerIndicator pagerIndicator;
  private ViewGroup messageView;

  private int backgroundColor;

  private int indicatorTint;
  private int indicatorSelectedTint;
  private int indicatorItemSize;
  private float indicatorItemScale;

  public MessageView(@NonNull final Context context) {
    this(context, null);
    init(context, null, 0);
  }

  public MessageView(@NonNull final Context context, @Nullable final AttributeSet attrs) {
    this(context, attrs, 0);
    init(context, attrs, 0);
  }

  public MessageView(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs, defStyleAttr);
  }

  private void init(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
    contentView = (ViewGroup) inflate(getContext(), R.layout.layout_default_messageview, this);

    TypedArray a = context.getTheme()
        .obtainStyledAttributes(attrs, R.styleable.MessageView, defStyleAttr, 0);
    try {
      backgroundColor = a.getInt(R.styleable.MessageView_background_color, -1);
      indicatorTint = a.getColor(R.styleable.MessageView_itemTint, -1);
      indicatorSelectedTint = a.getColor(R.styleable.MessageView_itemSelectedTint, -1);
      indicatorItemSize = a.getDimensionPixelOffset(R.styleable.MessageView_itemSize, -1);
      indicatorItemScale = a.getFloat(R.styleable.MessageView_itemScale, -1);
    } finally {
      a.recycle();
    }

    pager = contentView.findViewById(R.id.pager);
    pagerIndicator = contentView.findViewById(R.id.pager_indicator);
    messageView = contentView.findViewById(R.id.messageView);

    if (backgroundColor != -1) messageView.setBackgroundColor(backgroundColor);
    if (indicatorTint != -1) pagerIndicator.setmItemColor(indicatorTint);
    if (indicatorSelectedTint != -1) pagerIndicator.setmItemSelectedColor(indicatorSelectedTint);
    if (indicatorItemScale != -1) pagerIndicator.setmItemScale(indicatorItemScale);
    if (indicatorItemSize != -1) pagerIndicator.setmItemSize(indicatorItemSize);
  }

  public void setMessages(List<View> views) {
    if (pager != null && views != null) {
      ViewAdapter adapter = new ViewAdapter(views);
      pager.setAdapter(adapter);

      if (views.size() > 1) {
        pagerIndicator.setVisibility(VISIBLE);
        pagerIndicator.setupWithViewPager(pager);
      } else { pagerIndicator.setVisibility(GONE); }
    }
  }

  public void show() {
    setVisibility(VISIBLE);
  }

  public void hide() {
    setVisibility(GONE);
  }
}
