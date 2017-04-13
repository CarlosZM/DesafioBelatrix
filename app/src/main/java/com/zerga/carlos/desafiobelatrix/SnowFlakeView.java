package com.zerga.carlos.desafiobelatrix;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Carlos Zerga on 11/04/2017.
 */

public class SnowFlakeView extends View{

    private float startXPoint;
    private float startYPoint;

    private int currentFrame;

    private int circleAmount;

    private int movementDirection;

    private float extendedCircleCentrePosition;

    private float extendedCircleCentrePositionIncrementer;

    private float extendedCircleRadio;

    private float extendedCircleRadioIncrementer;

    private  float density;

    private float mainCircleRadio;

    private boolean moveExtendedCircle;

    private float angleIncrementer;

    private static final int AMOUNT_FRAMES = 50;

    private static final int AMOUNT_PARTS=10;

    private Paint extendedCirclePaint;

    private Paint extendedCircleLettersPaint;

    private Paint mainCirclePaint;

    private Paint mainCircleLetterPaint;

    private Paint lineBetweenCirclesPaint;

    private ArrayList<CircleExpanded> circleExpanded;

    public SnowFlakeView(Context context) {
        super(context);

        circleExpanded=new ArrayList<>();
        this.circleAmount=0;
        this.currentFrame=1;
        this.movementDirection=1;
        this.angleIncrementer=this.calculateAngleIncrementer(this.circleAmount);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        this.density=getContext().getResources().getDisplayMetrics().density;
        this.startXPoint=w/2;
        this.startYPoint=h/2;
        double kDimensionPart=(w>h?h:w)/(AMOUNT_PARTS*2.0);
        this.mainCircleRadio=(float)(3*kDimensionPart);
        this.extendedCircleCentrePosition=(float)kDimensionPart*2;
        this.extendedCircleCentrePositionIncrementer =(float)(kDimensionPart*6.0/AMOUNT_FRAMES);
        this.extendedCircleRadio=(float)(kDimensionPart/2);
        this.extendedCircleRadioIncrementer=(float)(kDimensionPart/(AMOUNT_FRAMES*2.0));


        this.mainCirclePaint=new Paint();
        this.mainCirclePaint.setColor(Color.RED);
        this.extendedCircleLettersPaint=new Paint();
        this.extendedCircleLettersPaint.setTextSize(12*density);
        this.extendedCircleLettersPaint.setColor(Color.WHITE);
        this.extendedCirclePaint=new Paint();
        this.lineBetweenCirclesPaint=new Paint();
        this.lineBetweenCirclesPaint.setColor(Color.BLACK);
        this.mainCircleLetterPaint=new Paint();
        this.mainCircleLetterPaint.setTextSize(20*density);
        this.mainCircleLetterPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if(visibility!=VISIBLE)
        {
            if(currentFrame==1)
            {
                currentFrame=AMOUNT_FRAMES-1;
                movementDirection=movementDirection*-1;
                moveExtendedCircle=true;
            }
        }
    }

    private float calculateAngleIncrementer(int circleAmount)
    {
        circleAmount=circleAmount==0?1:circleAmount;
        return 360/circleAmount;
    }

    private double calculateXByAngle(float angle)
    {
        Double decimal=Math.cos(Math.toRadians(angle));
        return this.startXPoint+decimal*extendedCircleCentrePosition;
    }

    private double calculateYByAngle(float angle)
    {
        Double decimal=Math.sin(Math.toRadians(angle));
        return this.startYPoint-decimal*extendedCircleCentrePosition;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(this.moveExtendedCircle)
        {
            currentFrame++;

            float angle=0;
            int circles=0;
            for (CircleExpanded circleExpanded:this.circleExpanded)
            {
                circles++;
                canvas.save();

                float xPosition=(float) calculateXByAngle(angle);
                float yPosition=(float) calculateYByAngle(angle);
                circleExpanded.x=xPosition;
                circleExpanded.y=yPosition;
                circleExpanded.radio=extendedCircleRadio;
                canvas.drawLine(startXPoint,startYPoint,xPosition,yPosition,lineBetweenCirclesPaint);
                this.extendedCirclePaint.setColor(circleExpanded.color);
                canvas.drawCircle(xPosition,yPosition ,extendedCircleRadio,this.extendedCirclePaint);


                if(currentFrame==AMOUNT_FRAMES)
                {
                    canvas.drawText("Item",xPosition-extendedCircleRadio/2,yPosition,this.extendedCircleLettersPaint);
                    canvas.drawText(circles+"",xPosition-extendedCircleRadio/4,yPosition+extendedCircleRadio/2,this.extendedCircleLettersPaint);
                }
                canvas.restore();
                angle=angle+angleIncrementer;
            }

            if(currentFrame==AMOUNT_FRAMES){
                movementDirection=-1*movementDirection;
                currentFrame=1;
                moveExtendedCircle=false;
            }else{
                invalidate();
            }
            extendedCircleCentrePosition=extendedCircleCentrePosition+ extendedCircleCentrePositionIncrementer *movementDirection;
            extendedCircleRadio=extendedCircleRadio+extendedCircleRadioIncrementer*movementDirection;
        }
        canvas.drawCircle(startXPoint,startYPoint,mainCircleRadio,mainCirclePaint);
        canvas.drawText("Inicio",startXPoint-mainCircleRadio/2,startYPoint,this.mainCircleLetterPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(Math.hypot(this.startXPoint-event.getX(),this.startYPoint-event.getY())<this.mainCircleRadio)
        {
            this.moveExtendedCircle=true;
            invalidate();
        }
        for (CircleExpanded circleExpanded:this.circleExpanded) {
            if(circleExpanded.isThatPointInsideThisCircle(event.getX(),event.getY())){
                Toast.makeText(getContext(),circleExpanded.toast,Toast.LENGTH_SHORT).show();
                break;
            }
        }
        return false;
    }

    public void addCircle(String message,int color)
    {
        if(color==Color.WHITE)
        {
            throw new IllegalArgumentException("You can't create a circle with color white");
        }
        this.circleExpanded.add(new CircleExpanded(color,message));
        this.circleAmount++;
        this.angleIncrementer=this.calculateAngleIncrementer(this.circleAmount);
    }

    class CircleExpanded{

        int color;
        String toast;
        float x;
        float y;
        float radio;

        CircleExpanded(int color, String toast) {
            this.color = color;
            this.toast = toast;
        }

        boolean isThatPointInsideThisCircle(float x, float y)
        {
            Double distance=Math.hypot(this.x-x,this.y-y);
            return this.radio>=distance;
        }
    }
}
