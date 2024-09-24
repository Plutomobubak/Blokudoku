package com.example.blokudoku;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

public class GameStateManager {
    private static final String PREFS_NAME = "game_state";
    private static final String KEY_1D_ARRAY = "blocks";
    private static final String KEY_2D_ARRAY = "grid";
    private static final String KEY_SCORE = "score";
    private static final String CREDS = "credentials";
    private static final String UID = "uid";
    private static final String NAME = "name";

    public static String getUID(Context context){
        SharedPreferences preferences = context.getSharedPreferences(CREDS, Context.MODE_PRIVATE);
        return preferences.getString(UID,"");
    }
    public static String getName(Context context){
        SharedPreferences preferences = context.getSharedPreferences(CREDS, Context.MODE_PRIVATE);
        return preferences.getString(NAME,"");
    }

    public static void generateUID(Context context){
        SharedPreferences preferences = context.getSharedPreferences(CREDS, Context.MODE_PRIVATE);
        preferences.edit().putString(UID, UUID.randomUUID().toString()).apply();
    }
    public static void setName(Context context, String name){
        SharedPreferences preferences = context.getSharedPreferences(CREDS, Context.MODE_PRIVATE);
        preferences.edit().putString(NAME, name).apply();
    }

    public static void saveGameState(Context context, int[] blocks, int[][] grid, int score) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Convert 1D array to string
        StringBuilder sb1D = new StringBuilder();
        for (int i : blocks) {
            sb1D.append(i).append(",");
        }
        editor.putString(KEY_1D_ARRAY, sb1D.toString());

        // Convert 2D array to string
        StringBuilder sb2D = new StringBuilder();
        for (int[] row : grid) {
            for (int value : row) {
                sb2D.append(value).append(",");
            }
            sb2D.append(";");
        }
        editor.putString(KEY_2D_ARRAY, sb2D.toString());
        editor.putInt(KEY_SCORE,score);

        editor.apply();
    }
    public static int[] loadBlocks(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String oneDString = preferences.getString(KEY_1D_ARRAY, "");
        if(oneDString.isEmpty())return new int[0];
        String[] items = oneDString.split(",");
        int[] blocks = new int[items.length];
        for (int i = 0; i < items.length; i++) {
            blocks[i] = Integer.parseInt(items[i]);
        }
        return blocks;
    }

    public static int[][] loadGrid(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String twoDString = preferences.getString(KEY_2D_ARRAY, "");
        if(twoDString.isEmpty())return new int[0][0];
        String[] rows = twoDString.split(";");
        int[][] grid = new int[rows.length][];

        for (int i = 0; i < rows.length; i++) {
            String[] items = rows[i].split(",");
            grid[i] = new int[items.length];
            for (int j = 0; j < items.length; j++) {
                grid[i][j] = Integer.parseInt(items[j]);
            }
        }
        return grid;
    }
    public static int loadScore(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return preferences.getInt(KEY_SCORE,0);
    }
}
