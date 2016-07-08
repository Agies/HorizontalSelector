package com.test.anotherscroll;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.ArrayList;

public class ManualScrollingFragment extends Fragment {
    private static final String TAG = "ManualScrollingFragment";
    private HorizontalListView list;

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
        list = (HorizontalListView) view.findViewById(R.id.another_horizontal_list);
        if (savedInstanceState != null) {
            list.setCurrentId(savedInstanceState.getInt("current_id"));
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt("current_id", list.getCurrentId());

        super.onSaveInstanceState(outState);
    }
}
