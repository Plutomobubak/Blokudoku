package com.example.blokudoku;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.View.DragShadowBuilder;

import androidx.constraintlayout.widget.ConstraintLayout;

import androidx.constraintlayout.widget.ConstraintSet;

import java.util.Random;

public class Blocks {
        public static Block[] blocks={
                // noodles
                // very short noodle
                new Block(new int[][]{
                        {1}
                }),
                new Block(new int[][]{
                        {1,1}
                }),
                new Block(new int[][]{
                        {1},
                        {1}
                }),
                new Block(new int[][]{
                        {1,1,1}
                }),
                new Block(new int[][]{
                        {1},
                        {1},
                        {1}
                }),
                new Block(new int[][]{
                        {1,1,1,1}
                }),
                new Block(new int[][]{
                        {1},
                        {1},
                        {1},
                        {1}
                }),
                new Block(new int[][]{
                        {1,1,1,1,1}
                }),
                new Block(new int[][]{
                        {1},
                        {1},
                        {1},
                        {1},
                        {1}
                }),
                new Block(new int[][]{
                        {1,0},
                        {0,1}
                }),
                new Block(new int[][]{
                        {0,1},
                        {1,0}
                }),
                // L-shapes
                //smol
                new Block(new int[][]{
                        {1,0},
                        {1,1}
                }),
                new Block(new int[][]{
                        {1,1},
                        {1,0}
                }),
                new Block(new int[][]{
                        {1,1},
                        {0,1}
                }),
                new Block(new int[][]{
                        {0,1},
                        {1,1}
                }),
                //medium
                new Block(new int[][]{
                        {0,0,1},
                        {1,1,1}
                }),
                new Block(new int[][]{
                        {1,0,0},
                        {1,1,1}
                }),
                new Block(new int[][]{
                        {1,1,1},
                        {1,0,0}
                }),
                new Block(new int[][]{
                        {1,1,1},
                        {0,0,1}
                }),
                new Block(new int[][]{
                        {1,0},
                        {1,0},
                        {1,1}
                }),
                new Block(new int[][]{
                        {1,1},
                        {1,0},
                        {1,0}
                }),
                new Block(new int[][]{
                        {1,1},
                        {0,1},
                        {0,1}
                }),
                new Block(new int[][]{
                        {0,1},
                        {0,1},
                        {1,1}
                }),
                //big
                new Block(new int[][]{
                        {0,0,1},
                        {0,0,1},
                        {1,1,1}
                }),
                new Block(new int[][]{
                        {1,0,0},
                        {1,0,0},
                        {1,1,1}
                }),
                new Block(new int[][]{
                        {1,1,1},
                        {0,0,1},
                        {0,0,1}
                }),
                new Block(new int[][]{
                        {1,1,1},
                        {1,0,0},
                        {1,0,0}
                }),
                //T- and cross
                new Block(new int[][]{
                        {1,0},
                        {1,1},
                        {1,0}
                }),
                new Block(new int[][]{
                        {0,1},
                        {1,1},
                        {0,1}
                }),
                new Block(new int[][]{
                        {1,1,1},
                        {0,1,0}
                }),
                new Block(new int[][]{
                        {0,1,0},
                        {1,1,1}
                }),
                new Block(new int[][]{
                        {0,1,0},
                        {1,1,1},
                        {0,1,0}
                }),
                new Block(new int[][]{
                        {1,1,1},
                        {0,1,0},
                        {0,1,0}
                }),
                new Block(new int[][]{
                        {0,1,0},
                        {0,1,0},
                        {1,1,1}
                }),
                new Block(new int[][]{
                        {1,0,0},
                        {1,1,1},
                        {1,0,0}
                }),
                new Block(new int[][]{
                        {0,0,1},
                        {1,1,1},
                        {0,0,1}
                }),
                // Zzzz
                new Block(new int[][]{
                        {0,1},
                        {1,1},
                        {1,0}
                }),
                new Block(new int[][]{
                        {1,0},
                        {1,1},
                        {0,1}
                }),
                new Block(new int[][]{
                        {0,1,1},
                        {1,1,0},
                }),
                new Block(new int[][]{
                        {1,1,0},
                        {0,1,1},
                }),
                // Blocks
                new Block(new int[][]{
                        {1,1},
                        {1,1}
                }),
                new Block(new int[][]{
                        {1,1},
                        {1,0},
                        {1,1}
                }),
                new Block(new int[][]{
                        {1,1},
                        {0,1},
                        {1,1}
                }),
                new Block(new int[][]{
                        {1,1,1},
                        {1,0,1}
                }),
                new Block(new int[][]{
                        {1,0,1},
                        {1,1,1}
                }),
        };
        private static Context context;
        public static LinearLayout blockLayout;


        public static void init(Context context,LinearLayout blockLayout){
            Blocks.context = context;
            Blocks.blockLayout= blockLayout;
        }
        public static void generateBlocks(){
            for (int i = 0; i < 3; i++) {
                Blocks.renderBlock(Blocks.context,Blocks.blockLayout, new Random().nextInt(Blocks.blocks.length));
            }
        }
        // Method to render a block from a matrix
        public static void renderBlock(Context context, LinearLayout layout, int blockInt) {
            int cellSize = (int) (100f /1.3f); // Size of each cell (e.g., 100x100dp)

            ConstraintLayout container = new ConstraintLayout(context);
            // Create a 2D array to hold references to ImageView elements
            ImageView[][] imageViews = new ImageView[blocks[blockInt].matrix.length][blocks[blockInt].matrix[0].length];

            // Loop through the matrix to create and add ImageView elements
            for (int row = 0; row < blocks[blockInt].matrix.length; row++) {
                for (int col = 0; col < blocks[blockInt].matrix[row].length; col++) {
                    // Create a new ImageView
                    ImageView imageView = new ImageView(context);
                    imageView.setId(View.generateViewId());

                    // Create a GradientDrawable for border
                    GradientDrawable borderDrawable = new GradientDrawable();
                    borderDrawable.setShape(GradientDrawable.RECTANGLE);
                    borderDrawable.setStroke(2, Color.TRANSPARENT); // Border width and color
                    borderDrawable.setColor(Color.TRANSPARENT); // Background color

                    // Set the drawable as the background of the ImageView
                    imageView.setBackground(borderDrawable);

                    // Set layout parameters
                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(cellSize, cellSize);
                    imageView.setLayoutParams(params);

                    // Add the ImageView to the container
                    container.addView(imageView);

                    // Store reference for later use
                    imageViews[row][col] = imageView;

                    if (blocks[blockInt].matrix[row][col] == 1) {
                        // Set the background color for block parts
                        imageView.setBackgroundColor(Color.BLUE); // Color for block part

                        // Optionally, you can combine with border drawable
                        GradientDrawable combinedDrawable = new GradientDrawable();
                        combinedDrawable.setShape(GradientDrawable.RECTANGLE);
                        combinedDrawable.setStroke(2, Color.parseColor("#dadadf")); // Border width and color
                        combinedDrawable.setColor(Color.parseColor("#3333ff")); // Background color for block part

                        imageView.setBackground(combinedDrawable);
                    }
                }
            }


            // Apply constraints to align ImageViews in a grid
            ConstraintSet constraints = new ConstraintSet();
            constraints.clone(container);

            for (int row = 0; row < imageViews.length; row++) {
                for (int col = 0; col < imageViews[row].length; col++) {
                    ImageView imageView = imageViews[row][col];
                    if (imageView != null) {
                        if (row > 0) {
                            constraints.connect(imageView.getId(), ConstraintSet.TOP, imageViews[row - 1][col].getId(), ConstraintSet.BOTTOM);
                        } else {
                            constraints.connect(imageView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                        }

                        if (col > 0) {
                            constraints.connect(imageView.getId(), ConstraintSet.START, imageViews[row][col - 1].getId(), ConstraintSet.END);
                        } else {
                            constraints.connect(imageView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
                        }
                    }
                }
            }

            // Apply the constraints
            constraints.applyTo(container);
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(32,32,32,32);
            container.setLayoutParams(params);
            blocks[blockInt].views.add(container);
            //set drag listener
            blocks[blockInt].views.get(blocks[blockInt].views.size()-1).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        ClipData data = ClipData.newPlainText("", "");
                        BlockDragShadowBuilder shadowBuilder = new BlockDragShadowBuilder(v);
                        v.startDragAndDrop(data, shadowBuilder, v, 0);
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            layout.addView(container);
        }

    public static class BlockDragShadowBuilder extends DragShadowBuilder {
        private final float scaleFactor = 1.3f; // You can change this scale factor
        private final float touchShiftFactor = 1.5f;
        public BlockDragShadowBuilder(View view) {
            super(view);
        }

        @Override
        public void onProvideShadowMetrics(Point size, Point touch) {
            View view = getView();

            // Set the shadow size to a scaled-up version of the view's size
            int width = (int) (view.getWidth() * scaleFactor);
            int height = (int) (view.getHeight() * scaleFactor);

            // Set the shadow size
            size.set(width, height);

            // Set the touch point to the center of the scaled view
            int shiftedTouchY = 250;//(int) (height * touchShiftFactor);
            touch.set(width/2 +25, shiftedTouchY);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            // Scale the canvas before drawing the shadow
            canvas.scale(scaleFactor, scaleFactor);
            getView().draw(canvas);
        }
    }
}
