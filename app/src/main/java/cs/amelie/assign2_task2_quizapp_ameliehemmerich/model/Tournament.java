package cs.amelie.assign2_task2_quizapp_ameliehemmerich.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tournaments")
public class Tournament {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int category;
    private String difficulty;
    private String startDate;
    private String endDate;

    public Tournament(String name, int category, String difficulty, String startDate, String endDate) {
        this.name = name;
        this.category = category;
        this.difficulty = difficulty;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
