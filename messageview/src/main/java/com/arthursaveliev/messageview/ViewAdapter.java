package com.arthursaveliev.messageview;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public class ViewAdapter extends PagerAdapter {

  private List<View> views;

  public ViewAdapter(@NonNull List<View> views) {
    this.views = views;
  }

  @Override public int getCount() {
    return views.size();
  }

  @Override public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    container.removeView((View) object);
  }

  @Override public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
    return view == object;
  }

  @NonNull @Override public Object instantiateItem(@NonNull ViewGroup container, int position) {
    View view = views.get(position);
    container.addView(view);
    return view;
  }
}
