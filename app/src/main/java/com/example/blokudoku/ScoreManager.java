package com.example.blokudoku;

import android.util.Log;
import android.widget.TextView;

import java.util.Arrays;



public class ScoreManager {
    private static TextView scoreView;
    private static int streak;
    private static int score;
    public static void init(int [][] grid, TextView scoreView){
        ScoreManager.scoreView = scoreView;
        ScoreManager.score=0;
        ScoreManager.streak=0;
    }
    public static void updateScore(int deleted, int combo, int added){
        ScoreManager.score += added;
        if(combo < 1){
            ScoreManager.streak = 0;
        }
        else{
            ScoreManager.score+= deleted * (combo + (ScoreManager.streak/3));
            ScoreManager.streak+=1;
        }
        Log.d("Score update","Del " + deleted + " Com " +combo+ " Add " + added);
        scoreView.setText(String.format("Score: %d", ScoreManager.score));
    }
}
