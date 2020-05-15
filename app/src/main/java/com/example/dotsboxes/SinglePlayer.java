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
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SinglePlayer extends AppCompatActivity {
    int w,r,lvl;
    ConstraintLayout cons;
    int[] score;
    Singles single;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView t = (TextView) findViewById(R.id.textView2);
        EditText e = (EditText) findViewById(R.id.player);
        t.setVisibility(View.INVISIBLE);
        e.setVisibility(View.INVISIBLE);
        w = 4;
        TextView t2 = (TextView) findViewById(R.id.textView);
        String s = w + "x" + w;
        t2.setText(s);
        ImageView i = (ImageView) findViewById(R.id.imageView);
        i.setImageResource(R.drawable.b);
    }

    public void nextr(View view) {
        w++;
        if (w > 5)
            w = 4;
        TextView t = (TextView) findViewById(R.id.textView);
        String s = w + "x" + w;
        t.setText(s);
        ImageView i = (ImageView) findViewById(R.id.imageView);
        if (w == 4) i.setImageResource(R.drawable.b);
        else i.setImageResource(R.drawable.c);
    }

    public void nextl(View view) {
        w--;
        if (w < 4)
            w = 5;
        TextView t = (TextView) findViewById(R.id.textView);
        String s = w + "x" + w;
        t.setText(s);
        ImageView i = (ImageView) findViewById(R.id.imageView);
        if (w == 4) i.setImageResource(R.drawable.b);
        else i.setImageResource(R.drawable.c);
    }

    public void play(View view) {
        ImageView i = (ImageView) findViewById(R.id.imageView);
        i.setVisibility(View.INVISIBLE);
        Button undo = (Button) findViewById(R.id.undo);
        Button b1 = (Button) findViewById(R.id.next1);
        Button b3 = (Button) findViewById(R.id.play);
        Button b2 = (Button) findViewById(R.id.next2);
        TextView t = (TextView) findViewById(R.id.textView);
        t.setVisibility(View.INVISIBLE);
        b1.setVisibility(View.INVISIBLE);
        b2.setVisibility(View.INVISIBLE);
        b3.setVisibility(View.INVISIBLE);
        setContentView(R.layout.activity_single_player);
    }

    public void easy(View view){
        lvl = 10;
        Button ea = (Button) findViewById(R.id.easy);
        Button me = (Button) findViewById(R.id.medium);
        Button ha = (Button) findViewById(R.id.hard);
        ea.setVisibility(View.INVISIBLE);
        ha.setVisibility(View.INVISIBLE);
        me.setVisibility(View.INVISIBLE);
        cons = (ConstraintLayout) findViewById(R.id.si);
        single = new Singles(this);
        cons.addView(single);
    }
    public void medium(View view){
        lvl = 50;
        Button ea = (Button) findViewById(R.id.easy);
        Button me = (Button) findViewById(R.id.medium);
        Button ha = (Button) findViewById(R.id.hard);
        ea.setVisibility(View.INVISIBLE);
        ha.setVisibility(View.INVISIBLE);
        me.setVisibility(View.INVISIBLE);
        cons = (ConstraintLayout) findViewById(R.id.si);
        single = new Singles(this);
        cons.addView(single);
    }
    public void hard(View view){
        lvl = 100;
        Button ea = (Button) findViewById(R.id.easy);
        Button me = (Button) findViewById(R.id.medium);
        Button ha = (Button) findViewById(R.id.hard);
        ea.setVisibility(View.INVISIBLE);
        ha.setVisibility(View.INVISIBLE);
        me.setVisibility(View.INVISIBLE);
        cons = (ConstraintLayout) findViewById(R.id.si);
        single = new Singles(this);
        cons.addView(single);
    }

    public void reset(View view){
        Intent i = new Intent(SinglePlayer.this, SinglePlayer.class);
        startActivity(i);
    }

    public void home(View view) {
        Intent i = new Intent(SinglePlayer.this, MainActivity.class);
        startActivity(i);
    }

    public void result() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.result);
                TextView t = (TextView) findViewById(R.id.blue);
                String str = "Blue: " + score[1];
                t.setText(str);
                t = (TextView) findViewById(R.id.red);
                str = "Red: " + score[0];
                t.setText(str);
            }
        }, 800);
    }

    public class Singles extends View {
        int o11, o12, o21, o22;
        int sw, sh, ix, iy, nx;
        int ny, nc;
        float rx1, ry1, rx2, ry2;
        float[] xc, yc;
        public Bitmap bit;
        public Canvas mcan;
        public Paint paint, mpaint, bpaint;
        int[][] box;
        int player, u, v, zz, count;
        Boolean loop;
        int n, m;
        int[][] hedge, vedge;
        int nn, x, y;

        Singles(Context context) {
            this(context, null);
        }


        public Singles(Context context, AttributeSet attrs) {
            super(context);
            paint = new Paint();
            mpaint = new Paint();
            bpaint = new Paint();
            score = new int[2];
            score[0] = 0;
            score[1] = 0;
            nc = 0;
            n = w - 1;
            m = n;
            nn = 2 * n + 1;
            box = new int[w][w];
            player = 1;
            int mColor = ResourcesCompat.getColor(getResources(), R.color.white, null);
            o11 = ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null);
            o12 = ResourcesCompat.getColor(getResources(), R.color.red, null);
            o21 = ResourcesCompat.getColor(getResources(), R.color.blue2, null);
            o22 = ResourcesCompat.getColor(getResources(), R.color.red2, null);
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
            hedge = new int[w][w];
            vedge = new int[w][w];
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

        @SuppressLint("DrawAllocation")
        public void onDraw(final Canvas can) {
            super.onDraw(can);
            if (bit != null)
                can.drawBitmap(bit, 0, 0, null);

            int wid = (sw - 400) / (w - 1);
            int y = (sh - sw + 400) / 2;

            xc = new float[w];
            yc = new float[w];
            for (int i = 0; i < w; i++, y += wid) {
                int x = 200;
                for (int j = 0; j < w; j++, x += wid) {
                    can.drawLine(x, y, x, y, paint);
                    xc[j] = x;
                }
                yc[i] = y;
            }
        }

        @SuppressLint("ClickableViewAccessibility")
        public boolean onTouchEvent(MotionEvent event) {
            float px = event.getX();
            float py = event.getY();
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ix = 100;
                iy = 100;
                for (int i = 0; i < w; i++) {
                    if (Math.abs(py - yc[i]) < 40) {

                        iy = i;
                        for (int c = 0; c < (w - 1); c++) {
                            if (((xc[c] + 5) <= px && px <= (xc[c + 1] - 5))) {
                                mpaint.setColor(o11);
                                bpaint.setColor(o21);
                                ix = c;
                                hmove(iy, ix);
                                break;
                            }
                        }
                    } else if (Math.abs(px - xc[i]) < 40) {

                        ix = i;
                        for (int c = 0; c < (w - 1); c++) {
                            if (((yc[c] + 5) <= py && py <= (yc[c + 1] - 5))) {
                                mpaint.setColor(o11);
                                bpaint.setColor(o21);
                                iy = c;
                                vmove(iy, ix);
                                break;
                            }
                        }
                    }
                    invalidate();
                }

            }
            return false;
        }

        void hmove(int i, int j) {
            if (hedge[i][j] < 1) {
                sethedge(i, j);
                if (score[0] + score[1] == m * n) {
                    result();
                } else if (player == 0) {
                    mpaint.setColor(o12);
                    bpaint.setColor(o22);
                    Random rnd = new Random();
                    r = rnd.nextInt(100);
                    if (r < lvl)
                        makemove();
                    else
                        rand();
                }
            }
        }

        void vmove(int i, int j) {
            if (vedge[i][j] < 1) {
                setvedge(i, j);
                if (score[0] + score[1] == m * n) {
                    result();
                } else if (player == 0) {
                    mpaint.setColor(o12);
                    bpaint.setColor(o22);
                    Random rnd = new Random();
                    r = rnd.nextInt(100);
                    if (r < lvl)
                        makemove();
                    else
                        rand();
                }
            }
        }

        void sethedge(int x, int y) {
            hedge[x][y] = 1;
            if (x > 0) box[x - 1][y]++;
            if (x < m) box[x][y]++;
            mcan.drawLine(xc[y], yc[x], xc[y + 1], yc[x], mpaint);
            MediaPlayer ring = MediaPlayer.create(SinglePlayer.this, R.raw.sound);
            ring.start();
            checkh(x, y);
            player = 1 - player;
        }

        void setvedge(int x, int y) {
            vedge[x][y] = 1;
            if (y > 0) box[x][y - 1]++;
            if (y < n) box[x][y]++;
            mcan.drawLine(xc[y], yc[x], xc[y], yc[x + 1], mpaint);
            MediaPlayer ring = MediaPlayer.create(SinglePlayer.this, R.raw.sound);
            ring.start();
            checkv(x, y);
            player = 1 - player;
        }

        void takeedge() {
            if (zz > 1) setvedge(x, y);
            else sethedge(x, y);
        }

        void makemove() {
            mpaint.setColor(o12);
            bpaint.setColor(o22);
            takesafe3s();
            if (sides3()) {
                if (sides01()) {
                    takeall3s();
                    takeedge();
                } else {
                    sac(u, v);
                }
                if (score[0] + score[1] == m * n) {
                    result();
                }
            } else if (sides01()) takeedge();
            else if (singleton()) takeedge();
            else if (doubleton()) takeedge();
            else makeanymove();
        }

        void takesafe3s() {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (box[i][j] == 3) {
                        if (vedge[i][j] < 1) {
                            if (j == 0 || box[i][j - 1] != 2) setvedge(i, j);
                        } else if (hedge[i][j] < 1) {
                            if (i == 0 || box[i - 1][j] != 2) sethedge(i, j);
                        } else if (vedge[i][j + 1] < 1) {
                            if (j == n - 1 || box[i][j + 1] != 2) setvedge(i, j + 1);
                        } else {
                            if (i == m - 1 || box[i + 1][j] != 2) sethedge(i + 1, j);
                        }
                    }
                }
            }
        }

        Boolean sides3() {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (box[i][j] == 3) {
                        u = i;
                        v = j;
                        return true;
                    }
                }
            }
            return false;
        }

        void takeall3s() {
            while (sides3()) takebox(u, v);
        }

        Boolean sides01() {
            Random rnd = new Random();
            int s = rnd.nextInt(2);
            if (s == 0) zz = 1;
            else zz = 2;
            int i = rnd.nextInt(n);
            int j = rnd.nextInt(n);
            if (zz == 1) {
                if (randhedge(i, j)) return true;
                else {
                    zz = 2;
                    if (randvedge(i, j)) return true;
                }
            } else {
                if (randvedge(i, j)) return true;
                else {
                    zz = 1;
                    if (randhedge(i, j)) return true;
                }
            }
            return false;
        }

        Boolean safehedge(int i, int j) {
            if (hedge[i][j] < 1) {
                if (i == 0) {
                    if (box[i][j] < 2) return true;
                } else if (i == m) {
                    if (box[i - 1][j] < 2) return true;
                } else if (box[i][j] < 2 && box[i - 1][j] < 2) return true;
            }
            return false;
        }

        Boolean safevedge(int i, int j) {
            if (vedge[i][j] < 1) {
                if (j == 0) {
                    if (box[i][j] < 2) return true;
                } else if (j == n) {
                    if (box[i][j - 1] < 2) return true;
                } else if (box[i][j] < 2 && box[i][j - 1] < 2) return true;
            }
            return false;
        }

        Boolean randhedge(int i, int j) {
            x = i;
            y = j;
            do {
                if (safehedge(x, y)) return true;
                else {
                    y++;
                    if (y == n) {
                        y = 0;
                        x++;
                        if (x > m) x = 0;
                    }
                }
            } while (x != i || y != j);
            return false;
        }

        Boolean randvedge(int i, int j) {
            x = i;
            y = j;
            do {
                if (safevedge(x, y)) return true;
                else {
                    y++;
                    if (y > n) {
                        y = 0;
                        x++;
                        if (x == m) x = 0;
                    }
                }
            } while (x != i || y != j);
            return false;
        }

        Boolean singleton() {
            int numb;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (box[i][j] == 2) {
                        numb = 0;
                        if (hedge[i][j] < 1) {
                            if (i < 1 || box[i - 1][j] < 2) numb++;
                        }
                        zz = 2;
                        if (vedge[i][j] < 1) {
                            if (j < 1 || box[i][j - 1] < 2) numb++;
                            if (numb > 1) {
                                x = i;
                                y = j;
                                return true;
                            }
                        }
                        if (vedge[i][j + 1] < 1) {
                            if (j + 1 == n || box[i][j + 1] < 2) numb++;
                            if (numb > 1) {
                                x = i;
                                y = j + 1;
                                return true;
                            }
                        }
                        zz = 1;
                        if (hedge[i + 1][j] < 1) {
                            if (i + 1 == m || box[i + 1][j] < 2) numb++;
                            if (numb > 1) {
                                x = i + 1;
                                y = j;
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }

        Boolean doubleton() {
            zz = 2;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n - 1; j++) {
                    if (box[i][j] == 2 && box[i][j + 1] == 2 && vedge[i][j + 1] < 1) {
                        if (ldub(i, j) && rdub(i, j + 1)) {
                            x = i;
                            y = j + 1;
                            return true;
                        }
                    }
                }
            }
            zz = 1;
            for (int j = 0; j < n; j++) {
                for (int i = 0; i < m - 1; i++) {
                    if (box[i][j] == 2 && box[i + 1][j] == 2 && hedge[i + 1][j] < 1) {
                        if (udub(i, j) && ddub(i + 1, j)) {
                            x = i + 1;
                            y = j;
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        Boolean ldub(int i, int j) {
            if (vedge[i][j] < 1) {
                if (j < 1 || box[i][j - 1] < 2) return true;
            } else if (hedge[i][j] < 1) {
                if (i < 1 || box[i - 1][j] < 2) return true;
            } else if (i == m - 1 || box[i + 1][j] < 2) {
                return true;
            }
            return false;
        }

        Boolean rdub(int i, int j) {
            if (vedge[i][j + 1] < 1) {
                if (j + 1 == n || box[i][j + 1] < 2) return true;
            } else if (hedge[i][j] < 1) {
                if (i < 1 || box[i - 1][j] < 2) return true;
            } else if (i + 1 == m || box[i + 1][j] < 2) {
                return true;
            }
            return false;
        }

        Boolean udub(int i, int j) {
            if (hedge[i][j] < 1) {
                if (i < 1 || box[i - 1][j] < 2) return true;
            } else if (vedge[i][j] < 1) {
                if (j < 1 || box[i][j - 1] < 2) return true;
            } else if (j == n - 1 || box[i][j + 1] < 2) {
                return true;
            }
            return false;
        }

        Boolean ddub(int i, int j) {
            if (hedge[i + 1][j] < 1) {
                if (i == m - 1 || box[i + 1][j] < 2) return true;
            } else if (vedge[i][j] < 1) {
                if (j < 1 || box[i][j - 1] < 2) return true;
            } else if (j == n - 1 || box[i][j + 1] < 2) {
                return true;
            }
            return false;
        }

        void sac(int i, int j) {
            count = 0;
            loop = false;
            incount(0, i, j);
            if (!loop) takeallbut(i, j);
            if (count + score[0] + score[1] == m * n) {
                takeall3s();
            } else {
                if (loop) {
                    count = count - 2;
                }
                outcount(0, i, j);
                i = m;
                j = n;
            }
        }

        void incount(int k, int i, int j) {
            count++;
            if (k != 1 && vedge[i][j] < 1) {
                if (j > 0) {
                    if (box[i][j - 1] > 2) {
                        count++;
                        loop = true;
                    } else if (box[i][j - 1] > 1) incount(3, i, j - 1);
                }
            } else if (k != 2 && hedge[i][j] < 1) {
                if (i > 0) {
                    if (box[i - 1][j] > 2) {
                        count++;
                        loop = true;
                    } else if (box[i - 1][j] > 1) incount(4, i - 1, j);
                }
            } else if (k != 3 && vedge[i][j + 1] < 1) {
                if (j < n - 1) {
                    if (box[i][j + 1] > 2) {
                        count++;
                        loop = true;
                    } else if (box[i][j + 1] > 1) incount(1, i, j + 1);
                }
            } else if (k != 4 && hedge[i + 1][j] < 1) {
                if (i < m - 1) {
                    if (box[i + 1][j] > 2) {
                        count++;
                        loop = true;
                    } else if (box[i + 1][j] > 1) incount(2, i + 1, j);
                }
            }
        }

        void takeallbut(int x, int y) {
            while (sides3not(x, y)) {
                takebox(u, v);
            }
        }

        Boolean sides3not(int x, int y) {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (box[i][j] == 3) {
                        if (i != x || j != y) {
                            u = i;
                            v = j;
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        void takebox(int i, int j) {
            if (hedge[i][j] < 1) sethedge(i, j);
            else if (vedge[i][j] < 1) setvedge(i, j);
            else if (hedge[i + 1][j] < 1) sethedge(i + 1, j);
            else setvedge(i, j + 1);
        }

        void outcount(int k, int i, int j) {
            if (count > 0) {
                if (k != 1 && vedge[i][j] < 1) {
                    if (count != 2) setvedge(i, j);
                    count--;
                    outcount(3, i, j - 1);
                } else if (k != 2 && hedge[i][j] < 1) {
                    if (count != 2) sethedge(i, j);
                    count--;
                    outcount(4, i - 1, j);
                } else if (k != 3 && vedge[i][j + 1] < 1) {
                    if (count != 2) setvedge(i, j + 1);
                    count--;
                    outcount(1, i, j + 1);
                } else if (k != 4 && hedge[i + 1][j] < 1) {
                    if (count != 2) sethedge(i + 1, j);
                    count--;
                    outcount(2, i + 1, j);
                }
            }
        }

        void rand() {
            if (score[0] + score[1] == m * n) {
                result();
            } else {
                Random rnd = new Random();
                r = rnd.nextInt(100);
                x = -1;
                if (r < 50) {
                    for (int i = 0; i <= m; i++) {
                        for (int j = 0; j < n; j++) {
                            if (hedge[i][j] < 1) {
                                x = i;
                                y = j;
                                i = m + 1;
                                j = n;
                            }
                        }
                    }
                    if (x < 0) {
                        for (int i = 0; i < m; i++) {
                            for (int j = 0; j <= n; j++) {
                                if (vedge[i][j] < 1) {
                                    x = i;
                                    y = j;
                                    i = m;
                                    j = n + 1;
                                }
                            }
                        }
                        setvedge(x, y);
                    } else sethedge(x, y);
                } else if (r >= 50) {
                    for (int i = 0; i < m; i++) {
                        for (int j = 0; j <= n; j++) {
                            if (vedge[i][j] < 1) {
                                x = i;
                                y = j;
                                i = m;
                                j = n + 1;
                            }
                        }
                    }
                    if (x < 0) {
                        for (int i = 0; i <= m; i++) {
                            for (int j = 0; j < n; j++) {
                                if (hedge[i][j] < 1) {
                                    x = i;
                                    y = j;
                                    i = m + 1;
                                    j = n;
                                }
                            }
                        }
                        sethedge(x, y);
                    } else
                        setvedge(x, y);
                }

                if (score[0] + score[1] == m * n) {
                    result();
                } else {
                    if (player == 0) {
                        mpaint.setColor(o12);
                        bpaint.setColor(o22);
                        rnd = new Random();
                        r = rnd.nextInt(100);
                        if (r < lvl)
                            makemove();
                        else
                            rand();
                    }
                }
            }
        }

        void makeanymove() {
            if (score[0] + score[1] == m * n) {
                result();
            } else {
                x = -1;
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j < n; j++) {
                        if (hedge[i][j] < 1) {
                            x = i;
                            y = j;
                            i = m + 1;
                            j = n;
                        }
                    }
                }
                if (x < 0) {
                    for (int i = 0; i < m; i++) {
                        for (int j = 0; j <= n; j++) {
                            if (vedge[i][j] < 1) {
                                x = i;
                                y = j;
                                i = m;
                                j = n + 1;
                            }
                        }
                    }
                    setvedge(x, y);
                } else {
                    sethedge(x, y);
                }
                if (score[0] + score[1] == m * n) {
                    result();
                } else {
                    if (player == 0) {
                        mpaint.setColor(o12);
                        bpaint.setColor(o22);
                        Random rnd = new Random();
                        r = rnd.nextInt(100);
                        if (r < lvl)
                            makemove();
                        else
                            rand();
                    }
                }
            }
        }

        void checkh(int x,int y) {
            int hit=0;
            if (x>0) {
                if (box[x-1][y]==4) {
                    rx1 = xc[y] + 20;
                    ry1 = yc[x-1] + 20;
                    rx2 = xc[y + 1] - 20;
                    ry2 = yc[x] - 20;
                    Rect rec = new Rect((int) rx1, (int) ry1, (int) rx2, (int) ry2);
                    mcan.drawRect(rec,bpaint);
                    score[player]++;
                    hit=1;
                }
            }
            if (x<m) {
                if (box[x][y]==4) {
                    rx1 = xc[y] + 20;
                    ry1 = yc[x] + 20;
                    rx2 = xc[y + 1] - 20;
                    ry2 = yc[x + 1] - 20;
                    Rect rec = new Rect((int) rx1, (int) ry1, (int) rx2, (int) ry2);
                    mcan.drawRect(rec,bpaint);
                    score[player]++;
                    hit=1;
                }
            }
            if (hit>0) {
                player=1-player;
                invalidate();
                if (score[0]+score[1]==m*n) {
                    result();
                }
            }
        }

        void checkv(int x,int y) {
            int hit=0;
            if (y>0) {
                if (box[x][y-1]==4) {
                    rx1 = xc[y-1] + 20;
                    ry1 = yc[x] + 20;
                    rx2 = xc[y] - 20;
                    ry2 = yc[x + 1] - 20;
                    Rect rec = new Rect((int) rx1, (int) ry1, (int) rx2, (int) ry2);
                    mcan.drawRect(rec,bpaint);
                    score[player]++;
                    hit=1;
                }
            }
            if (y<n) {
                if (box[x][y]==4) {
                    rx1 = xc[y] + 20;
                    ry1 = yc[x] + 20;
                    rx2 = xc[y + 1] - 20;
                    ry2 = yc[x + 1] - 20;
                    Rect rec = new Rect((int) rx1, (int) ry1, (int) rx2, (int) ry2);
                    mcan.drawRect(rec,bpaint);
                    score[player]++;
                    hit=1;
                }
            }
            if (hit>0) {
                player=1-player;
                invalidate();
                if (score[0]+score[1]==m*n) {
                    result();
                }
            }
        }



    }

}


