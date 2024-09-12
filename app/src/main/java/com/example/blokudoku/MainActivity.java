package com.example.blokudoku;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        boolean load = intent.getBooleanExtra("LOAD", false);
        // Find the main ConstraintLayout from the XML
        ConstraintLayout mainLayout = findViewById(R.id.main);
        LinearLayout blockLayout = findViewById(R.id.blocks);
        ConstraintLayout gridLayout = Grid.init(this);
        // Add the gridLayout to the main ConstraintLayout
        mainLayout.addView(gridLayout);

        // Now set constraints for gridLayout inside mainLayout
        ConstraintSet mainConstraints = new ConstraintSet();
        mainConstraints.clone(mainLayout);

        // Constrain the gridLayout below the title TextView
        mainConstraints.connect(gridLayout.getId(), ConstraintSet.TOP, R.id.score, ConstraintSet.BOTTOM, 32);
        mainConstraints.connect(gridLayout.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        mainConstraints.connect(gridLayout.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);

        // Apply the constraints to the main layout
        mainConstraints.applyTo(mainLayout);
        int score = 0;
        Blocks.init(this, blockLayout);
        if(load){
            score = GameStateManager.loadScore(this);

            int[][] grid = GameStateManager.loadGrid(this);
            if (grid.length > 8) {
                Grid.grid = grid;
                Grid.updateGrid();
            }
            int[] blocks = GameStateManager.loadBlocks(this);
            for (int block : blocks) {
                Blocks.renderBlock(this, blockLayout, block);
            }
        }
        else {
            Blocks.generateBlocks();
        }
        ScoreManager.init(findViewById(R.id.score),findViewById(R.id.popup),score);
    }
}