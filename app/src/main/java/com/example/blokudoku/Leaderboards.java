package com.example.blokudoku;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class Leaderboards {

    private static Retrofit rf;
    public static List<Score> scores;


    private interface LeaderboardService{
        @POST("/post")
        Call<Void> postScore(@Body Score score);
        @GET("/get/{cat}")
        Call<List<Score>> listScores(@Path("cat") int cat);
    }

    public static class Score{
        @SerializedName("Player")
        public String player;
        @SerializedName("Score")
        public int score;
        @SerializedName("UID")
        public String UID;

        public Score(String player, int score, String UID) {
            this.player = player;
            this.score = score;
            this.UID = UID;
        }
    }
    public static void postScore(String UID,String name,int score){
        Score ins = new Score(name, score,UID);
        if (Leaderboards.rf == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(50, TimeUnit.SECONDS)  // Set connection timeout
                    .readTimeout(50, TimeUnit.SECONDS)     // Set read timeout
                    .writeTimeout(50, TimeUnit.SECONDS)    // Set write timeout
                    .build();
            Leaderboards.rf = new Retrofit.Builder()
                    .baseUrl("https://node-server-ufna.onrender.com/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        LeaderboardService service = rf.create(LeaderboardService.class);
        service.postScore(ins).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("Post", "Score posted successfully");
                } else {
                    Log.d("Post", "Failed to post score. Status: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("Post", "Request failed: " + t.getMessage());
            }
        });
    }
    public static void fetchScores(int category, FetchScoresCallback callback) {
        if (Leaderboards.rf == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(50, TimeUnit.SECONDS)  // Set connection timeout
                    .readTimeout(50, TimeUnit.SECONDS)     // Set read timeout
                    .writeTimeout(50, TimeUnit.SECONDS)    // Set write timeout
                    .build();
            Leaderboards.rf = new Retrofit.Builder()
                    .baseUrl("https://node-server-ufna.onrender.com/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        LeaderboardService service = rf.create(LeaderboardService.class);
        Call<List<Score>> call = service.listScores(category);

        call.enqueue(new Callback<List<Score>>() {
            @Override
            public void onResponse(Call<List<Score>> call, Response<List<Score>> response) {
                if (response.isSuccessful()) {
                    Leaderboards.scores = response.body();
                    Log.d("Response", "First player: " + Leaderboards.scores.get(0).player);
                    callback.onSuccess(Leaderboards.scores); // Trigger callback to update the UI
                } else {
                    Log.d("Leaderboards", "Request failed with status: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Score>> call, Throwable t) {
                Log.d("Leaderboards", "Request failed: " + t.getMessage());
                callback.onFailure(t);
            }
        });
    }

    // Callback interface to handle the response in your activity
    public interface FetchScoresCallback {
        void onSuccess(List<Score> scores);
        void onFailure(Throwable t);
    }
}
