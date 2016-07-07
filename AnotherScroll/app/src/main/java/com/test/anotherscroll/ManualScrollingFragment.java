package com.test.anotherscroll;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ManualScrollingFragment extends Fragment {
    private static final String TAG = "ManualScrollingFragment";
    private int peek = 100;
    private ResponsiveScrollView scroll;
    private Point point;
    private ArrayList<GridElement> elements = new ArrayList<>();

    public ManualScrollingFragment() {
        // Required empty public constructor
    }

    public static ManualScrollingFragment newInstance() {
        ManualScrollingFragment fragment = new ManualScrollingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manual_scrolling, container, false);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.manual_layout);
        point = getPoint();
        scroll = (ResponsiveScrollView) view.findViewById(R.id.manual_scrollview);
        scroll.setSmoothScrollingEnabled(true);
        scroll.setOnScrollChangeListener(new ResponsiveScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollStateChanged(HorizontalScrollView v, int newState) {

            }

            @Override
            public void onScrollChanged(HorizontalScrollView v) {
                setAlpha();
            }
        });

        for (int i = 0; i < 4; i++) {
            GridElement newView = new GridElement(getContext());
            newView.id = i;
            newView.card.setText("------- Card #" + i + " -------");
            newView.setLayoutParams(new LinearLayout.LayoutParams(point.x - (i == 0 || i == 3 ? peek : peek * 2), ViewGroup.LayoutParams.MATCH_PARENT));
            newView.setPadding(i == 0 ? peek : 0, 0, i == 3 ? peek : 0, 0);
            int currentId = 0;
            if (savedInstanceState != null) {
                currentId = savedInstanceState.getInt("CurrentId",0);
            }
            if (i != currentId) {
                newView.body.setAlpha(0);
            } else {
                currentView = newView;
            }
            layout.addView(newView);
            elements.add(newView);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        int currentId = 0;
        if (currentView != null) {
            currentId = currentView.id;
        }
        outState.putInt("CurrentId", currentId);

        super.onSaveInstanceState(outState);
    }

    private void setAlpha() {
        for (GridElement element : elements) {
            findCurrentView(element);
        }
        for (GridElement element : elements) {
            float alpha = getAlpha(element, element.id);
            element.body.setAlpha(alpha);
        }
    }
    GridElement currentView;
    float progress = 1;
    protected void findCurrentView(GridElement view) {
        int coordinates[] = {-1, -1};
        view.card.getLocationOnScreen(coordinates);
        if (coordinates[0] > peek - 50 && coordinates[0] < (point.x - peek - 50)) {
            currentView = view;
            float percent = (coordinates[0] / (point.x * 1.0f - peek)) - .12f;
            progress = BigDecimal.valueOf(percent).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        }
    }
    protected float getAlpha(GridElement view, int id) {
//        int coordinates[] = {-1, -1};
//        view.getLocationOnScreen(coordinates);
//        if (coordinates[0] + view.getWidth() < 0) return 0.0f;
//        if (this.getContext() == null) return 0.0f;
//        float percent = 1 - (coordinates[0] / ((point.x * 1.0f) - peek));
//
//        if (percent > 1) {
//            percent -= 2;
//        }
//        percent = Math.abs(percent);
//        return percent;
        if (view == currentView) {
            return 1 - progress;
        } else {
            return progress;
        }
    }

    @NonNull
    private Point getPoint() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        final Point point = new Point();
        display.getSize(point);
        return point;
    }
}
