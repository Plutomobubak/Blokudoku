package com.example.blokudoku;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class GridVerify {
    public static boolean verifyPlacable(int[] index, int[][] grid, int[][] block){
        // check for index out of range
        // if over bounds Rows
        if(index[1]+block[0].length-1>grid[0].length-1) return false;
        // if over bounds Columns
        if(index[0]+block.length-1>grid.length-1) return false;
        //check overlap
        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block[i].length; j++) {
                if (block[i][j] == 1) {
                    if(grid[index[0]+i][index[1]+j]==1) return false;
                }
            }
        }
        return true;
    }
    public static ArrayList<int[]> verifyConnected(int[][] grid){
        ArrayList<int[]> results = new ArrayList<int[]>();
        //check for connected rows, type 0
        for (int i = 0; i < grid.length; i++) {
            int sum=0;
            for (int j = 0; j < grid[i].length; j++) {
                sum+=grid[i][j];
            }
            Log.d("row","R"+i+" "+sum);
            if (sum>8) results.add(new int[]{
                    0,i
            });
        }
        //check for columns
        for (int i = 0; i < grid.length; i++) {
            int sum=0;
            StringBuilder column = new StringBuilder();
            for (int j = 0; j < grid[i].length; j++) {
                sum+=grid[j][i];
                column.append(grid[j][i]);
            }
            Log.d("col"+i,column.toString());
            Log.d("col","C"+i+" "+sum);
            if (sum>8) results.add(new int[]{
                    1,i
            });
        }
        //check for squares
//        for (int i = 0; i < grid.length; i++) {
//            int sum=0;
//            for (int j = 0; j < grid[i].length; j++) {
//                int x = (i%3)*3 + j%3;
//                int y = (i-i%3)*3 + (j-j%3);
//                sum+=grid[i][j];
//            }
//            if (sum==8) results.add(new int[]{
//                    2,i
//            });
//        }
        return results;
    }
    public static int[][] deleteConnected(ArrayList<int[]> connected, int[][] grid){
        for(int[] res:connected){
            switch(res[0]) {
                case 0:
                    Arrays.fill(grid[res[1]], 0);
                    break;
                case 1:
                    for (int i = 0; i < grid.length; i++) {
                        grid[i][res[1]] = 0;
                    }
                    break;
                case 2:
                    for (int i = 0; i < grid[res[1]].length; i++) {
                        int x= (res[1]%3)*3 + i%3;
                        int y= (res[1]-res[1]%3)*3 + (i-i%3);
                        grid[x][y] = 0;
                    }
                    break;
            }
        }
        return grid;
    }
}
