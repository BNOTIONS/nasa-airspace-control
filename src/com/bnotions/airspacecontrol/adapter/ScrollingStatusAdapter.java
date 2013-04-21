package com.bnotions.airspacecontrol.adapter;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created with IntelliJ IDEA.
 * User: pirdod
 * Date: 4/20/13
 * Time: 11:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScrollingStatusAdapter {

    private Context context;
    private TextView txt_stat1;
    private TextView txt_stat2;
    private ArrayList<String> list_texts;

    private int width;
    private int height;

    public ScrollingStatusAdapter(Context context, TextView txt_stat1, TextView txt_stat2) {

        this.context = context;
        this.txt_stat1 = txt_stat1;
        this.txt_stat2 = txt_stat2;
        this.list_texts = new ArrayList<String>();

        initialize();
    }

    private void initialize() {

        initializeWidthAndHeight();
        setTextIfAvailable(0, 1);
        txt_stat1.animate().alpha(0).setDuration(0).start();
        txt_stat2.animate().translationX(width).setDuration(0).start();
    }

    private void setTextIfAvailable(int stat1_index, int stat2_index) {

        String text_1 = (stat1_index < list_texts.size()) ? list_texts.get(stat1_index) : "";
        String text_2 = (stat2_index < list_texts.size()) ? list_texts.get(stat2_index) : "";
        txt_stat1.setText(text_1);
        txt_stat2.setText(text_2);
    }

    private void initializeWidthAndHeight() {

        WindowManager w_manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = w_manager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
    }

    public void setListTexts(ArrayList<String> texts) {

        this.list_texts = texts;
        setTextIfAvailable(0, 1);
    }

    public void addText(String text) {

        if (list_texts == null) list_texts = new ArrayList<String>();
        list_texts.add(text);
    }

    public void startAnimation() {

        txt_stat1.animate().alpha(1f).setDuration(200).start();
        startScrollingAfter(6000, 6000, -width, 0);
    }

    private void startScrollingAfter(int delay, int duration, int x_1, int x_2) {

        txt_stat1.animate().translationX(x_1).setDuration(duration).setListener(stat1_listener).start();
        txt_stat2.animate().translationX(x_2).setDuration(duration).setListener(stat2_listener).start();
    }

    private Animator.AnimatorListener stat1_listener = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

            if (txt_stat1.getX() == (-width)) {
                txt_stat1.animate().translationX(width).setListener(stat1_listener).setDuration(0).start();

            } else if (txt_stat1.getX() == width) {
                txt_stat1.animate().translationX(width).setListener(stat1_listener).setDuration(0).start();
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };
    private Animator.AnimatorListener stat2_listener = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };
}
