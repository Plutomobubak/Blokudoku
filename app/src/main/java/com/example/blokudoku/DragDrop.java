package com.example.blokudoku;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

public class DragDrop {

    // This method will setup drag-and-drop logic for given views
    public static void setupDragAndDrop(Block[] blocks, View dropTarget, Context context) {

        int [][] originalColors = new int[9][9];
        // Set drag listener for the drop target
        dropTarget.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        // Save the original color before starting the drag
                        if (v instanceof ImageView){
                            for (int i = 0; i < Grid.imageViews.length-2; i++) {
                                for (int j = 0; j < Grid.imageViews[i].length; j++) {
                                    ColorDrawable col = (ColorDrawable)Grid.imageViews[i][j].getBackground();
                                    originalColors[i][j] = col.getColor();
                                }
                            }
                        }
                        return true;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        // When drag enters, change the background color
                        if (v instanceof ImageView) {
                            View dragged = (View) event.getLocalState(); // Get the dragged view
                            int[] index = getImageViewIndex(((ImageView) v));
                            index[0] -= 2;

                            int[][] matrix = new int[0][0];
                            for (Block block:blocks) {
                                for(View view:block.views)
                                    if (view == dragged) {
                                        matrix = block.matrix;
                                        break;
                                    }
                            }
                            if(matrix[0].length >3)index[1]-=2;
                            else if (matrix[0].length >1)index[1] -= 1;
                            if (index[0]<0 || index[1]<0)return false;
                            if(GridVerify.verifyPlacable(index,Grid.grid,matrix)){
                                for (int i = 0; i < matrix.length; i++) {
                                    for (int j = 0; j < matrix[i].length; j++) {
                                        if(matrix[i][j]==1){
                                                Grid.imageViews[index[0] + i][index[1] + j].setBackgroundColor(Color.parseColor("#999999"));
                                        }
                                    }
                                }
                            }
                        }
                        return true;

                    case DragEvent.ACTION_DRAG_EXITED:
                        // Reset the background color to the original color when drag exits
                        for (int i = 0; i < Grid.imageViews.length-2; i++) {
                            for (int j = 0; j < Grid.imageViews[i].length; j++) {
                                Grid.imageViews[i][j].setBackgroundColor(originalColors[i][j]);
                            }
                        }
                        return true;

                    case DragEvent.ACTION_DROP:
                        if (v instanceof ImageView) {
                            View dragged = (View) event.getLocalState(); // Get the dragged view
                            int[] index = getImageViewIndex(((ImageView) v));
                            index[0] -= 2;
                            int[][] matrix = new int[0][0];
                            for (Block block:blocks) {
                                for(View view:block.views)
                                    if (view == dragged) {
                                        matrix = block.matrix;
                                        break;
                                    }
                            }
                            if(matrix[0].length >3)index[1]-=2;
                            if (matrix[0].length >1)index[1] -= 1;
                            if (index[0]<0 || index[1]<0)return false;
                            if(GridVerify.verifyPlacable(index,Grid.grid,matrix)){
                                for (int i = 0; i < matrix.length; i++) {
                                    for (int j = 0; j < matrix[i].length; j++) {
                                        if (matrix[i][j] == 1) {
                                                Grid.imageViews[index[0] + i][index[1] + j].setBackgroundColor(Color.parseColor("#3333ff"));
                                                Grid.grid[index[0]+i][index[1]+j] = 1;
                                        }
                                    }
                                }
                                ArrayList<int[]> res = GridVerify.verifyConnected(Grid.grid);
                                Log.d("Connected",Arrays.toString(res.toArray()));
                                GridVerify.deleteConnected(res,Grid.grid);
                                Grid.updateGrid();
                                ((ViewGroup)dragged.getParent()).removeView(dragged);
                                if(Blocks.blockLayout.getChildCount()<1)Blocks.generateBlocks();
                            }
                        }
                        return true;

                    case DragEvent.ACTION_DRAG_ENDED:
                        return true;

                    default:
                        return false;
                }
            }
        });
    }
    private static int[] getImageViewIndex(ImageView imageView) {
        for (int row = 0; row < Grid.imageViews.length; row++) {
            for (int col = 0; col < Grid.imageViews[row].length; col++) {
                if (Grid.imageViews[row][col] == imageView) {
                    return new int[]{row, col};
                }
            }
        }
        return null; // ImageView not found
    }
}
