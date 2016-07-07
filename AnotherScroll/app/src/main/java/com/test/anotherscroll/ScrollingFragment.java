package com.test.anotherscroll;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ScrollingFragment extends Fragment {
    //private HorizontalGridView gridView;
    private int x = 0;
    private int peek = 100;

    public ScrollingFragment() {
    }

    public static ScrollingFragment newInstance() {
        ScrollingFragment fragment = new ScrollingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        x = 0;
        View view = inflater.inflate(R.layout.fragment_scrolling, container, false);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        final Point point = new Point();
        display.getSize(point);
//        gridView = (HorizontalGridView) view.findViewById(R.id.gridView);
//        gridView.setSelectedPosition(0);
//        gridView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                int pos = Math.round(x / (point.x / 1.5f));
//                int scrollTo = x - ((point.x) * pos);
//                if (newState == 0 && scrollTo != 0) {
//                    //gridView.smoothScrollBy(scrollTo, 0);
//                    Log.d("Scroll", "Scrolling to position: " + pos + ", value: " + scrollTo);
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                x += dx;
//                Log.d("Scroll", "Currently at " + x);
//            }
//        });
//        GridElementAdapter adapter = new GridElementAdapter(getContext(), point.x, peek);
//        gridView.setAdapter(adapter);
        return view;
    }
}
