package com.test.anotherscroll;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.ArrayList;

public class HorizontalListView extends FrameLayout {
    private static final String TAG = "HorizontalListView";
    private WeakReference<GridElement> currentView;
    private WeakReference<GridElement> scrollView;
    private float progress = 1;
    private int peek = 100;
    private ResponsiveScrollView scroll;
    private Point point;
    private ArrayList<GridElement> elements = new ArrayList<>();
    private Adapter adapter;
    private int currentId = 0;
    private LinearLayout mainLayout;

    public HorizontalListView(Context context) {
        super(context);
        init(null, 0);
    }

    public HorizontalListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public HorizontalListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        inflate(getContext(), R.layout.horizontal_list, this);

        mainLayout = (LinearLayout) findViewById(R.id.manual_layout);
        point = getPoint();
        scroll = (ResponsiveScrollView) findViewById(R.id.manual_scrollview);
        scroll.setSmoothScrollingEnabled(true);
        scroll.setOnScrollChangeListener(new ResponsiveScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollStateChanged(HorizontalScrollView v, int newState) {
                if (newState == 0) {
                    scrollToCenter();
                }
            }

            @Override
            public void onScrollChanged(HorizontalScrollView v) {
                setAlpha();
            }
        });

        //generateElements();
    }

    private void scrollToCenter() {
        if (currentView == null || currentView.get() == null) return;
        int scrollTo = currentView.get().id * (point.x - (peek*2));
        scroll.smoothScrollTo(scrollTo, 0);
    }

    private void generateElements() {
        for (int i = 0; i < 4; i++) {
            GridElement newView = new GridElement(getContext());
            newView.id = i;
            newView.card.setText("------- Card #" + i + " -------");
            newView.setLayoutParams(new LinearLayout.LayoutParams(point.x - (i == 0 || i == 3 ? peek : peek * 2), ViewGroup.LayoutParams.MATCH_PARENT));
            newView.setPadding(i == 0 ? peek : 0, 0, i == 3 ? peek : 0, 0);
            if (i != currentId) {
                newView.body.setAlpha(0);
            } else {
                currentView = new WeakReference<>(newView);
            }
            mainLayout.addView(newView);
            elements.add(newView);
        }
    }

    public int getPeek() {
        return peek;
    }
    public void setPeek(int value) {
        this.peek = value;
        invalidateAndLayout();
    }

    public Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
        invalidateAndLayout();
    }

    private void invalidateAndLayout() {
        invalidate();
        requestLayout();
    }

    private void setAlpha() {
        int center = point.x/2;
        int left = peek - 50;
        int right = point.x - peek - 50;
        for (GridElement element : elements) {
            findCurrentView(element, center, left, right);
        }
        for (GridElement element : elements) {
            float alpha = getAlpha(element);
            element.body.setAlpha(alpha);
        }
    }

    protected float getAlpha(GridElement view) {
        if (currentView == null || currentView.get() == null) return 0.0f;
        if (view == scrollView.get()) {
            return 1 - progress;
        } else {
            return progress;
        }
    }

    protected void findCurrentView(GridElement view, int center, int left, int right) {
        int coordinates[] = {-1, -1};
        view.card.getLocationOnScreen(coordinates);
        int origin = coordinates[0] + center;
        if (origin > left && origin < right) {
            currentView = new WeakReference<>(view);
        }
        if (coordinates[0] > left && coordinates[0] < right) {
            scrollView = new WeakReference<>(view);
            float percent = (coordinates[0] / (point.x * 1.0f - peek)) - .12f;
            progress = BigDecimal.valueOf(percent).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        }
    }

    private Point getPoint() {
        Display display = ((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        final Point point = new Point();
        display.getSize(point);
        return point;
    }

    public int getCurrentId() {
        return currentId;
    }

    public void setCurrentId(int currentId) {
        this.currentId = currentId;
    }
}
