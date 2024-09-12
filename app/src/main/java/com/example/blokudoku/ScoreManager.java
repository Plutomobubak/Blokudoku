package com.example.blokudoku;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class ScoreManager {
    private static TextView scoreView;
    private static TextView popup;
    private static ConstraintLayout main;
    private static int streak;
    public static int score;
    public static void init(int [][] grid, TextView scoreView, TextView popup, ConstraintLayout main,int score){
        ScoreManager.scoreView = scoreView;
        ScoreManager.score=score;
        ScoreManager.streak=0;
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(16f);
        drawable.setColor(Color.WHITE);
        popup.setBackground(drawable);
        //popup.setVisibility(View.GONE);
        popup.bringToFront();
        popup.setVisibility(View.GONE);
        ScoreManager.popup=popup;
        ScoreManager.main = main;
    }
    public static void updateScore(int deleted, int combo, int added, int[] index){
        // Update score
        int scoreChange = added;
        if(combo < 1){
            ScoreManager.streak = 0;
        }
        else{
            scoreChange+= (int) (2 * deleted * (combo + (ScoreManager.streak/3f)));
            ScoreManager.streak+=1;
        }
        ScoreManager.score+=scoreChange;
        Log.d("Score update","Del " + deleted + " Com " +combo+ " Add " + added + " Streak " + streak);
        // Score popup
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(main);

        // Change margins for the target view
        constraintSet.setMargin(popup.getId(), ConstraintSet.TOP, 100*index[0]); // Top margin = 50px
        constraintSet.setMargin(popup.getId(), ConstraintSet.START, 100+100*index[1]); // Start margin = 100px

        // Apply the updated constraints to the layout
        constraintSet.applyTo(main);

        popup.setText(String.format("+%d", scoreChange));

        Animation slideDown = new TranslateAnimation(0, 0, -100, 0);
        slideDown.setDuration(500);

        // Set a fade-out animation after slide-down
        Animation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(500);
        fadeOut.setStartOffset(500); // Delay before fading out

        // Combine the animations
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(slideDown);
        animationSet.addAnimation(fadeOut);

        // Show the TextView
        popup.setVisibility(View.VISIBLE);

        // Start the animation
        popup.startAnimation(animationSet);

        // Hide the TextView after the animation ends
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                popup.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        // Score display
        scoreView.setText(String.format("Score: %d", ScoreManager.score));
    }
}
