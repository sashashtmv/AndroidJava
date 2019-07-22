package testcom.com.net2.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import testcom.com.net2.R;

public class SlidePagerAdapter extends PagerAdapter {

    private ArrayList<String> images;
    private WeakReference<Context> contextWeakReference;

    public SlidePagerAdapter(ArrayList<String> images, WeakReference<Context> contextWeakReference) {
        this.images = images;
        this.contextWeakReference = contextWeakReference;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(contextWeakReference.get());
        int padding = (int) contextWeakReference.get().getResources().getDimension(R.dimen.view_pager_padding);

        imageView.setPadding(padding, padding, padding, padding);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Picasso.with(contextWeakReference.get()).load(images.get(position)).into(imageView);
        container.addView(imageView, 0);

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}