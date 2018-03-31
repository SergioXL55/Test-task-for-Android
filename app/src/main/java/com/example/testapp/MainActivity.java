package com.example.testapp;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView im;// наше изображение
    final static int RADIUS = 392;//диаметр круга
    final static int LINE = 20;//толщина круга
    public int typeMe = 0;//0 hot, 1 cold
    final static int ANIM_SPEED = 3000;//скорость прокрутки
    final static int[] PARAM = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        im = (ImageView) findViewById(R.id.imageView);
        //********************
        PARAM[0] = Color.RED;//цвет для холодного сч.
        PARAM[1] = Color.BLUE;//цвет для горячего сч.
        //********************
        //Вариант 1 с картинокй
        //im.setImageResource(R.drawable.ring);

        //Вариант 2 с рисованием круга
        create_circle();

        im.setOnClickListener(new View.OnClickListener() {  //нажатие на круг
            @Override
            public void onClick(View view) {
                rotate_me();
                color_me();
            }
        });
    }

    //рисование круга
    void create_circle() {
        DrawMe drawView = new DrawMe(this); //создание экземпляра класса Draw
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // конфигурация изобраджения
        Bitmap bmp = Bitmap.createBitmap(RADIUS + LINE, RADIUS + LINE, conf); // создание Bitmap
        Canvas canvas = new Canvas(bmp); // создание Canvas
        drawView.draw(canvas); // зарисовка Bitmap
        Drawable d = new BitmapDrawable(getResources(), bmp); // конвертируем из Bitmap в Drawable
        im.setImageDrawable(d);// применение рисунка для нашего Imageview
    }

    //вращение круга
    void rotate_me() {
        ObjectAnimator animation = ObjectAnimator.ofFloat(im, View.ROTATION, 0.0f, 360f);//добавляем анимацию вращения на опр. угол для нашего круга
        animation.setDuration(ANIM_SPEED);//скорость вращения
        animation.setRepeatCount(0);//количество повторов анимации
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.start();//запуск
    }

    //изменение цвета
    void color_me() {
        int colorFrom = PARAM[typeMe];//с какого цвета начать
        int colorTo = PARAM[Math.abs(typeMe - 1)];//каким цветом закончить
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(ANIM_SPEED); // время изменения
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                im.setColorFilter((int) animator.getAnimatedValue());//привязываем анимацию к нашему кругу
            }

        });
        colorAnimation.start();//запуск анимации
        typeMe = Math.abs(typeMe - 1);// измекняет тип cold/hot
    }
}
