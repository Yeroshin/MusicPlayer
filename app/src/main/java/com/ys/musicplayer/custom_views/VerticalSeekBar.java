package com.ys.musicplayer.custom_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;




public class VerticalSeekBar extends androidx.appcompat.widget.AppCompatSeekBar {
    int initValue;
    Paint mPaint;
    int top;
    int bottom;
    int left;
    int right;
    int h;
    int w;
    float thumbW;
    float thumbH;
    int width;
    int height;
    Drawable thumb;
    Drawable progress;
    boolean enabled=false;
    boolean measured=false;
    public interface OnSeekBarChangeListener {
        void onProgress(VerticalSeekBar seekBar);
        void onStopTrackingTouch(VerticalSeekBar seekBar);
    }
    private VerticalSeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener;
    public void setOnSeekBarChangeListener(VerticalSeekBar.OnSeekBarChangeListener l) {
        mOnSeekBarChangeListener = l;
    }
  /*  public void setEnabled(boolean isEnabled){
        setEnabled(isEnabled);
        enabled=isEnabled;
    }*/
    void onProgress() {

        if (mOnSeekBarChangeListener != null) {
            mOnSeekBarChangeListener.onProgress(this);
        }
    }
    void onStopTrackingTouch() {

        if (mOnSeekBarChangeListener != null) {
            mOnSeekBarChangeListener.onStopTrackingTouch(this);
        }
    }

    public VerticalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        int[] attrsArray = new int[] {

                android.R.attr.thumb, // 0
                android.R.attr.background,//1
                android.R.attr.progressDrawable//2

        };
        /////////////////////////


     /*   Drawable progressDrawable = context.getTheme().obtainStyledAttributes(
                attrs, com.android.internal.R.styleable.ProgressBar, 0, 0)
                .getDrawable(android.R.styleable.ProgressBar_progressDrawable);*/
        /////////////////////////

        TypedArray ta = context.obtainStyledAttributes(attrs, attrsArray);
         int id = ta.getResourceId(1 , 0);// index of attribute in attrsArray
        thumb = ta.getDrawable(0);
        progress = ta.getDrawable(1);
        progress = ta.getDrawable(2);
      /*  thumbH=thumb.getIntrinsicWidth();//90angle
        thumbW=thumb.getIntrinsicHeight();//90angle*/


        //match_parent=-1
        //wrap_content=-2
        //   int lw=ta.getInt(2 /* index of attribute in attrsArray */, 0);
        //  int lh=ta.getInt(3 /* index of attribute in attrsArray */, 0);
        //  float w= ta.getFloat(4,0);
        //  int width=background.getIntrinsicWidth();

    }
    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec,heightMeasureSpec );
        // setMeasuredDimension(getMeasuredWidth(),getMeasuredHeight() );
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(),heightMeasureSpec);
       // int r=getHeight();
    }
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.h=h;
        this.w=w;
        super.onSizeChanged(h, w, oldh, oldw);
       // int r=getHeight();

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom){
        super.onLayout(changed,left,top,right,bottom);
        this.top=top;
        this.bottom=bottom;
        this.left=left;
        this.right=right;
        width=right-left;
        height=bottom-top;


        /////////////////////////////////////
        if(!measured){

            /////////////////////
          /*  Bitmap thumbb = Bitmap.createBitmap((int)(width*0.5), (int)(width*0.5), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(thumbb);
            //canvas.drawBitmap(((BitmapDrawable)thumb).getBitmap(), 0,0, null);
            canvas.drawBitmap(((BitmapDrawable)thumb).getBitmap(), null, new Rect(0, 0, (int)(width*0.5f), (int)(width*0.5f)), null);
           // canvas.drawBitmap(((BitmapDrawable)thumb).getBitmap(), null, new Rect((int)(width*0.2f), (int)(width*0.15f), (int)(width*0.8f), (int)(width*0.85f)), null);
            Drawable drawable = new BitmapDrawable(getResources(), thumbb);
            setThumb(drawable);*/
            ///////////////////
           /* Bitmap progressBitmap = Bitmap.createBitmap((int)(width*0.5), (int)(height*0.5), Bitmap.Config.ARGB_8888);
            Canvas progressCanvas = new Canvas(progressBitmap);
            //canvas.drawBitmap(((BitmapDrawable)thumb).getBitmap(), 0,0, null);
            progressCanvas.drawBitmap(((BitmapDrawable)progress).getBitmap(), null, new Rect(0, 0, (int)(width*0.5f), (int)(height*0.5f)), null);
            // canvas.drawBitmap(((BitmapDrawable)thumb).getBitmap(), null, new Rect((int)(width*0.2f), (int)(width*0.15f), (int)(width*0.8f), (int)(width*0.85f)), null);
            Drawable progressDrawable = new BitmapDrawable(getResources(), progressBitmap);*/
/////////////////////
          /*  Drawable progressDrawable=getProgressDrawable();
            progressDrawable.setBounds(0,0,height,width);

            setProgressDrawable(progressDrawable);*/
            //////////////////
            measured=true;
           /* thumbH=drawable.getIntrinsicWidth();//90angle
            thumbW=drawable.getIntrinsicHeight();*/
          /*  thumbH=width*0.6f;
            thumbW=width*0.6f;
            setValue(initValue);*/
        }

        ///////////////////////////
    }
    protected void onDraw(Canvas canvas) {
     /*   mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        int bigStep=(height-thumbH)/4;
      //  float bigStepf=(float)h/5;
        int littleStep=bigStep/4;

        for(int i=0;i<5;i++){

           /* int x=height-getProgress()*height/getMax();
            float xf=height-getProgress()*height/getMax();
            int y=height-thumbH/2+bigStep*i;
            float yf=(float)height-((float)thumbH)/2+bigStepf*i;*/
        /*    if(height-getProgress()*height/getMax()>thumbH/2+bigStep*i){
                mPaint.setColor(Color.rgb(21, 25, 25));
            }else if(enabled){
                mPaint.setColor(Color.rgb(0, 255, 0));
            }
            int m=thumbH/2;
            int t=thumbH/2+bigStep*i;
            canvas.drawLine(width/6, thumbH/2+bigStep*i, width-width/6, thumbH/2+bigStep*i, mPaint);
            if(i<4){
                for(int a=1;a<4;a++){
                 /*   int p=getProgress();
                    int h=height;
                    int m=getMax();*/
    /*                if(height-getProgress()*height/getMax()>(thumbH/2+bigStep*i)+littleStep*a){
                        mPaint.setColor(Color.rgb(21, 25, 25));
                    }else if(enabled){
                        mPaint.setColor(Color.rgb(0, 255, 0));
                    }
                    canvas.drawLine(w/4, (thumbH/2+bigStep*i)+littleStep*a, w-w/4, (thumbH/2+bigStep*i)+littleStep*a, mPaint);
                }
            }


        }
        Rect r=new Rect(width/3,thumbH/2-thumbH/4,width-width/3,thumbH/2+height/5*4+thumbH/4);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.rgb(0, 0, 0));

        canvas.drawRect(r,mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.rgb(25, 25, 25));
        canvas.drawRect(r,mPaint);

      /*  mPaint.setColor(Color.rgb(255, 0, 0));
        canvas.drawLine(0, 0, right, 0, mPaint);*/
       canvas.rotate(-90);
        canvas.translate(-getHeight(),0);
        super.onDraw(canvas);
    }

    void setValue(int value){
        setProgress(value);
        onSizeChanged( getWidth(), getHeight(), 0, 0);
        onProgress();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int progress=getMax() - (int) (getMax() * event.getY() / getHeight());
                setProgress(progress);
                getBackground().setLevel(progress*100);

                onSizeChanged(getWidth(), getHeight(), 0, 0);
                onProgress();
                break;
            case MotionEvent.ACTION_UP:

                onStopTrackingTouch();
                break;

            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }
}


