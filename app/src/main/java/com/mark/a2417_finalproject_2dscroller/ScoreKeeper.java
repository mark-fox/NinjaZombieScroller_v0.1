package com.mark.a2417_finalproject_2dscroller;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Class that will keep record of the scoring in game and draw those values to screen.
 */

public class ScoreKeeper {

    private int playerPoints = 0;
    private int enemyPoints = 0;
    private int increaseAmount;

    private int playerX;
    private int playerY;
    private int enemyX;
    private int enemyY;

    private Paint painter;

    // Constructor.
    public ScoreKeeper() {
        // Calculates initial values.
        increaseAmount = Constants.POINTS;
        playerX = (Constants.SCREEN_WIDTH / Constants.SCORE_WIDTH_RATIO);
        playerY = (Constants.SCREEN_HEIGHT / Constants.SCORE_HEIGHT_RATIO);

        enemyX = playerX;
        enemyY = playerY * 2;

        // Creates paint object to be reused.
        painter = new Paint();
        painter.setColor(Color.WHITE);
        painter.setTextSize(Constants.SCREEN_HEIGHT / Constants.FONT_RATIO);
    }


    // Draw function for displaying values.
    public void draw(Canvas canvas) {
        canvas.drawText("Player: " + playerPoints, playerX, playerY, painter);
        canvas.drawText("Enemy: " + enemyPoints, enemyX, enemyY, painter);
    }


    // Functions for increasing individual values.
    public void addForEnemy() {
        enemyPoints += increaseAmount;
    }

    public void addForPlayer() {
        playerPoints += increaseAmount;
    }
}
