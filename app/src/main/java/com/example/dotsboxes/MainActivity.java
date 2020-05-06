package com.example.dotsboxes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;

public class MainActivity extends AppCompatActivity {
    int n;
    MyCanvasView myCanvasView;
    int black;
    Button undo;



    ConstraintLayout cons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        n = 3;
        cons = (ConstraintLayout) findViewById(R.id.layout);
        undo = (Button) findViewById(R.id.undo);
    }

    public void nextr(View view){
        n++;
        if(n>5)
            n=3;
        TextView t = (TextView) findViewById(R.id.textView);
        String s = n + "x" + n;
        t.setText(s);
        ImageView i = (ImageView) findViewById(R.id.imageView);
        if(n==3) i.setImageResource(R.drawable.a);
        else if (n==4) i.setImageResource(R.drawable.b);
        else i.setImageResource(R.drawable.c);
    }
    public void nextl(View view){
        n--;
        char c;
        if(n<3)
            n=5;
        TextView t = (TextView) findViewById(R.id.textView);
        String s = n + "x" + n;
        t.setText(s);
        ImageView i = (ImageView) findViewById(R.id.imageView);
        if(n==3) i.setImageResource(R.drawable.a);
        else if (n==4) i.setImageResource(R.drawable.b);
        else i.setImageResource(R.drawable.c);
    }

    public void play(View view){
        ImageView i = (ImageView) findViewById(R.id.imageView);
        i.setVisibility(View.INVISIBLE);
        Button b1 = (Button) findViewById(R.id.next1);
        Button b3 = (Button) findViewById(R.id.play);
        Button b2 = (Button) findViewById(R.id.next2);
        Button res = (Button) findViewById(R.id.reset);
        TextView t = (TextView) findViewById(R.id.textView);
        t.setVisibility(View.INVISIBLE);
        b1.setVisibility(View.INVISIBLE);
        b2.setVisibility(View.INVISIBLE);
        undo.setVisibility(View.VISIBLE);
        b3.setVisibility(View.INVISIBLE);
        myCanvasView = new MyCanvasView(this);
        cons.addView(myCanvasView);
        res.setVisibility(View.VISIBLE);
    }

    public void reset(View view){
        black = ResourcesCompat.getColor(getResources(), R.color.back, null);
        Button res = (Button) findViewById(R.id.reset);
        res.setVisibility(View.INVISIBLE);
        cons.removeView(myCanvasView);
        undo.setVisibility(View.INVISIBLE);
        ImageView i = (ImageView) findViewById(R.id.imageView);
        i.setVisibility(View.VISIBLE);
        Button b1 = (Button) findViewById(R.id.next1);
        Button b3 = (Button) findViewById(R.id.play);
        Button b2 = (Button) findViewById(R.id.next2);
        TextView t = (TextView) findViewById(R.id.textView);
        t.setVisibility(View.VISIBLE);
        b1.setVisibility(View.VISIBLE);
        b2.setVisibility(View.VISIBLE);
        b3.setVisibility(View.VISIBLE);

    }
    public void undo(View view){
        myCanvasView.vundo();
    }

    public class MyCanvasView extends View {

        private Canvas can;
        public Bitmap bit;
        public Canvas mcan;
        public Paint paint;
        public Paint mpaint;
        public Paint bpaint;
        int sw ;
        int sh;
        float[] xc;
        float[] yc;
        int ix;
        int iy;
        int flag;
        int[] ftrack;
        int[] ktrack1;
        int[] ktrack2;
        float mx;
        float my;
        int pl;
        int blue;
        int red;
        int blue2;
        int red2;
        int nx;
        int ny;
        int nc;
        int[] cx;
        int[] cy;
        float rx1;
        float ry1;
        float rx2;
        float ry2;
        List<String> lx;
        List<String> ly;

        MyCanvasView(Context context) {
            this(context, null);
        }

        public MyCanvasView(Context context, AttributeSet attrs) {
            super(context);
            pl = 1;
            nc = 0;
            int z = 2*n*(n-1);
            ftrack = new int[z];
            ktrack1 = new int[z];
            ktrack2 = new int[z];
            cx = new int[z];
            cy = new int[z];
            paint = new Paint();
            mpaint = new Paint();
            bpaint = new Paint();
            int mColor = ResourcesCompat.getColor(getResources(), R.color.white, null);
            blue = ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null);
            red = ResourcesCompat.getColor(getResources(), R.color.red, null);
            blue2 = ResourcesCompat.getColor(getResources(), R.color.blue2, null);
            red2 = ResourcesCompat.getColor(getResources(), R.color.red2, null);
            black = ResourcesCompat.getColor(getResources(), R.color.back, null);
            paint.setColor(mColor);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(35);

            mpaint.setColor(red);
            mpaint.setAntiAlias(true);
            mpaint.setStyle(Paint.Style.STROKE);
            mpaint.setStrokeJoin(Paint.Join.ROUND);
            mpaint.setStrokeCap(Paint.Cap.ROUND);
            mpaint.setStrokeWidth(15);

            bpaint.setColor(red2);
            bpaint.setAntiAlias(true);
            bpaint.setStyle(Paint.Style.STROKE);
            bpaint.setStrokeJoin(Paint.Join.ROUND);
            bpaint.setStrokeCap(Paint.Cap.ROUND);
            bpaint.setStrokeWidth(0);
            bpaint.setStyle(Paint.Style.FILL_AND_STROKE);

            DisplayMetrics displayMetrics = new DisplayMetrics();

            ((Activity) getContext()).getWindowManager()
                    .getDefaultDisplay()
                    .getMetrics(displayMetrics);


            sw = displayMetrics.widthPixels;
            sh = displayMetrics.heightPixels;
            flag = 0;
            lx = new ArrayList<String>();
            ly = new ArrayList<String>();
            nx = 0;
            ny = 0;
            int y = ((sh - sw +400)/2)-100;
            int w = (sw - 400);
            layout(0,sw,y,y+w+100);
        }


        protected void onSizeChanged(int width, int height,
                                     int oldWidth, int oldHeight) {
            super.onSizeChanged(width, height, oldWidth, oldHeight);
            bit = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
            mcan = new Canvas(bit);

        }




        @SuppressLint({"ClickableViewAccessibility", "DrawAllocation"})
        public void onDraw(final Canvas can){
            super.onDraw(can);
            if(bit!=null)
                can.drawBitmap(bit,0,0,null);

            int w = (sw - 400)/(n-1);
            int y = (sh - sw +400)/2;

            xc = new float[n];
            yc = new float[n];
            for (int i = 0;i<n;i++,y+=w){
                int x = 200;
                for (int j=0;j<n;j++,x+=w){
                    can.drawLine(x,y,x,y,paint);
                    xc[j] = x;
                }
                yc[i] = y;
            }

            if(pl % 2 == 0){
                mpaint.setColor(red);
                bpaint.setColor(red2);
            }
            else {
                mpaint.setColor(blue);
                bpaint.setColor(blue2);
            }
            if(flag == 1) {
                if (ix != 100 || iy != 100) {
                    int f = 0;
                    String str = "x" + ix + iy;
                    if(lx.contains(str)) f =1 ;
                    if(f==0) {
                        mcan.drawLine(xc[ix], yc[iy], xc[ix + 1], yc[iy], mpaint);
                        pl++;
                        cx[nc] = ix;
                        cy[nc] = iy;
                        lx.add(nx,str);
                        nx++;
                        check(ix,iy);
                    }

                }

            }
            else if(flag == 2) {
                if (ix != 5 || iy != 5) {
                    int f = 0;
                    String str = "y" + iy + ix;
                    if(ly.contains(str)) f =1 ;
                    if(f==0) {
                        mcan.drawLine(xc[ix], yc[iy], xc[ix], yc[iy + 1], mpaint);
                        pl++;
                        ly.add(ny,str);
                        cx[nc] = ix;
                        cy[nc] = iy;
                        ny++;
                        check(ix,iy);
                    }
                }
            }
            can.save();
            can.restore();
        }


        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float px = event.getX();
            float py = event.getY();
            mx = px;
            my = py;
            ix = 100;
            iy = 100;
            for (int i=0;i<n;i++) {
                if (Math.abs(py - yc[i]) < 50) {

                    iy = i;
                    for (int c = 0; c < (n - 1); c++) {
                        if ((xc[c] <= px && px <= xc[c + 1])) {
                            flag = 1;
                            ix = c;
//                            nc++;
                            break;
                        }
                    }
                } else if (Math.abs(px - xc[i]) < 50) {

                    ix = i;
                    for (int c = 0; c < (n - 1); c++) {
                        if ((yc[c] <= py && py <= yc[c+1])) {
                            flag = 2;
                            iy = c;
//                            cy[nc] = iy;
//                            cx[nc] = ix;
//                            nc++;
                            break;
                        }
                    }
                }
            }

            if(flag ==1 ||flag == 2) {
                invalidate();
            }
            return true;
        }

        public void vundo(){
            int i = 0;
            if(nc!=0) {
                nc--;
                int x = cx[nc];
                int y = cy[nc];
                if (ftrack[nc]== 1) {
                    lx.remove(--nx);
                    mpaint.setColor(black);
                    bpaint.setColor(black);
                    mcan.drawLine(xc[x], yc[y], xc[x+1], yc[y], mpaint);
                } else if (ftrack[nc] == 2) {
                    ly.remove(--ny);
                    mpaint.setColor(black);
                    bpaint.setColor(black);
                    mcan.drawLine(xc[x], yc[y], xc[x], yc[y+1], mpaint);
                }
                if (ktrack1[nc] == 1) {
                    rx1 = xc[x];
                    ry1 = yc[y];
                    rx2 = xc[x+ 1];
                    ry2 = yc[y+ 1];
                    Rect rec = new Rect((int) rx1, (int) ry1, (int) rx2, (int) ry2);
                    mcan.drawRect(rec, bpaint);
                }
                else if (ktrack1[nc] == 3) {
                    rx1 = xc[x];
                    ry1 = yc[y];
                    rx2 = xc[x+ 1];
                    ry2 = yc[y+ 1];
                    Rect rec = new Rect((int) rx1, (int) ry1, (int) rx2, (int) ry2);
                    mcan.drawRect(rec, bpaint);
                }
                if (ktrack2[nc] == 2) {
                    rx1 = xc[x];
                    ry1 = yc[y-1];
                    rx2 = xc[x+ 1];
                    ry2 = yc[y];
                    Rect rec = new Rect((int) rx1, (int) ry1, (int) rx2, (int) ry2);
                    mcan.drawRect(rec, bpaint);
                }
               else if (ktrack2[nc] == 4) {
                    rx1 = xc[x- 1];
                    ry1 = yc[y];
                    rx2 = xc[x];
                    ry2 = yc[y+ 1];
                    Rect rec = new Rect((int) rx1, (int) ry1, (int) rx2, (int) ry2);
                    mcan.drawRect(rec, bpaint);
                }
            }

        }





        public void check(int x, int y) {
            int f=0;
            int k1 = 0;
            int k2 = 0;
            if(flag==1){
                String sx1 = "x" + (x) + (y+1);
                String sx2 = "x" + (x) + (y-1);
                if(lx.contains(sx1)) {
                    String s1 = "y" + y + x;
                    String s2 = "y" + y + (x+1);
                    if(ly.contains(s1) && ly.contains(s2)) {
                        k1 =1;
                        f=1;
                        rx1 = xc[x];
                        ry1 = yc[y];
                        rx2 = xc[x+1];
                        ry2 = yc[y+1];
                        Rect rec = new Rect((int)rx1,(int)ry1,(int)rx2,(int)ry2);
                        mcan.drawRect(rec,bpaint);

                    }
                }
                if(lx.contains(sx2)){
                    String s1 = "y" + (y-1) + x;
                    String s2 = "y" + (y-1) + (x+1);
                    if(ly.contains(s1) && ly.contains(s2)) {
                        k2 = 2;
                        f=1;
                        rx1 = xc[x];
                        ry1 = yc[y-1];
                        rx2 = xc[x+1];
                        ry2 = yc[y];
                        Rect rec = new Rect((int)rx1,(int)ry1,(int)rx2,(int)ry2);
                        mcan.drawRect(rec,bpaint);
                    }
                }
            }
            else if(flag == 2){
                String sy1 = "y" + (y) + (x+1);
                String sy2 = "y" + (y) + (x-1);
                if(ly.contains(sy1)) {
                    String s1 = "x" + x + y;
                    String s2 = "x" + x + (y+1);
                    if(lx.contains(s1) && lx.contains(s2)) {
                        k1 =3;
                        f=1;
                        rx1 = xc[x];
                        ry1 = yc[y];
                        rx2 = xc[x+1];
                        ry2 = yc[y+1];
                        Rect rec = new Rect((int)rx1,(int)ry1,(int)rx2,(int)ry2);
                        mcan.drawRect(rec,bpaint);
                    }
                }
                if(ly.contains(sy2)){
                    String s1 = "x" + (x-1) + y;
                    String s2 = "x" + (x-1) + (y+1);
                    if(lx.contains(s1) && lx.contains(s2)) {
                        k2 = 4;
                        f=1;
                        rx1 = xc[x-1];
                        ry1 = yc[y];
                        rx2 = xc[x];
                        ry2 = yc[y+1];
                        Rect rec = new Rect((int)rx1,(int)ry1,(int)rx2,(int)ry2);
                        mcan.drawRect(rec,bpaint);
                    }
                }
            }
            if(f == 1){
                pl++;
                Vibrator vibrator;
                vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= 26) {
                    assert vibrator != null;
                    vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                }
            }
            ktrack1[nc] = k1;
            ktrack2[nc] = k2;
            ftrack[nc] = flag;
            nc++;
        }
    }

}
