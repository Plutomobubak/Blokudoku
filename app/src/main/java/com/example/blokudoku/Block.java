package com.example.blokudoku;

import android.util.Log;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class Block {
    public int[][] matrix;
    public ArrayList<ConstraintLayout> views;
    public Block(int[][] matrix){
        this.matrix = matrix;
        this.views = new ArrayList<ConstraintLayout>();
    }
    public boolean checkPlaceable(int[][] grid){
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (GridVerify.verifyPlacable(new int[]{i,j},grid,matrix)) {
                    Log.d("Placeable","Index "+ i + " " + j);
                    return true;
                }
            }
        }
        return false;
    }
}
