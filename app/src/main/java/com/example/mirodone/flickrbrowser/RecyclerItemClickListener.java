package com.example.mirodone.flickrbrowser;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = "RecyclerItemClickListen";

    interface OnRecyclerClickListener {
        void onItemClick (View view, int position);
        void onItemLongClick (View view, int position);
    }

    private final OnRecyclerClickListener mListener;
    private final GestureDetectorCompat mGestureDetector;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnRecyclerClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d(TAG,"onSingleTapUp: Starts");
                View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if(view != null && mListener != null){
                    mListener.onItemClick(view, recyclerView.getChildAdapterPosition(view));
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.d(TAG, "onLongPress: Starts");
                View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (view != null && mListener != null) {
                    mListener.onItemLongClick(view, recyclerView.getChildAdapterPosition(view));
                }
            }
        });
    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.d(TAG, "onInterceptTouchEvent: starts");
       // return super.onInterceptTouchEvent(rv, e);
        if(mGestureDetector != null){
            boolean result = mGestureDetector.onTouchEvent(e);
            return result;
        }else {
            return true;
        }
    }
}