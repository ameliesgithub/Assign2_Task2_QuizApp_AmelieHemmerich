package cs.amelie.assign2_task2_quizapp_ameliehemmerich.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "questions")
public class Question {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int tournamentId;
    private String questionText;
    private String correctAnswer;
    private String incorrectAnswers;
    private String type;

    public Question(int tournamentId, String questionText, String correctAnswer, String incorrectAnswers, String type) {
        this.tournamentId = tournamentId;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(String incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
