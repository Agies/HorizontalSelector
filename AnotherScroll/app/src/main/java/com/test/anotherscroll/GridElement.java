package com.test.anotherscroll;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Random;

class Randomizer {
    public static Random instance = new Random();
}

/**
 * TODO: document your custom view class.
 */
public class GridElement extends ScrollView {
    public static final String TAG = "GridElement";
    public TextView card;
    public int id;
    public TextView body;

    public GridElement(Context context) {
        super(context);
        init();
    }

    public GridElement(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GridElement(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.scroll_grid_element, this);
        //this.setBackgroundColor(Randomizer.instance.nextInt());
        card = (TextView) findViewById(R.id.scroll_card);
        body = (TextView) findViewById(R.id.scroll_body);
    }
}