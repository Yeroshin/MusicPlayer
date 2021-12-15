package com.ys.musicplayer.custom_views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;


public class RotaryKnob extends androidx.appcompat.widget.AppCompatImageView {
    Drawable background;
    Paint mPaint;
    double Rad=0;
    double innerR;
    double outerR;
    int startX;
    int startY;
    int stopX;
    int stopY;
    double knobAngle=269;
    double initAngle=1;
    int MAX=270;
    int positionX;
    int positionY;
    int viewWidth;
    int viewHeight;
    int width;
    int w;
    int h;
    boolean enabled;
    public interface OnRotaryKnobChangeListener {
        void onProgress(RotaryKnob rotaryKnob);
        void onStopTrackingTouch(RotaryKnob rotaryKnob);
    }
    private RotaryKnob.OnRotaryKnobChangeListener mOnRotaryKnobChangeListener;
    public void setOnRotaryKnobChangeListener(RotaryKnob.OnRotaryKnobChangeListener l) {
        mOnRotaryKnobChangeListener = l;
    }
    public void removeOnRotaryKnobChangeListener() {
        mOnRotaryKnobChangeListener = null;
    }
    void onProgress() {

        if (mOnRotaryKnobChangeListener != null) {
            mOnRotaryKnobChangeListener.onProgress(this);
        }
    }
    void onStopTrackingTouch() {

        if (mOnRotaryKnobChangeListener != null) {
            mOnRotaryKnobChangeListener.onStopTrackingTouch(this);
        }
    }
    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
       // Drawable d = getCurrentDrawable();
        viewWidth= getMeasuredWidth();
        viewHeight = getMeasuredHeight();
        width=Math.min(viewWidth,viewHeight);
        setMeasuredDimension(width ,width);
       // setMeasuredDimension((int)Rad*2,(int)Rad*2);
       // setMeasuredDimension(width,height);
       // resolveSizeAndState(400, 400, 0);
    }
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
        this.w=w;
        this.h=h;
        //////////////////////////////
        width=Math.min(w,h);
        //////////////////////////////
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        int action = event.getAction();
        double delta;

        double f=Math.cos(Math.toRadians(45));
        double t=Math.toDegrees(Math.acos(f));
        if((event.getY()<width)&&(event.getX()<width)){
            switch(action) {
                case (MotionEvent.ACTION_DOWN) :


                    initAngle = -Math.toDegrees(Math.atan2(event.getY()-width/2, event.getX()-width/2));
                    if(initAngle<0){
                        initAngle=initAngle+360;
                    }
                    // float a=Math.atan2();
                    return true;
                case (MotionEvent.ACTION_MOVE) :

                    double movedAngle = -Math.toDegrees(Math.atan2(event.getY()-width/2, event.getX()-width/2));
                    if(movedAngle<0){
                        movedAngle=movedAngle+360;
                    }
                    //////////////////////////////

                    delta=movedAngle-initAngle;

                    if(delta<-MAX){
                        delta=360+delta;
                    }
                    if(delta>MAX){
                        delta=delta-360;
                    }

                    if((knobAngle+delta)>=MAX){
                        knobAngle=MAX;
                    }else if((knobAngle+delta)<=0){
                        knobAngle=1;
                    }else{
                        knobAngle=knobAngle+delta;
                    }



//////////////////////////////////////////////////////
                    initAngle=movedAngle;
                    invalidate();
                    onProgress();
                    return true;
                case (MotionEvent.ACTION_UP) :
                    onStopTrackingTouch();
                    return true;
                case (MotionEvent.ACTION_CANCEL) :

                    return true;
                case (MotionEvent.ACTION_OUTSIDE) :

                    return true;
                default :
                    return super.onTouchEvent(event);
            }
        }


        return super.onTouchEvent(event);
    }
    void setKnob(Drawable knob){
        background=knob;
        setBackground(knob);
    }
    public RotaryKnob(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);




      /*  int[] attrsArray = new int[] {
                android.R.attr.id, // 0
                android.R.attr.background, // 1
                android.R.attr.layout_width, // 2
                android.R.attr.layout_height,//3
                android.R.attr.rotation// 4
        };
        TypedArray ta = context.obtainStyledAttributes(attrs, attrsArray);
        int id = ta.getResourceId(0, 0);*/
        //match_parent=-1
        //wrap_content=-2
     //   int lw=ta.getInt(2 /* index of attribute in attrsArray */, 0);
      //  int lh=ta.getInt(3 /* index of attribute in attrsArray */, 0);
      //  float w= ta.getFloat(4,0);
      //  int width=background.getIntrinsicWidth();

      /*  background = ta.getDrawable(1);
        Rect r=background.getBounds();*/


      //  TypedArray ta = context.obtainStyledAttributes(attrs, R.style.View, 0, 0);
       // Drawable src = ta.getDrawable(R.styleable.ImageView_src);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom){
        super.onLayout(changed,left,top,right,bottom);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        ////////////////////////////////
      /*  Bitmap back = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(back);
        int l=(int)(((float)width)/100*90);
        int s=(int)(((float)width)/100*10);
        c.drawBitmap(((BitmapDrawable)background).getBitmap(), null, new Rect(s, s, l, l), null);
        Drawable drawable = new BitmapDrawable(getResources(), back);
        setBackground(drawable);*/
        ///////////////////////////////
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        /////////////////////////////////
        Bitmap back = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
       // Canvas c = new Canvas(back);
        int l=(int)(((float)width)/100*90);
        int s=(int)(((float)width)/100*10);
      //  canvas.drawBitmap(((BitmapDrawable)background).getBitmap(), null, new Rect(s, s, l, l), null);

        /////////////////////////////////

        Rad=width/2;

      /* background.setBounds(width/100*10, width/100*10, width/100*90, width/100*90);
       this.setBackground(background);*/
      //  Drawable d = getResources().getDrawable(R.drawable.knob_plastic, null);



        for(int i=0;i<MAX;i=i+6){
            if((i%30)!=0){
                innerR=Rad/100*85;
                outerR=Rad/100*95;
            }else{
                innerR=Rad/100*85;
                outerR=Rad/100*98;
            }
            if(i<knobAngle){
                mPaint.setColor(Color.rgb(20, 20, 20));
            }else if(enabled&&i>MAX*0.3f){
                mPaint.setColor(Color.rgb(0, 255, 0));
            }else if(enabled){
                mPaint.setColor(Color.rgb(255, 0, 0));
            }
            startX=width/2+(int)(innerR*Math.cos(Math.toRadians(i)));
            startY=width/2-(int)(innerR*Math.sin(Math.toRadians(i)));

            stopX=width/2+(int)(outerR*Math.cos(Math.toRadians(i)));
            stopY=width/2-(int)(outerR*Math.sin(Math.toRadians(i)));
            canvas.drawLine(startX, startY, stopX, stopY, mPaint);
        }
        /////pointer
        positionX=width/2+(int)((Rad/100*25)*Math.cos(Math.toRadians(knobAngle)));
        positionY=width/2-(int)((Rad/100*25)*Math.sin(Math.toRadians(knobAngle)));
       // RectF rect=new RectF((int)(positionX-Rad/20), (int)(positionX+Rad/20), positionX, (int)(positionY+Rad/20));
        LinearGradient shader = new LinearGradient((float) (positionX-Rad/13), (float) (positionY-Rad/13), (float) (positionX+Rad/13), (float) (positionY+Rad/13),
                new int[] { Color.BLACK,  Color.WHITE },
                null, Shader.TileMode.CLAMP);
        mPaint.setShader(shader);
        mPaint.setStyle(Paint.Style.FILL);
       // mPaint.setColor(Color.rgb(0, 0, 0));


      //  canvas.drawOval(rect,mPaint);
        canvas.drawCircle(positionX,positionY,(float)Rad/13,mPaint);
        mPaint.setShader(null);

        if(enabled){
            mPaint.setColor(Color.rgb(0, 255, 0));
        }else {
            mPaint.setColor(Color.rgb(100, 100, 100));
        }
        positionX=width/2+(int)((Rad/100*25)*Math.cos(Math.toRadians(knobAngle)));
        positionY=width/2-(int)((Rad/100*25)*Math.sin(Math.toRadians(knobAngle)));
        canvas.drawCircle(positionX,positionY,(float)Rad/20,mPaint);

       // background.setBounds(10, 10, 50, 50);


    }
}
