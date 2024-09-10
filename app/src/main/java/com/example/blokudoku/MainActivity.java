package com.example.blokudoku;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.LinearLayout;
import android.widget.TextView;

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
        TextView title = findViewById(R.id.title);
        title.setText("Blokucocku");

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
        mainConstraints.connect(gridLayout.getId(), ConstraintSet.TOP, R.id.title, ConstraintSet.BOTTOM, 32);
        mainConstraints.connect(gridLayout.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        mainConstraints.connect(gridLayout.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);

        // Apply the constraints to the main layout
        mainConstraints.applyTo(mainLayout);

        Blocks.init(this,blockLayout);
        Blocks.generateBlocks();
    }
}