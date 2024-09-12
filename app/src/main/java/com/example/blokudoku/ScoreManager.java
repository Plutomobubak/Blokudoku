package com.example.blokudoku;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;


public class ScoreManager {
    private static TextView scoreView;
    private static TextView popup;
    private static AnimatorSet anim;
    private static int streak;
    public static int score;
    public static void init(TextView scoreView, TextView popup,int score){
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

        // Slide down
        ObjectAnimator slideDown = ObjectAnimator.ofFloat(popup, "translationY", -100, 0);
        slideDown.setDuration(500);

        // Fade out
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(popup, "alpha", 1.0f, 0.0f);
        fadeOut.setDuration(500);
        fadeOut.setStartDelay(500);

        // Combine the animations
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(slideDown, fadeOut);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        // Start the animation
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                popup.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        anim = animatorSet;
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
//        ConstraintSet constraintSet = new ConstraintSet();
//        constraintSet.clone(main);
//
//        // Change margins for the target view
//        constraintSet.setMargin(popup.getId(), ConstraintSet.TOP, 100*index[0]); // Top margin = 50px
//        constraintSet.setMargin(popup.getId(), ConstraintSet.START, 100+100*index[1]); // Start margin = 100px
//
//        // Apply the updated constraints to the layout
//        constraintSet.applyTo(main);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) popup.getLayoutParams();
        params.topMargin = 100 * index[0];
        params.leftMargin = 100 + 100 * index[1];
        popup.setLayoutParams(params);
        popup.requestLayout();
        popup.setText(String.format("+%d", scoreChange));

        popup.setVisibility(View.VISIBLE);

        anim.start();
        // Score display
        scoreView.setText(String.format("Score: %d", ScoreManager.score));
    }
}
