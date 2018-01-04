package com.mark.a2417_finalproject_2dscroller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Class for drawing the background scene and updating the scrolling.
 */

public class Background {

    private Drawable cityBackground;
    private Context mContext;
    private int x;
    private int y;
    private int width;
    private int height;
    private Bitmap cityBackground2;
    private Drawable ground;
    private int groundHeight;
    private int groundY;
    private int groundWidth;
    private boolean shortGround = false;
    private int groundX;

    private int dx;
    private int startX = 0;


    // Constructor.
    public Background(Context context) {
        mContext = context;
        // Retrieves the indicated background image.
        cityBackground =  mContext.getDrawable(R.drawable.city_background_night);
        // Second image captured to originally follow the first, but should now
        // replace the original instance.
        // TODO replace bitmap with drawable.
        cityBackground2 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.city_background_night);

        // Calculates a scaling ratio of the image height and the screen's height.
        // This is currently not being used anymore.
        try {
            Constants.SCREEN_SCALER = (Constants.SCREEN_HEIGHT / cityBackground2.getHeight());
        }
        catch (NullPointerException e) {
            Log.e("tag", "error getting background height");
        }


        // Sets initial values.
        this.x = 0;
        this.y = 0;
        this.groundX = 0;
        // TODO make these constants and set in MainActivity
        this.width = Constants.SCREEN_WIDTH;
        this.height = 4 * (Constants.SCREEN_HEIGHT / 5);
        // Static speed for the background to move each frame.
        this.dx = Constants.MOVE_SPEED;


        // Retrieves ground image and compares its width against the screen.
        ground = mContext.getDrawable(R.drawable.ground);
        if (ground.getIntrinsicWidth() < Constants.SCREEN_WIDTH) {
            groundWidth = ground.getIntrinsicWidth();
            shortGround = true;
        }
        // Calculates the y and height values.
        groundHeight = Constants.SCREEN_HEIGHT - height;
        groundY = Constants.SCREEN_HEIGHT - groundHeight;
    }




    // Draw function to draw the background images to the canvas.
    public void draw(Canvas canvas) {
        // Covers the canvas for a baseline.
        canvas.drawColor(Color.BLACK);

        // Draws the background image.
        cityBackground.setBounds(x, y, width*2, height);
        cityBackground.draw(canvas);
        // Checks if the edge of the image has reached the active screen.
        // Adds a second image after it if so.
        if (width *2 < Constants.SCREEN_WIDTH) {
            cityBackground.setBounds((width *2) + x, y, (width * 4) + x, height);
            cityBackground.draw(canvas);
        }


        // Draws the ground image.
        ground.setBounds(groundX, groundY, groundWidth, Constants.SCREEN_HEIGHT);
        ground.draw(canvas);
        // Checks whether the ground image is smaller than the screen.
        if (shortGround) {
            int counter = 1;
            int rightX = 0;
            // Adds more copies of image side by side until the whole screen is filled.
            while (rightX < Constants.SCREEN_WIDTH) {
                rightX = groundX + groundWidth + (groundWidth * counter);
                ground.setBounds(groundX + (groundWidth * counter), groundY, rightX, Constants.SCREEN_HEIGHT);
                ground.draw(canvas);
                counter++;
            }
        }
    }



    // Update function for increasing the x value of background.
    public void update(boolean movingTime) {
        if (movingTime) {
            // Moves the background's x coordinate.
            x -= dx;
            if (x < -(width * 2)) {
                x = startX;
            }
        }
    }




    // Getters that are currently not being used.
    public void setScrollSpeed(int dx) {
        this.dx = dx;
    }
    public int getGroundY() { return groundY; }
}
