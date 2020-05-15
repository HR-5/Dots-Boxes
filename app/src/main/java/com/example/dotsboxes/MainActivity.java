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
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    int n;
    MyCanvasView myCanvasView;
    int black;
    int ptot;
    int f;
    Button undo;
    int[] score;


    ConstraintLayout cons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode);
    }

    public void single(View view) {
        Intent in = new Intent(MainActivity.this, SinglePlayer.class);
        startActivity(in);
    }

    public void multi(View view) {
        setContentView(R.layout.activity_main);
        f = 2;
        n = 3;
        cons = (ConstraintLayout) findViewById(R.id.layout);
        undo = (Button) findViewById(R.id.undo);
        ptot = 0;
    }

    public void nextr(View view) {
        n++;
        if (n > 5)
            n = 3;
        TextView t = (TextView) findViewById(R.id.textView);
        String s = n + "x" + n;
        t.setText(s);
        ImageView i = (ImageView) findViewById(R.id.imageView);
        if (n == 3) i.setImageResource(R.drawable.a);
        else if (n == 4) i.setImageResource(R.drawable.b);
        else i.setImageResource(R.drawable.c);
    }

    public void nextl(View view) {
        n--;
        char c;
        if (n < 3)
            n = 5;
        TextView t = (TextView) findViewById(R.id.textView);
        String s = n + "x" + n;
        t.setText(s);
        ImageView i = (ImageView) findViewById(R.id.imageView);
        if (n == 3) i.setImageResource(R.drawable.a);
        else if (n == 4) i.setImageResource(R.drawable.b);
        else i.setImageResource(R.drawable.c);
    }

    public void play(View view) {
        if (f == 2) {
            EditText player = findViewById(R.id.player);
            String str = player.getText().toString();
            TextView t2 = findViewById(R.id.textView2);
            String s = "";
            if (str.equals(s)) ptot = 7;
            else
                ptot = Integer.parseInt(str);
            if (ptot == 2 || ptot == 3 || ptot == 4) {
                ImageView i = (ImageView) findViewById(R.id.imageView);
                i.setVisibility(View.INVISIBLE);
                undo = (Button) findViewById(R.id.undo);
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
                player.setVisibility(View.INVISIBLE);
                t2.setVisibility(View.INVISIBLE);
                myCanvasView = new MyCanvasView(this);
                cons.addView(myCanvasView);
                res.setVisibility(View.VISIBLE);
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Enter Valid Data", Toast.LENGTH_SHORT);
                toast.show();
            }
        } else if (f == 1) {

            Intent in = new Intent(MainActivity.this, SinglePlayer.class);
            startActivity(in);
//            singles = new Singles(this);
//            cons.addView(singles);

        }
    }

    public void reset(View view) {
        black = ResourcesCompat.getColor(getResources(), R.color.back, null);
        Button res = (Button) findViewById(R.id.reset);
        res.setVisibility(View.INVISIBLE);
        Button res2 = (Button) findViewById(R.id.reset2);
        res2.setVisibility(View.INVISIBLE);
        cons.removeView(myCanvasView);
        undo.setVisibility(View.INVISIBLE);
        ImageView i = (ImageView) findViewById(R.id.imageView);
        i.setVisibility(View.VISIBLE);
        Button b1 = (Button) findViewById(R.id.next1);
        Button b3 = (Button) findViewById(R.id.play);
        Button b2 = (Button) findViewById(R.id.next2);
        TextView t2 = findViewById(R.id.textView2);
        EditText player = findViewById(R.id.player);
        TextView t = (TextView) findViewById(R.id.textView);
        t.setVisibility(View.VISIBLE);
        b1.setVisibility(View.VISIBLE);
        b2.setVisibility(View.VISIBLE);
        b3.setVisibility(View.VISIBLE);
        if(f == 2){
            player.setVisibility(View.VISIBLE);
            t2.setVisibility(View.VISIBLE);
        }

    }

    @SuppressLint("ResourceAsColor")
    public void result() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.result);
                TextView t = (TextView) findViewById(R.id.blue);
                String str = "Blue: " + score[0];
                t.setText(str);
                t = (TextView) findViewById(R.id.red);
                str = "Red: " + score[1];
                t.setText(str);
                if (ptot > 2) {
                    t = (TextView) findViewById(R.id.org);
                    str = "Orange: " + score[2];
                    t.setText(str);
                    t.setVisibility(View.VISIBLE);
                }
                if (ptot > 3) {
                    t = (TextView) findViewById(R.id.green);
                    str = "Green: " + score[3];
                    t.setText(str);
                    t.setVisibility(View.VISIBLE);
                }
            }
        }, 300);
    }

    public void home(View view) {
        Intent i = new Intent(MainActivity.this, MainActivity.class);
        startActivity(i);
    }

    public void undo(View view) {
        myCanvasView.vundo();
    }

    public class MyCanvasView extends View {
        public Bitmap bit;
        public Canvas mcan;
        public Paint paint;
        public Paint mpaint;
        public Paint bpaint;
        int sw;
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
        int o11;
        int o12;
        int o21;
        int o22;
        int o13;
        int o14;
        int o23;
        int o24;
        int nx;
        int ny;
        int nc;
        int[] cx;
        int[] cy;
        float rx1;
        float ry1;
        float rx2;
        float ry2;
        int r;
        List<String> lx;
        List<String> ly;

        MyCanvasView(Context context) {
            this(context, null);
        }

        public MyCanvasView(Context context, AttributeSet attrs) {
            super(context);
            pl = 1;
            nc = 0;
            score = new int[ptot];
            int z = 2 * n * (n - 1);
            ftrack = new int[z];
            ktrack1 = new int[z];
            ktrack2 = new int[z];
            cx = new int[z];
            cy = new int[z];
            paint = new Paint();
            mpaint = new Paint();
            bpaint = new Paint();
            int mColor = ResourcesCompat.getColor(getResources(), R.color.white, null);
            o11 = ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null);
            o12 = ResourcesCompat.getColor(getResources(), R.color.red, null);
            o21 = ResourcesCompat.getColor(getResources(), R.color.blue2, null);
            o22 = ResourcesCompat.getColor(getResources(), R.color.red2, null);
            o13 = ResourcesCompat.getColor(getResources(), R.color.green, null);
            o14 = ResourcesCompat.getColor(getResources(), R.color.orange, null);
            o23 = ResourcesCompat.getColor(getResources(), R.color.green2, null);
            o24 = ResourcesCompat.getColor(getResources(), R.color.orange2, null);
            black = ResourcesCompat.getColor(getResources(), R.color.back, null);
            paint.setColor(mColor);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(35);

            mpaint.setColor(o11);
            mpaint.setAntiAlias(true);
            mpaint.setStyle(Paint.Style.STROKE);
            mpaint.setStrokeJoin(Paint.Join.ROUND);
            mpaint.setStrokeCap(Paint.Cap.ROUND);
            mpaint.setStrokeWidth(15);

            bpaint.setColor(o21);
            bpaint.setAntiAlias(true);
            bpaint.setStyle(Paint.Style.STROKE);
            bpaint.setStrokeJoin(Paint.Join.ROUND);
            bpaint.setStrokeCap(Paint.Cap.ROUND);
            bpaint.setStrokeWidth(0);
            bpaint.setStyle(Paint.Style.FILL);

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
            int y = ((sh - sw + 400) / 2) - 100;
            int w = (sw - 400);
            layout(0, sw, y, y + w + 100);
        }

        protected void onSizeChanged(int width, int height,
                                     int oldWidth, int oldHeight) {
            super.onSizeChanged(width, height, oldWidth, oldHeight);
            bit = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            mcan = new Canvas(bit);
        }

        @SuppressLint({"ClickableViewAccessibility", "DrawAllocation"})
        public void onDraw(final Canvas can) {
            super.onDraw(can);
            if (bit != null)
                can.drawBitmap(bit, 0, 0, null);

            int w = (sw - 400) / (n - 1);
            int y = (sh - sw + 400) / 2;

            xc = new float[n];
            yc = new float[n];
            for (int i = 0; i < n; i++, y += w) {
                int x = 200;
                for (int j = 0; j < n; j++, x += w) {
                    can.drawLine(x, y, x, y, paint);
                    xc[j] = x;
                }
                yc[i] = y;
            }

            if (pl % ptot == 1) {
                r = 1;
                mpaint.setColor(o12);
                bpaint.setColor(o22);

            } else if (pl % ptot == 2) {
                r = 2;
                mpaint.setColor(o14);
                bpaint.setColor(o24);
            } else if (pl % ptot == 3) {
                r = 3;
                mpaint.setColor(o13);
                bpaint.setColor(o23);
            } else if (pl % ptot == 0) {
                r = 0;
                mpaint.setColor(o11);
                bpaint.setColor(o21);
            }

            if (flag == 1) {
                if (ix != 100 || iy != 100) {
                    int f = 0;
                    String str = "x" + ix + iy;
                    if (lx.contains(str)) f = 1;
                    if (f == 0) {
                        mcan.drawLine(xc[ix], yc[iy], xc[ix + 1], yc[iy], mpaint);
                        MediaPlayer ring = MediaPlayer.create(MainActivity.this, R.raw.sound);
                        ring.start();
                        cx[nc] = ix;
                        cy[nc] = iy;
                        lx.add(nx, str);
                        nx++;
                        check(ix, iy);
                    }

                }

            } else if (flag == 2) {
                if (ix != 5 || iy != 5) {
                    int f = 0;
                    String str = "y" + iy + ix;
                    if (ly.contains(str)) f = 1;
                    if (f == 0) {
                        mcan.drawLine(xc[ix], yc[iy], xc[ix], yc[iy + 1], mpaint);
                        MediaPlayer ring = MediaPlayer.create(MainActivity.this, R.raw.sound);
                        ring.start();
                        ly.add(ny, str);
                        cx[nc] = ix;
                        cy[nc] = iy;
                        ny++;
                        check(ix, iy);
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
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                mx = px;
                my = py;
                ix = 100;
                iy = 100;
                int g = 0;
                for (int i = 0; i < n; i++) {
                    if (Math.abs(py - yc[i]) < 40) {

                        iy = i;
                        for (int c = 0; c < (n - 1); c++) {
                            if (((xc[c] + 5) <= px && px <= (xc[c + 1] - 5))) {
                                g = 1;
                                flag = 1;
                                ix = c;
                                break;
                            }
                        }
                        if (g == 1) break;
                    } else if (Math.abs(px - xc[i]) < 40) {

                        ix = i;
                        for (int c = 0; c < (n - 1); c++) {
                            if (((yc[c] + 5) <= py && py <= (yc[c + 1] - 5))) {
                                g = 2;
                                flag = 2;
                                iy = c;
                                break;
                            }
                        }
                        if (g == 2) break;
                    }
                }

                if (g == 1 || g == 2) {
                    invalidate();
                }

            }
            return false;
        }

        public void vundo() {
            int i = 0;
            if (nc != 0) {
                nc--;
                pl--;
                mpaint.setStrokeWidth(20);
                int x = cx[nc];
                int y = cy[nc];
                if (ftrack[nc] == 1) {
                    lx.remove(--nx);
                    mpaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                    mcan.drawLine(xc[x], yc[y], xc[x + 1], yc[y], mpaint);
                } else if (ftrack[nc] == 2) {
                    ly.remove(--ny);
                    mpaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                    mcan.drawLine(xc[x], yc[y], xc[x], yc[y + 1], mpaint);
                }
                if (ktrack1[nc] == 1) {
                    i = 1;
                    rx1 = xc[x] + 20;
                    ry1 = yc[y] + 20;
                    rx2 = xc[x + 1] - 20;
                    ry2 = yc[y + 1] - 20;
                    Rect rec = new Rect((int) rx1, (int) ry1, (int) rx2, (int) ry2);
                    bpaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                    mcan.drawRect(rec, bpaint);
                } else if (ktrack1[nc] == 3) {
                    i = 1;
                    rx1 = xc[x] + 20;
                    ry1 = yc[y] + 20;
                    rx2 = xc[x + 1] - 20;
                    ry2 = yc[y + 1] - 20;
                    Rect rec = new Rect((int) rx1, (int) ry1, (int) rx2, (int) ry2);
                    bpaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                    mcan.drawRect(rec, bpaint);
                }
                if (ktrack2[nc] == 2) {
                    i = 1;
                    rx1 = xc[x] + 20;
                    ry1 = yc[y - 1] + 20;
                    rx2 = xc[x + 1] - 20;
                    ry2 = yc[y] - 20;
                    Rect rec = new Rect((int) rx1, (int) ry1, (int) rx2, (int) ry2);
                    bpaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                    mcan.drawRect(rec, bpaint);
                } else if (ktrack2[nc] == 4) {
                    i = 1;
                    rx1 = xc[x - 1] + 20;
                    ry1 = yc[y] + 20;
                    rx2 = xc[x] - 20;
                    ry2 = yc[y + 1] - 20;
                    Rect rec = new Rect((int) rx1, (int) ry1, (int) rx2, (int) ry2);
                    bpaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                    mcan.drawRect(rec, bpaint);
                }
                if (i == 1) pl++;
            }
            mpaint.setXfermode(null);
            bpaint.setXfermode(null);
            mpaint.setStrokeWidth(15);

        }

        public void check(int x, int y) {
            int f = 0;
            int k1 = 0;
            pl++;
            int k2 = 0;
            if (flag == 1) {
                String sx1 = "x" + (x) + (y + 1);
                String sx2 = "x" + (x) + (y - 1);
                if (lx.contains(sx1)) {
                    String s1 = "y" + y + x;
                    String s2 = "y" + y + (x + 1);
                    if (ly.contains(s1) && ly.contains(s2)) {
                        k1 = 1;
                        f = 1;
                        rx1 = xc[x] + 20;
                        ry1 = yc[y] + 20;
                        rx2 = xc[x + 1] - 20;
                        ry2 = yc[y + 1] - 20;
                        Rect rec = new Rect((int) rx1, (int) ry1, (int) rx2, (int) ry2);
                        mcan.drawRect(rec, bpaint);
                        score[r]++;

                    }
                }
                if (lx.contains(sx2)) {
                    String s1 = "y" + (y - 1) + x;
                    String s2 = "y" + (y - 1) + (x + 1);
                    if (ly.contains(s1) && ly.contains(s2)) {
                        k2 = 2;
                        f = 1;
                        rx1 = xc[x] + 20;
                        ry1 = yc[y - 1] + 20;
                        rx2 = xc[x + 1] - 20;
                        ry2 = yc[y] - 20;
                        Rect rec = new Rect((int) rx1, (int) ry1, (int) rx2, (int) ry2);
                        mcan.drawRect(rec, bpaint);
                        score[r]++;
                    }
                }
            } else if (flag == 2) {
                String sy1 = "y" + (y) + (x + 1);
                String sy2 = "y" + (y) + (x - 1);
                if (ly.contains(sy1)) {
                    String s1 = "x" + x + y;
                    String s2 = "x" + x + (y + 1);
                    if (lx.contains(s1) && lx.contains(s2)) {
                        k1 = 3;
                        f = 1;
                        rx1 = xc[x] + 20;
                        ry1 = yc[y] + 20;
                        rx2 = xc[x + 1] - 20;
                        ry2 = yc[y + 1] - 20;
                        Rect rec = new Rect((int) rx1, (int) ry1, (int) rx2, (int) ry2);
                        mcan.drawRect(rec, bpaint);
                        score[r]++;
                    }
                }
                if (ly.contains(sy2)) {
                    String s1 = "x" + (x - 1) + y;
                    String s2 = "x" + (x - 1) + (y + 1);
                    if (lx.contains(s1) && lx.contains(s2)) {
                        k2 = 4;
                        f = 1;
                        rx1 = xc[x - 1] + 20;
                        ry1 = yc[y] + 20;
                        rx2 = xc[x] - 20;
                        ry2 = yc[y + 1] - 20;
                        Rect rec = new Rect((int) rx1, (int) ry1, (int) rx2, (int) ry2);
                        mcan.drawRect(rec, bpaint);
                        score[r]++;
                    }
                }
            }
            if (f == 1) {
                pl--;
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
            if (nc >= (2 * n * (n - 1))) {
                result();
            }
        }
    }
}