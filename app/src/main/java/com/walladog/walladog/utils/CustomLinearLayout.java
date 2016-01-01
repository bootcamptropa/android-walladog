package com.walladog.walladog.utils;

/**
 * Created by hadock on 31/12/15.
 *
 */

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class CustomLinearLayout extends LinearLayout {

    public final static int PAGES = 5;
    // You can choose a bigger number for LOOPS, but you know, nobody will fling
    // more than 1000 times just in order to test your "infinite" ViewPager :D
    public final static int LOOPS = 1000;
    public final static int FIRST_PAGE = PAGES * LOOPS / 2;
    public final static float BIG_SCALE = 1.0f;
    public final static float SMALL_SCALE = 0.7f;
    public final static float DIFF_SCALE = BIG_SCALE - SMALL_SCALE;


    private float scale = BIG_SCALE;

    public CustomLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLinearLayout(Context context) {
        super(context);
    }

    public void setScaleBoth(float scale)
    {
        this.scale = scale;
        this.invalidate(); 	// If you want to see the scale_up every time you set
        // scale_up you need to have this line here,
        // invalidate() function will call onDraw(Canvas)
        // to redraw the view for you
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // The main mechanism to display scale_up animation, you can customize it
        // as your needs
        int w = this.getWidth();
        int h = this.getHeight();
        canvas.scale(scale, scale, w/2, h/2);

        super.onDraw(canvas);
    }
}