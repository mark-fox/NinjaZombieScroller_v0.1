package com.mark.a2417_finalproject_2dscroller;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Class for an individual animation and its individual sprite images.
 */

public class Sprite {

    private int frameIndex;
    private double frameTime;
    private long lastFrame;
    private boolean isPlaying = false;

    private Bitmap spriteSheet;
    private int rowIndex;
    private int colIndex;
    private float picWidth;
    private float picHeight;

    private int rows;
    private int cols;
    private int count;

    private boolean done;



    // Constructor.
    public Sprite(Bitmap sprite, double animTime, int rows, int cols, int count) {
        // Sets up initial values.
        spriteSheet = sprite;
        this.rows = rows;
        this.cols = cols;
        this.count = count;
        frameIndex = 0;
        rowIndex = 0;
        colIndex = 0;

        // Height and width of image on screen.
        picHeight = Constants.PLAYER_HEIGHT;
        double scaler = sprite.getHeight() / picHeight;
        picWidth = (int)(sprite.getWidth() / scaler);

        // Scales the spritesheet using a calculated ratio value.
        spriteSheet = Bitmap.createScaledBitmap(spriteSheet, (int)(picWidth * this.cols),
                (int)(picHeight * this.rows), true);


        // Calculates how long each frame will be.
        frameTime = animTime / this.count;
        lastFrame = System.currentTimeMillis();
    }



    // Draw function for displaying animation object.
    public void draw(Canvas canvas, Rect destination) {
        if (!isPlaying) {
            return;
        }
// TODO maybe try implementing this for flipping image
//        Matrix m = new Matrix();
//        m.setScale(-1, 1);
//        m.postTranslate(Constants.SCREEN_WIDTH, 0);
//        canvas.drawBitmap(spriteSheet, m, null);
//        https://stackoverflow.com/questions/7925278/drawing-mirrored-bitmaps-in-android


        // Creates rectangle object for comparisons.
        Rect src = new Rect((int)(colIndex * picWidth),
                (int)(rowIndex * picHeight),
                (int)(colIndex * picWidth + picWidth),
                (int)(rowIndex * picHeight + picHeight));

        // Draws image to screen.
        canvas.drawBitmap(spriteSheet, src, destination, null);
    }



    // Update function for navigating around the spritesheet to display the correct image.
    public void update() {
        if (!isPlaying) {
            return;
        }

        if (System.currentTimeMillis() - lastFrame > frameTime * 1000) {
            frameIndex++;
            if (frameIndex >= count) {
                frameIndex = 0;
                colIndex = 0;
                rowIndex = 0;
                done = true;
            } else {
                colIndex++;
                if (colIndex >= cols) {
                    colIndex = 0;
                    rowIndex++;
                    if (rowIndex >= rows) {
                        rowIndex = 0;
                    }
                }
            }
            lastFrame = System.currentTimeMillis();
        }
    }



    // Function for initializing animation loop and resets values.
    public void play() {
        isPlaying = true;
        frameIndex = 0;
        rowIndex = 0;
        colIndex = 0;
        lastFrame = System.currentTimeMillis();
        done = false;
    }


    // Function for stopping animation.
    public void stop() {
        isPlaying = false;
    }



    // Getters.
    public boolean isPlaying() { return isPlaying; }
    public float getWholeWidth() { return picWidth * cols; }
    public float getWholeHeight() { return picHeight * rows; }
    public float getWidth() { return picWidth; }
    public boolean isDone() { return done; }
}
