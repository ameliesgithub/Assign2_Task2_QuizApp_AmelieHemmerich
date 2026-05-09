package cs.amelie.assign2_task2_quizapp_ameliehemmerich.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "participations")
public class Participation {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId;
    private int tournamentId;
    private int score;
    private int rating;
    private String completedAt;

    public Participation(int userId, int tournamentId, int score, int rating, String completedAt) {
        this.userId = userId;
        this.tournamentId = tournamentId;
        this.score = score;
        this.rating = rating;
        this.completedAt = completedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }
}
