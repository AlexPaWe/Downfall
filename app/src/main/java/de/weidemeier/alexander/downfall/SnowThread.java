package de.weidemeier.alexander.downfall;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.view.SurfaceHolder;
import android.widget.TextView;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by thebige on 21.12.16.
 */

public class SnowThread extends Thread {

    public static final int MAX_NUMBER_OF_SNOWFLAKES = 100;

    private Object pauseLock;

    private SurfaceHolder surfaceHolder;

    private Context context;

    private Bitmap snowflakeBmp;

    private int numberOfSnowflakes;

    private Stack<Snowflake> snowflakes;

    private long lastTime;

    /** Indicate whether the surface has been created & is ready to draw */
    private boolean paused = false;

    private boolean started;

    private int dpi;


    public SnowThread(SurfaceHolder surfaceHolder, Context context) {
        pauseLock = new Object();

        this.surfaceHolder = surfaceHolder;

        this.context = context;

        snowflakeBmp = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.expsnfl);

        numberOfSnowflakes = 0;

        snowflakes = new Stack<Snowflake>();

        lastTime = System.currentTimeMillis();

        started = false;

        dpi = context.getResources().getDisplayMetrics().densityDpi;
    }


    @Override
    public void run() {
        while (true) {

            //compute time difference from last approach
            long deltaMS = System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();

            if (numberOfSnowflakes < MAX_NUMBER_OF_SNOWFLAKES && Math.random() < 0.75) {
                Snowflake snowflake =
                        Snowflake.snowflakeFactory(surfaceHolder.getSurfaceFrame().width(), snowflakeBmp);
                snowflakes.add(snowflake);
                numberOfSnowflakes++;
            }

            Iterator<Snowflake> iterator = snowflakes.iterator();
            while (iterator.hasNext()) {
                iterator.next().fall(deltaMS, dpi);
            }

            Canvas c = null;
            c = surfaceHolder.lockCanvas();
            doDraw(c);
            if (c != null) surfaceHolder.unlockCanvasAndPost(c);

            //pauses the thread if necessary
            synchronized (pauseLock) {
                while (paused) {
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void start() {
        super.start();
        started = true;
    }


    private void doDraw(Canvas canvas) {

        //Draw the background color. This is like clearing the screen, too.
        canvas.drawRGB(255, 255, 255);

        Iterator<Snowflake> iterator = snowflakes.iterator();
        while (iterator.hasNext()) {

            Snowflake snowflake = iterator.next();
            if (isOnCanvas(snowflake)) {
                snowflake.draw(canvas);
            } else {
                iterator.remove();
                numberOfSnowflakes--;
            }
        }
    }


    /**
     * Checks if a snowflake would be visible on the canvas.
     * @param snowflake to be checked for visibility
     * @return true if snowflake would be visible on the canvas.
     */
    private boolean isOnCanvas(Snowflake snowflake) {
        return (snowflake.getPosX() < surfaceHolder.getSurfaceFrame().width() &&
            snowflake.getPosY() < surfaceHolder.getSurfaceFrame().height());
    }


    public void onPause() {
        synchronized (pauseLock) {
            paused = true;
        }
    }


    public void onResume() {

        //sets the lastTime variable to avoid that the resume looks like a reset.
        lastTime += (System.currentTimeMillis() - lastTime);

        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }


    public boolean hitsSnowflake(float x, float y) throws ConcurrentModificationException {
        for (Snowflake snowflake : snowflakes) {
            if ((snowflake.getPosX() - 6 < x && x < snowflake.getPosX() + 6) &&
                    (snowflake.getPosX() - 6 < x && x < snowflake.getPosX() + 6)) {
                snowflake.setClicked();
                return true;
            }
        }
        return false;
    }


    public void kill() {
        started = false;
    }


    public boolean isStarted() {
        return started;
    }


    public boolean isPaused() {
        return paused;
    }
}
