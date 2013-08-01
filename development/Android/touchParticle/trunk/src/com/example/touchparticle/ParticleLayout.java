package com.example.touchparticle;

import java.util.ArrayList;
import com.example.touchparticle.ParticleLayout.Particles.UnitParticle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;

public class ParticleLayout extends RelativeLayout implements OnTouchListener {
    private static String TAG = ParticleLayout.class.getSimpleName();

    private ArrayList<Particles> mCache = new ArrayList<ParticleLayout.Particles>();
    private Bitmap mBitmap = null;

    public ParticleLayout(Context context) {
        super(context);
        initialize();
    }

    public ParticleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public ParticleLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d(TAG, "onTouch" + event.getAction());
        if ((MotionEvent.ACTION_DOWN == event.getActionMasked())
                || (MotionEvent.ACTION_MOVE == event.getActionMasked())) {
            int index = mCache.indexOf(null);
            if (-1 != index) {
                mCache.set(index, (new Particles((int) event.getX(), (int) event.getY())));
            } else {
                mCache.add(new Particles((int) event.getX(), (int) event.getY()));
            }
            this.invalidate();
        }
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw");

        canvas.drawARGB(255, 0, 0, 0);
        if (null != mCache) { 
            Bitmap bitmap = mBitmap;
            int size = mCache.size();
            Paint paint = new Paint();
            for (int i = 0; i < size; i++) {
                Particles particles = mCache.get(i);
                if (null != particles) {
                    ArrayList<UnitParticle> units = particles.getParticles();
                    int s = units.size();
                    for (int j = 0; j < s; j++) {
                        UnitParticle unit = units.get(j);
                        if (null != unit) {
                            unit.update();
                            if (0 >= unit.alpha) {
                                units.clear();
                                units = null;
                                break;
                            }
                            paint.setAlpha(unit.alpha);

                            int width = (int) (bitmap.getWidth() * unit.scale);
                            int height = (int) (bitmap.getHeight() * unit.scale);
                            int x = unit.x - (width / 2);
                            int y = unit.y - (height / 2);
                            canvas.drawBitmap(bitmap, null,
                                    new Rect(x, y, x + width, y + height), paint);
                        }
                    }
                }
            invalidate();
            }
//            bitmap.recycle();
//            bitmap = null;
        }
    }

    private void initialize() {
        setOnTouchListener(this);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher, null);
    }

    class Particles {
        ArrayList<UnitParticle> mList = null;
        int size = 20;

        public Particles(int x, int y) {
            createParticleArray(x, y);
        }

        private void createParticleArray(int x, int y) {
            mList = new ArrayList<UnitParticle>(size);
            for (int i = 0; i < size; i++) {
                mList.add(new UnitParticle(x, y));
            }
        }

        public ArrayList<UnitParticle> getParticles() {
            return mList;
        }

        class UnitParticle {
            int x = 0;
            int y = 0;
            int alpha = 0;
            double scale = 0.0;

            public UnitParticle(int x, int y) {
                this.x = x - ((int) (Math.random() * 100) - 50);
                this.y = y - ((int) (Math.random() * 100) - 50);
                alpha = 255;
                scale = (Math.random() + 0.5);
            }

            void update() {
                x += (int) (Math.random() * 10 - 5);
                y += (int) (Math.random() * 10 - 5);
                alpha -= 5;
            }
        }
    }
}
