package com.example.blokudoku;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.EditText;
import android.widget.LinearLayout;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import okio.Timeout;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        EdgeToEdge.enable(this);
        setContentView(R.layout.menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if(GameStateManager.getUID(this)=="")GameStateManager.generateUID(this);
        if(GameStateManager.getName(this)=="")showNameInputDialog(this);
        LinearLayout layout = findViewById(R.id.buttons);

        Button startButton = new Button(this);
        if(GameStateManager.loadGrid(this).length>0){
            Button continueButton = new Button(this);
            continueButton.setText("Continue");
            continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Menu.this, MainActivity.class);
                    intent.putExtra("LOAD",true);
                    startActivity(intent);
                }
            });
            layout.addView(continueButton);
        }

        startButton.setText("New Game");
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, MainActivity.class);
                intent.putExtra("LOAD",false);
                startActivity(intent);
            }
        });

        layout.addView(startButton);
        RecyclerView rc = findViewById(R.id.leaderboard);
        rc.setLayoutManager(new LinearLayoutManager(this));

        ScoreAdapter adapter = new ScoreAdapter(new ArrayList<>());
        rc.setAdapter(adapter);

        Leaderboards.FetchScoresCallback callbacks = new Leaderboards.FetchScoresCallback() {
            @Override
            public void onSuccess(List<Leaderboards.Score> scores) {
                // Data is ready, update the RecyclerView adapter
                adapter.updateScores(scores); // Assuming you've added a method to update scores in your adapter
                adapter.notifyDataSetChanged(); // Notify the adapter to update the UI
            }

            @Override
            public void onFailure(Throwable t) {
                // Handle failure (e.g., show a message to the user)
                Log.d("MainActivity", "Failed to fetch scores: " + t.getMessage());
            }
        };


        findViewById(R.id.day).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Leaderboards.fetchScores(0, callbacks);
            }
        });
        findViewById(R.id.week).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Leaderboards.fetchScores(1, callbacks);
            }
        });
        findViewById(R.id.month).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Leaderboards.fetchScores(2, callbacks);
            }
        });
        findViewById(R.id.all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Leaderboards.fetchScores(3, callbacks);
            }
        });
        Leaderboards.fetchScores(0, callbacks);
    }


    public void showNameInputDialog(Context context) {
        // Create an EditText to get the user's input
        final EditText input = new EditText(context);

        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Enter your name");

        // Set the input view to the dialog
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = input.getText().toString();
                // Do something with the input (e.g., display it, save it, etc.)
                GameStateManager.setName(context,name);
            }
        });

        builder.setNegativeButton("Clear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.show();
            }
        });

        // Show the dialog
        builder.show();
    }
}