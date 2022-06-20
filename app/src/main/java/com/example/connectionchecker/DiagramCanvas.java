package com.example.connectionchecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;

public class DiagramCanvas extends AppCompatActivity {

    private float pr = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DiagramCanv(this));
        Intent intent = getIntent();
        if(intent.hasExtra("pr")) {
            pr = intent.getFloatExtra("pr", 0);
            System.out.println(pr);
        }
    }

    public class DiagramCanv extends View
    {

        public DiagramCanv(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas){
            super.onDraw(canvas);
            int x = getWidth(),y= getHeight(),radius = 400;
            Paint paint  = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.rgb(1,1,1));
            canvas.drawPaint(paint);
            paint.setColor(Color.rgb(200,33,33));
            canvas.drawCircle(x/2,y/2,radius,paint);
            paint.setColor(Color.rgb(33,200,33));
            RectF rectF = new RectF(x/2-radius, y/2-radius, x/2+radius, y/2+radius);
            canvas.drawArc(rectF,-90,pr * 360,true,paint);
            paint.setColor(Color.rgb(1,1,1));
            canvas.drawCircle(x/2,y/2,radius - 50,paint);
            paint.setColor(Color.rgb(255,255,255));
            paint.setTextSize(100);
            String text = String.format("%.2f",pr * 100.f) + "%";
            Rect textBounds = new Rect();
            paint.getTextBounds(text,0,text.length(),textBounds);
            float textWidth = paint.measureText(text);
            canvas.drawText(text,(x/2) - textWidth/2f,y/2 + 550,paint);

        }

    }

}