package com.mark.a2417_finalproject_2dscroller;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Class outline for a generic Enemy instance.
 */

public class Enemy {

    private Rect rectangle;
    private int width;
    private int height;
    private boolean active = true;
    private boolean readyForDeath = false;
    private boolean hitPlayer = false;

    private int xPos;
    private int yPos;

    private int direction;
    private int xSpeed;

    private AnimationManager mAnimationManager;

    private int state = 0;
    private int defaultSpeed;



    // Constructor.
    public Enemy(int side, Sprite[] sprites) {
        // Sets up initial values.
        direction = side;
        height = Constants.PLAYER_HEIGHT;
        float scaler = sprites[0].getWholeHeight() / height;
        width = (int)(sprites[0].getWholeWidth() / scaler);


        // Sets the object's speed based on the side of screen it starts.
        if (side < 2) { // Start on Right and move left.
            xPos = (2 * width) + Constants.SCREEN_WIDTH;
            xSpeed = -100; // TODO Constant.
            defaultSpeed = -100;
        } else { // Start on Left and move right.
            xPos = 2 * -width;
            xSpeed = 100; // TODO Constant.
            defaultSpeed = 100;
        }
        // Y coordinate will be the same as the player's for now.
        yPos = Constants.PLAYER_START_Y;

        // Creates a rectangle object for comparisons.
        rectangle = new Rect(xPos, yPos, xPos + width, yPos + height);

        // Instantiates the Animation Manager for animations.
        mAnimationManager = new AnimationManager(sprites);
    }



    // Draw function for telling manager to draw animation.
    public void draw(Canvas canvas) {
        mAnimationManager.draw(canvas, rectangle);
    }



    // Update function for increasing objects location and determines its state.
    public void update() {
        xPos += xSpeed;
        // Checks if objects moving left have gone off the screen.
        if (direction < 2 && (xPos + width) < 0) {
            active = false;
            readyForDeath = true;
        // Checks if objects moving right have gone off the screen.
        } else if (direction == 2 && xPos > Constants.SCREEN_WIDTH) {
            active = false;
            readyForDeath = true;
        }

        // Recreates rectangle object.
        rectangle.set(xPos, yPos, xPos + width, yPos + height);

        // Looks at flags and sets state accordingly.
        if (readyForDeath) {    // Player hit enemy or gone off screen.
            state = 2;
            xSpeed = 0;
        } else if (hitPlayer) { // Enemy collided with player.
            state = 1;
            xSpeed = 0;
        } else {    // Moving.
            state = 0;
            xSpeed = defaultSpeed;
        }

        // Instructs animation manager to play animation based on state.
        mAnimationManager.playAnim(state);
        mAnimationManager.update();
        // Checks if the current animation has gone through all frames yet.
//        if (mAnimationManager.isDone(state)) {
//            state = 0;
//        }
    }





    // Function to compare player's location with enemy's location.
    protected boolean checkAttackRange(Rect player, boolean attacking) {
        // Calculates a little buffer room around player for the player to have
        // striking room with sword.
        float buffer = (player.right - player.left) / 2;

        // Checks if rectangles intersect.
        if (Rect.intersects(rectangle, new Rect((int)(player.left - buffer), player.top, (int)(player.right + buffer), player.bottom))) {
            if (attacking) {
                readyForDeath = true;
                return true;
            }
        }

//        if (xPos <= player.right + buffer && xPos > player.left) {
//            // Moving left and in range.
//            if (attacking) {
//                readyForDeath = true;
//                return true;
//            }
//        } else if (xPos + width >= player.left + buffer && xPos < player.left) {
//            // Moving right and in range.
//            if (attacking) {
//                readyForDeath = true;
//                return true;
//            }
//        }
        return false;
    }



    // Function that compares player's location with enemy.
    protected boolean checkCollision(Rect player) {
        if (Rect.intersects(rectangle, player) && !active) {
            if (!readyForDeath || !hitPlayer) {
                return true;
            }
        }
        return false;
    }


    // Getters and Setters.
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public Rect getRectangle() { return rectangle; }
    public boolean isDone() { return mAnimationManager.isDone(state); }
    public boolean isReadyForDeath() {
        return readyForDeath;
    }
    public boolean isHitPlayer() {
        return hitPlayer;
    }
}
