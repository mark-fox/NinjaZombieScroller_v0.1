package com.mark.a2417_finalproject_2dscroller;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * This class is used to organize the playing and stopping of each animation.
 */

public class AnimationManager {

    private Sprite[] animations;
    private int animIndex = 0;


    // Constructor.
    public AnimationManager(Sprite[] sprites) {
        animations = sprites;
    }



    // Function for stopping and starting animations.
    public void playAnim(int index) {
        // Loops through the array of different animations.
        for (int i = 0; i < animations.length; i++) {
            // Checks if correct animation is found.
            if (i == index) {
                // Checks whether the indicated animation is still playing.
                // The animation is started if it is not already.
                if (!animations[index].isPlaying()) {
                    animations[i].play();
                }
            } else {
                // All other animations are stopped.
                animations[i].stop();
            }
        }
        animIndex = index;
    }


    // Draw function that instructs the correct animation to be drawn.
    public void draw(Canvas canvas, Rect rect) {
        if (animations[animIndex].isPlaying()) {
            animations[animIndex].draw(canvas, rect);
        }
    }


    // Update function that instructs the correct animation to be updated.
    public void update() {
        if (animations[animIndex].isPlaying()) {
            animations[animIndex].update();
        }
    }



    // Getter function that retrieves the active animation's sprite width.
    public float getActiveWidth() {
        for (int i = 0; i < animations.length; i++) {
            if (animations[i].isPlaying()) {
                return animations[i].getWidth();
            }
        }
        return 0;
    }

    // Getter function that retrieves the animation's "done" status.
    public boolean isDone(int index) { return animations[index].isDone(); }
}
