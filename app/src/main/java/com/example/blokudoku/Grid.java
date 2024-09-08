package com.example.blokudoku;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import android.content.Context;

public class Grid {
    public static ImageView[][] imageViews;
    public static int[][] grid;
    public static ConstraintLayout init(Context context){
        // Create a new ConstraintLayout that will serve as the grid container
        ConstraintLayout gridLayout = new ConstraintLayout(context);
        gridLayout.setId(View.generateViewId());  // Set a unique ID for the grid layout
        gridLayout.setBackgroundColor(Color.parseColor("#dadadf"));

        // Define the number of rows and columns for the grid
        int gridSize = 9;
        int imageViewSize = 100;  // Size for each ImageView (for example, 100x100dp)

        // Create an array to store ImageView references for later use in setting constraints
        ImageView[][] imageViews = new ImageView[gridSize][gridSize];
        int[][] grid = new int[gridSize][gridSize];

        // Loop to create a 9x9 grid of ImageViews
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                // Create a new ImageView
                ImageView imageView = new ImageView(context);
                imageView.setId(View.generateViewId());  // Assign a unique ID to ImageView
                //imageView.setImageResource(R.drawable.ic_launcher_background);  // Set any drawable
                if ((row > 2 && row < 6) ^ (col > 2 && col < 6))
                {
                    imageView.setBackgroundColor(Color.parseColor("#eaeaef"));  // Set background color

                }
                else imageView.setBackgroundColor(Color.parseColor("#ffffff"));  // Set background color


                // Set layout parameters for ImageView
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(imageViewSize, imageViewSize);
                imageView.setLayoutParams(params);

                // Add the ImageView to the grid ConstraintLayout
                gridLayout.addView(imageView);

                // Store reference for later constraint setting
                imageViews[row][col] = imageView;
                grid[row][col] = 0;
            }
        }

        // Now set the constraints to create the 9x9 grid
        ConstraintSet gridConstraints = new ConstraintSet();
        gridConstraints.clone(gridLayout);  // Clone the existing layout

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                ImageView currentView = imageViews[row][col];

                gridConstraints.setMargin(currentView.getId(), ConstraintSet.TOP, 2);
                gridConstraints.setMargin(currentView.getId(), ConstraintSet.BOTTOM, 2);
                gridConstraints.setMargin(currentView.getId(), ConstraintSet.END, 2);
                gridConstraints.setMargin(currentView.getId(), ConstraintSet.START, 2);
                // Set constraints for each ImageView
                if (row == 0) {
                    // Top row: Constrain top to the parent of the gridLayout
                    gridConstraints.setMargin(currentView.getId(), ConstraintSet.TOP, 10);
                    gridConstraints.connect(currentView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                }
                else {
                    // Other rows: Constrain top to the bottom of the ImageView above it
                    gridConstraints.connect(currentView.getId(), ConstraintSet.TOP, imageViews[row - 1][col].getId(), ConstraintSet.BOTTOM);
                }

                if(row == gridSize-1){
                    gridConstraints.setMargin(currentView.getId(), ConstraintSet.BOTTOM, 10);
                    gridConstraints.connect(currentView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                }
                else{
                    gridConstraints.connect(currentView.getId(), ConstraintSet.BOTTOM, imageViews[row + 1][col].getId(), ConstraintSet.TOP);
                }

                if (col == 0) {
                    gridConstraints.setMargin(currentView.getId(), ConstraintSet.START, 10);
                    // Left column: Constrain left to the parent of the gridLayout
                    gridConstraints.connect(currentView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
                } else {
                    // Other columns: Constrain left to the right of the previous ImageView
                    gridConstraints.connect(currentView.getId(), ConstraintSet.START, imageViews[row][col - 1].getId(), ConstraintSet.END);
                }

                if (col == gridSize-1) {
                    // Left column: Constrain left to the parent of the gridLayout
                    gridConstraints.setMargin(currentView.getId(), ConstraintSet.END, 10);
                    gridConstraints.connect(currentView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
                } else {
                    // Other columns: Constrain left to the right of the previous ImageView
                    gridConstraints.connect(currentView.getId(), ConstraintSet.END, imageViews[row][col + 1].getId(), ConstraintSet.START);
                }

                // Optionally, add margins between cells
                   // Top margin
            }
        }

        // Apply the grid constraints
        gridConstraints.applyTo(gridLayout);
        Grid.imageViews = imageViews;
        Grid.grid = grid;
        Grid.AddDragDrop(Blocks.blocks,context);
        return gridLayout;
    }
    public static void updateGrid(){
        for (int i = 0; i < imageViews.length; i++) {
            for (int j = 0; j < imageViews[i].length; j++) {
                if(grid[i][j]==1)
                    imageViews[i][j].setBackgroundColor(Color.parseColor("#4444ff"));
                else if ((i > 2 && i < 6) ^ (j > 2 && j < 6))
                    imageViews[i][j].setBackgroundColor(Color.parseColor("#eaeaef"));  // Set background color
                else
                    imageViews[i][j].setBackgroundColor(Color.parseColor("#ffffff"));

            }
        }
    }
    public static void AddDragDrop(Block[] blocks,Context context){
        for (ImageView[] imageView : imageViews) {
            for (ImageView view : imageView) {
                DragDrop.setupDragAndDrop(blocks, view, context);
            }
        }

    }
}
