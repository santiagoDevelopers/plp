package com.example.mapas;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


public class OnEventTouchListener implements View.OnTouchListener {

    private GestureDetector gestureDetector;
    private boolean flag_move = false;

    public OnEventTouchListener(Context c) {
        gestureDetector = new GestureDetector(c, new GestureListener());
    }

    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        if(motionEvent.getAction() == MotionEvent.ACTION_UP){
            onUpTouch();
        }

        return gestureDetector.onTouchEvent(motionEvent);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            flag_move = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (flag_move){
                        onMoveTouch();
                    }

                }
            }, 800);
            onDownTouch();
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            flag_move = false;
            onClick();
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            onDoubleClick();
            return super.onDoubleTap(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            flag_move = false;
            onLongClick();
            super.onLongPress(e);
        }



    }


    public void onClick(){}

    public void onDoubleClick(){}

    public void onLongClick(){}

    public void onDownTouch(){}

    public void onUpTouch(){}

    public void onMoveTouch(){}

    public void onScrollTouch(MotionEvent e1, MotionEvent e2){}

    public void onFlingTouch(MotionEvent e1, MotionEvent e2){}
}