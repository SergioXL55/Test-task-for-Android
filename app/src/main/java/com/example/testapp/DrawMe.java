package com.example.testapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class DrawMe extends View {

    private Paint paint = new Paint();//рисовалка
    private MainActivity ma;

    public DrawMe(Context context) {
        super(context);
        ma = new MainActivity();
    }

    @Override
    public void draw(Canvas c) {
        super.draw(c);
        paint.setColor(MainActivity.PARAM[ma.typeMe]);//цвет круга
        paint.setStrokeWidth(MainActivity.LINE);//толщина круга
        paint.setStyle(Paint.Style.STROKE);//заливка
        paint.setAntiAlias(true);//плавность
        final RectF oval = new RectF(MainActivity.LINE, MainActivity.LINE, MainActivity.RADIUS, MainActivity.RADIUS);
        c.drawArc(oval, 5, 350, false, paint); //рисование не полного круга 350 гр.
    }

}
