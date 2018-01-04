package com.mark.a2417_finalproject_2dscroller;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.erz.joysticklibrary.JoyStick;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Make app fullscreen.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Get rid of title bar on top.
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setConstants();

        RelativeLayout relativeLayout = new RelativeLayout(this);
        JoyStick joyStick = setupJoystick();
        GameManager gameManager = new GameManager(this);


        relativeLayout.addView(gameManager);
        relativeLayout.addView(joyStick);

        setContentView(relativeLayout);
    }

    private void setConstants() {
        // Set constant values for screen size.
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;

        // Set player's size based on screen size.
        // Currently, width is overridden when determining sprite width.
        Constants.PLAYER_WIDTH = (dm.widthPixels / Constants.PLAYER_SIZE_RATIO);
        Constants.PLAYER_HEIGHT = (dm.heightPixels / Constants.PLAYER_SIZE_RATIO);

        // Set player's start location.
        Constants.PLAYER_START_X = (dm.widthPixels / Constants.PLAYER_START_X_RATIO);
        Constants.PLAYER_START_Y = (int)(dm.heightPixels - (Constants.PLAYER_HEIGHT * Constants.PLAYER_START_Y_RATIO));

        // Set joystick's location and size.
        Constants.STICK_X = (dm.widthPixels / Constants.STICK_X_RATIO);
        Constants.STICK_Y = (int)(dm.heightPixels * Constants.STICK_Y_RATIO);
        Constants.STICK_SIZE = (dm.widthPixels / Constants.STICK_SIZE_RATIO);
    }

    // Unable to modify joystick's properties from within SurfaceView.
    private JoyStick setupJoystick() {

        View view = new JoyStick(this);
        view.setLayoutParams(new RelativeLayout.LayoutParams(Constants.STICK_SIZE, Constants.STICK_SIZE));

        JoyStick joyStick = (JoyStick) view;

        joyStick.setPadColor(Color.argb(80, 0, 0, 0));
        joyStick.setButtonColor(Color.argb(80, 41, 163, 41));
        joyStick.setX(Constants.STICK_X);
        joyStick.setY(Constants.STICK_Y);

        return joyStick;
    }
}









// References:
    // game setup and processes (whole series) - https://www.youtube.com/watch?v=OojQitoAEXs
    // joystick - https://android-arsenal.com/details/1/2712#!package
                // https://github.com/erz05/JoyStick?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=2712
                // https://github.com/minneapolis-edu/hello_joystick
    // drawing background image - https://stackoverflow.com/questions/5176441/drawable-image-on-a-canvas
    // side scrolling background - https://www.youtube.com/watch?v=GPzTSpZwFoU&index=3&list=PLWweaDaGRHjvQlpLV0yZDmRKVBdy6rSlg
    // sprite animation - https://www.youtube.com/watch?v=WxkuDwJcq6M
    // enemy class setup - http://www.kilobolt.com/day-6-adding-enemies/unit-2-day-6-adding-enemies
    // finding parent layout - https://stackoverflow.com/questions/17879743/get-parents-view-from-a-layout
    // setting size of joystick - https://stackoverflow.com/questions/5042197/android-set-height-and-width-of-custom-view-programmatically
    // combining sprites into sheet - css.spritegen.com
    // making circle button programmatically - https://stackoverflow.com/questions/18391830/how-to-programmatically-round-corners-and-set-random-background-colors
    // might be helping with capturing touch events  - https://stackoverflow.com/questions/28979683/android-surfaceview-not-responding-to-touch-events






    // Game Art:
        // city night background - https://opengameart.org/content/city-background-repetitive-3
        // ground - https://opengameart.org/content/blocks
        // ninja character - https://opengameart.org/content/ninja-adventure-free-sprite

