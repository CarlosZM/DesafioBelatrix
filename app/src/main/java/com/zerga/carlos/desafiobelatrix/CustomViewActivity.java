package com.zerga.carlos.desafiobelatrix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.pizarra21.myapplication.R;

public class CustomViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SnowFlakeView snowFlakeView=new SnowFlakeView(this);
        snowFlakeView.addCircle("Lunes", Color.BLUE);
        snowFlakeView.addCircle("Martes", Color.BLUE);
        snowFlakeView.addCircle("Miercoles", Color.RED);
        snowFlakeView.addCircle("Jueves", Color.YELLOW);
        snowFlakeView.addCircle("Viernes", Color.BLACK);
        snowFlakeView.addCircle("Lunes", Color.BLUE);
        snowFlakeView.addCircle("Martes", Color.BLUE);
        snowFlakeView.addCircle("Miercoles", Color.RED);
        snowFlakeView.addCircle("Jueves", Color.YELLOW);
        snowFlakeView.addCircle("Viernes", Color.BLACK);
        snowFlakeView.addCircle("Lunes", Color.BLUE);
        snowFlakeView.addCircle("Martes", Color.BLUE);
        snowFlakeView.addCircle("Miercoles", Color.RED);
        snowFlakeView.addCircle("Jueves", Color.YELLOW);
        snowFlakeView.addCircle("Viernes", Color.BLACK);
        snowFlakeView.addCircle("Lunes", Color.BLUE);
        snowFlakeView.addCircle("Martes", Color.BLUE);
        snowFlakeView.addCircle("Miercoles", Color.RED);
        snowFlakeView.addCircle("Jueves", Color.YELLOW);
        snowFlakeView.addCircle("Viernes", Color.BLACK);
        snowFlakeView.addCircle("Lunes", Color.BLUE);
        snowFlakeView.addCircle("Martes", Color.BLUE);
        snowFlakeView.addCircle("Miercoles", Color.RED);
        snowFlakeView.addCircle("Jueves", Color.YELLOW);
        snowFlakeView.addCircle("Viernes", Color.BLACK);
        snowFlakeView.addCircle("Lunes", Color.BLUE);
        snowFlakeView.addCircle("Martes", Color.BLUE);
        snowFlakeView.addCircle("Miercoles", Color.RED);
        snowFlakeView.addCircle("Jueves", Color.YELLOW);
        snowFlakeView.addCircle("Viernes", Color.BLACK);
        setContentView(snowFlakeView);
    }
}
