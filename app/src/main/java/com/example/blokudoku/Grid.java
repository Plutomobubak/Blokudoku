package com.example.blokudoku;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import android.content.Context;

public class Grid {
    public static ImageView[][] imageViews;
    public static int[][] grid;
    public static ConstraintLayout init(Context context) {
        // Create a new ConstraintLayout that will serve as the grid container
        ConstraintLayout gridLayout = new ConstraintLayout(context);
        gridLayout.setId(View.generateViewId());  // Set a unique ID for the grid layout


        //idk why this works but im not changing it
        ShapeDrawable shapeDrawable = new ShapeDrawable();
        shapeDrawable.getPaint().setColor(Color.parseColor("#dadadf")); // Set solid color
        shapeDrawable.setPadding(0,0,0,-500);

        InsetDrawable inset = new InsetDrawable(shapeDrawable,0,0,0,200);
        // Apply the InsetDrawable as the background
        gridLayout.setBackground(inset);

        // Define the number of rows and columns for the grid
        int gridSize = 9;
        int imageViewSize = 100;  // Size for each ImageView (e.g., 100x100dp)

        // Create an array to store ImageView references for later use in setting constraints
        ImageView[][] imageViews = new ImageView[gridSize + 2][gridSize];
        int[][] grid = new int[gridSize][gridSize];  // Adjusted grid size to include the extra row



        // Loop to create a grid of ImageViews
        for (int row = 0; row < gridSize + 2; row++) {
            for (int col = 0; col < gridSize; col++) {
                // Create a new ImageView
                ImageView imageView = new ImageView(context);
                imageView.setId(View.generateViewId());  // Assign a unique ID to ImageView

                // Set background color based on the row and column
                if (row >= gridSize) {
                    imageView.setBackgroundColor(Color.TRANSPARENT);  // Invisible row
                } else if ((row > 2 && row < 6) ^ (col > 2 && col < 6)) {
                    imageView.setBackgroundColor(Color.parseColor("#eaeaef"));  // Background color
                } else {
                    imageView.setBackgroundColor(Color.parseColor("#ffffff"));  // Background color
                }

                // Set layout parameters for ImageView
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(imageViewSize, imageViewSize);
                imageView.setLayoutParams(params);

                // Add the ImageView to the grid ConstraintLayout
                gridLayout.addView(imageView);

                // Store reference for later constraint setting
                imageViews[row][col] = imageView;
                if (row < gridSize) {
                    grid[row][col] = 0;  // Initialize grid values
                }
            }
        }

        // Now set the constraints to create the grid
        ConstraintSet gridConstraints = new ConstraintSet();
        gridConstraints.clone(gridLayout);  // Clone the existing layout

        for (int row = 0; row < gridSize + 2; row++) {
            for (int col = 0; col < gridSize; col++) {
                ImageView currentView = imageViews[row][col];

                // Set margins
                gridConstraints.setMargin(currentView.getId(), ConstraintSet.TOP, 2);
                gridConstraints.setMargin(currentView.getId(), ConstraintSet.BOTTOM, 2);
                gridConstraints.setMargin(currentView.getId(), ConstraintSet.END, 2);
                gridConstraints.setMargin(currentView.getId(), ConstraintSet.START, 2);

                // Set constraints for each ImageView
                if (row == 0) {
                    gridConstraints.setMargin(currentView.getId(), ConstraintSet.TOP, 10);
                    gridConstraints.connect(currentView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                } else {
                    gridConstraints.connect(currentView.getId(), ConstraintSet.TOP, imageViews[row - 1][col].getId(), ConstraintSet.BOTTOM);
                }

                if (row == gridSize + 1) {
                    gridConstraints.setMargin(currentView.getId(), ConstraintSet.BOTTOM, 10);
                    gridConstraints.connect(currentView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                } else {
                    gridConstraints.connect(currentView.getId(), ConstraintSet.BOTTOM, imageViews[row + 1][col].getId(), ConstraintSet.TOP);
                }

                if (col == 0) {
                    gridConstraints.setMargin(currentView.getId(), ConstraintSet.START, 10);
                    gridConstraints.connect(currentView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
                } else {
                    gridConstraints.connect(currentView.getId(), ConstraintSet.START, imageViews[row][col - 1].getId(), ConstraintSet.END);
                }

                if (col == gridSize - 1) {
                    gridConstraints.setMargin(currentView.getId(), ConstraintSet.END, 10);
                    gridConstraints.connect(currentView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
                } else {
                    gridConstraints.connect(currentView.getId(), ConstraintSet.END, imageViews[row][col + 1].getId(), ConstraintSet.START);
                }
            }
        }

        // Apply the grid constraints
        gridConstraints.applyTo(gridLayout);
        Grid.imageViews = imageViews;
        Grid.grid = grid;
        Grid.AddDragDrop(Blocks.blocks, context);
        return gridLayout;
    }


    public static void updateGrid(){
        for (int i = 0; i < imageViews.length-2; i++) {
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
