package de.weidemeier.alexander.downfall;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by thebige on 21.12.16.
 */

public class Snowflake {

    public static final double MIN_VERTICAL_VELOCITY = 0.25;
    public static final double MAX_VERTICAL_VELOCITY = 0.4;

    public static final double MAX_HORIZONTAL_VELOCITY = 0.25;

    private int posX;
    private int posY;

    private double vX;
    private double vY;


    public Snowflake(int posX, int posY, double vX, double vY) {
        this.posX = posX;
        this.posY = posY;
        this.vX = vX;
        this.vY = vY;
    }


    public static Snowflake snowflakeFactory(int width) {
        int randPosX = (int) (Math.random() * width);
        int randDirection = (Math.random() < 0.5) ? -1 : 1;
        double randVX = (Math.random() * MAX_HORIZONTAL_VELOCITY) * randDirection;
        double randVY = (Math.random() * MAX_VERTICAL_VELOCITY) + MIN_VERTICAL_VELOCITY;

        return new Snowflake(randPosX, 0, randVX, randVY);
    }


    public void fall(long deltaMS) {
        posX += vX * (deltaMS);
        posY += vY * (deltaMS);
    }


    public int getPosX() {
        return posX;
    }


    public int getPosY() {
        return posY;
    }
}
