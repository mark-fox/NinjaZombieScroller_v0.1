package com.mark.a2417_finalproject_2dscroller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

/**
 * Class for Player objects.
 */

public class Player {

    private Rect playerRect;
    private boolean isMoving;
    private int movementSpeed;
    private int xPos;
    private int yPos;
    private int width;
    private int height;

    private AnimationManager mAnimationManager;
    private Sprite idle;
    private Sprite attack;
    private Sprite walk;
    private Context mContext;

    private int state = 0;
    private int threshold;
    private boolean attacking = false;


    // Constructor.
    public Player(Rect rectangle, Context context) {
        mContext = context;
        playerRect = rectangle;
        width = playerRect.width();
        height = playerRect.height();
        xPos = playerRect.left;
        yPos = playerRect.top;
        movementSpeed = Constants.MOVE_SPEED;
        threshold = (int) (Constants.SCREEN_WIDTH * Constants.THRESHHOLD_RATIO);


        idle = makeSprite(R.drawable.idle_sprite, 1f, 4, 3, 10);
        attack = makeSprite(R.drawable.attack_sprite_fix, 0.5f, 10, 1, 10);
        walk = makeSprite(R.drawable.run_sprite, 1f, 4, 3, 10);

        float scaler = idle.getWholeHeight() / height;
        // TODO this width may be a problem since sprites are all different.
        // TODO maybe find the widest and use that's width.
        width = (int)(idle.getWholeWidth() / scaler);

        playerRect.right = xPos + width;

        mAnimationManager = new AnimationManager(new Sprite[] {idle, attack, walk});
    }


    // Draw function.
    protected void draw(Canvas canvas) {
        mAnimationManager.draw(canvas, playerRect);
    }


    // Update function.
    protected void update(int direction, boolean attacking) {
        int currWidth = 0;
        currWidth = (int)mAnimationManager.getActiveWidth();
        if (direction > 0) {    // Is moving.
            if (direction == 1) {   // Moving left.
                xPos -= movementSpeed;
                if (xPos < 0) {
                    xPos = 0;
                } else if ((xPos + currWidth) >= threshold) {
                    xPos -= (currWidth / 2);
                }
            } else if (direction == 2) {    // Moving right.
                xPos += movementSpeed;
            }

            int rightX = xPos + currWidth;
            if (rightX >= threshold) {
                rightX = threshold + (currWidth / 2);
                xPos = rightX - currWidth;
            }
            playerRect.set(xPos, yPos, rightX, yPos + height);
        }

        if (attacking) {
            if (!mAnimationManager.isDone(1)) {
                state = 1;
                this.attacking = attacking;
            } else {
                state = 0;
                this.attacking = !attacking;
            }
        } else if (direction > 0) {
            state = 2;
        } else {
            state = 0;
        }

        mAnimationManager.playAnim(state);
        mAnimationManager.update();

    }


    // Function to use attack sprite for engaging an enemy.
    private void attack() {
        Log.d("tag", "attacking!");
    }


    private Sprite makeSprite(int id, float time, int rows, int cols, int count) {

        Bitmap sprite = BitmapFactory.decodeResource(mContext.getResources(), id);
        return new Sprite(sprite, time, rows, cols, count);
    }


    // Getters and Setters.
    public Rect getPlayerRect() {
        return playerRect;
    }
    public boolean getIsMoving() { return isMoving; }
    public void setIsMoving(boolean moving) { isMoving = moving; }
    public int getMovementSpeed() {
        return movementSpeed;
    }
    public int getState() { return state; }
    public boolean isAttacking() { return attacking; }
}
