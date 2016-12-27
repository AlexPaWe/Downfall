package de.weidemeier.alexander.downfall;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by thebige on 20.12.16.
 */

public class SnowDisplay extends SurfaceView implements SurfaceHolder.Callback {

    private SnowThread thread;


    public SnowDisplay(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        // create thread only; it's started in surfaceCreated()
    }


    public void setThread(SnowThread snowThread) {
        this.thread = snowThread;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }


    /* Callback invoked when the surface dimensions change. */
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }


    /*
     * Callback invoked when the Surface has been destroyed and must no longer
     * be touched. WARNING: after this method returns, the Surface/Canvas must
     * never be touched again!
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        // we have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode
        /*boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }*/
    }
}
