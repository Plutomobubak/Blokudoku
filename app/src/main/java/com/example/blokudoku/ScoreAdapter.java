package com.example.blokudoku;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

    private List<Leaderboards.Score> scoreList;

    // Constructor for ScoreAdapter
    public ScoreAdapter(List<Leaderboards.Score> scoreList) {
        this.scoreList = scoreList;
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_row, parent, false);
        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        Leaderboards.Score score = scoreList.get(position);
        holder.playerName.setText(score.player);
        holder.playerScore.setText(String.valueOf(score.score));
    }

    @Override
    public int getItemCount() {
        return scoreList.size();
    }

    public void updateScores(List<Leaderboards.Score> scores) {
        scoreList = scores;
    }

    // ViewHolder class
    public static class ScoreViewHolder extends RecyclerView.ViewHolder {

        TextView playerName, playerScore;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.player);
            playerScore = itemView.findViewById(R.id.score);
        }
    }
}

