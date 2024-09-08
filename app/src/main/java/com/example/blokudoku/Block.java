package com.example.blokudoku;

import android.view.View;

import java.util.ArrayList;

public class Block {
    public int[][] matrix;
    public ArrayList<View> views;
    public Block(int[][] matrix){
        this.matrix = matrix;
        this.views = new ArrayList<View>();
    }
}
