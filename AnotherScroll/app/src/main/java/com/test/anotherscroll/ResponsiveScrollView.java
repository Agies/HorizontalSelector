package com.test.anotherscroll;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Adapter;
import android.widget.HorizontalScrollView;

/**
 * Created by agies on 7/6/16.
 */
public class ResponsiveScrollView extends HorizontalScrollView {
    private static final String TAG = "ResponsiveScrollView";
    private Runnable scrollerTask;
    private int currentPosition = 0;
    private int scrollState = SCROLL_STATE_IDLE;
    private int newCheck = 100;
    private OnScrollChangeListener onScrollStoppedListener;
    private Adapter adapter;

    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_DRAGGING = 1;

    public static final int SCROLL_STATE_SETTLING = 2;
    private OnTouchListener onTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                ResponsiveScrollView.this.startScrollerTask();
            }
            return false;
        }
    };

    public ResponsiveScrollView(Context context) {
        super(context);
        init();
    }

    public ResponsiveScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ResponsiveScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
        Log.d(TAG, "setAdapter: ");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "onAttachedToWindow: ");
    }

    private void init() {
        this.scrollerTask = new Runnable() {
            public void run() {
                int newPosition = getScrollX();
                if (currentPosition - newPosition == 0) {
                    onScrollStateChanged(SCROLL_STATE_IDLE);
                } else {
                    currentPosition = getScrollX();
                    if (scrollState != SCROLL_STATE_SETTLING) {
                        onScrollStateChanged(SCROLL_STATE_SETTLING);
                    }
                    ResponsiveScrollView.this.postDelayed(scrollerTask, newCheck);
                }
            }
        };
        this.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                ResponsiveScrollView.this.onScrollChanged();
            }
        });

        this.setOnTouchListener(onTouchListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    protected void onScrollStateChanged(int state) {
        this.scrollState = state;
        OnScrollChangeListener listener = onScrollStoppedListener;
        if (listener != null) {
            listener.onScrollStateChanged(this, state);
        }
    }

    protected void onScrollChanged() {
        OnScrollChangeListener listener = onScrollStoppedListener;
        if (listener != null) {
            listener.onScrollChanged(this);
        }
    }

    protected void startScrollerTask(){
        onScrollStateChanged(SCROLL_STATE_DRAGGING);
        currentPosition = getScrollX();
        this.postDelayed(scrollerTask, newCheck);
    }

    public void setOnScrollChangeListener(OnScrollChangeListener listener) {
        this.onScrollStoppedListener = listener;
    }

    public interface OnScrollChangeListener {
        void onScrollStateChanged(HorizontalScrollView v, int newState);
        void onScrollChanged(HorizontalScrollView v);
    }
}
