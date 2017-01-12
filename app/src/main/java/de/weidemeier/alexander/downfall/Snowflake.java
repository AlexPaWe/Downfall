package de.weidemeier.alexander.downfall;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.DrawableWrapper;

/**
 * Created by thebige on 21.12.16.
 */

public class Snowflake {

    //velocities are given in inch/ms
    public static final double MIN_VERTICAL_VELOCITY = 1.2/1000;
    public static final double MAX_VERTICAL_VELOCITY = 1.2/1000; //not the real max velocity!

    public static final double MAX_HORIZONTAL_VELOCITY = 0/1000;

    private double vX;
    private double vY;

    private Bitmap snowflakeBmp;

    private String message = "Just a test message!";

    private boolean clicked = false;

    private int posX;
    private int posY;


    public Snowflake(int posX, int posY, double vX, double vY) {
        this.posX = posX;
        this.posY = posY;
        this.vX = vX;
        this.vY = vY;
    }


    public static Snowflake snowflakeFactory(int width, Bitmap snowflakeBmp) {
        int randPosX = (int) (Math.random() * width);
        int randDirection = (Math.random() < 0.5) ? -1 : 1;
        double randVX = (Math.random() * MAX_HORIZONTAL_VELOCITY) * randDirection;
        double randVY = (Math.random() * MAX_VERTICAL_VELOCITY) + MIN_VERTICAL_VELOCITY;

        Snowflake snowflake = new Snowflake(randPosX, 0, randVX, randVY);
        snowflake.setSnowflakeBmp(snowflakeBmp);
        return  snowflake;
    }


    public void fall(long deltaMS, int dpi) {
        posX += dpi * (vX * deltaMS);
        posY += dpi * (vY * deltaMS);
    }


    public void draw(Canvas canvas) {
        if (!clicked) {
            canvas.drawBitmap(snowflakeBmp, posX, posY, new Paint());
        } else {
            canvas.drawText(message, posX, posY, new Paint());
        }
    }


    public void setSnowflakeBmp(Bitmap snowflakeBmp) {
        this.snowflakeBmp = snowflakeBmp;
    }


    public int getPosX() {
        return posX;
    }


    public int getPosY() {
        return posY;
    }


    public void setClicked() {
        clicked = true;
    }
}
