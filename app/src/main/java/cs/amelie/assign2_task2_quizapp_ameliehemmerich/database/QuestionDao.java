package cs.amelie.assign2_task2_quizapp_ameliehemmerich.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import cs.amelie.assign2_task2_quizapp_ameliehemmerich.model.Question;

@Dao
public interface QuestionDao {

    @Insert
    void insert(Question question);

    @Query("SELECT * FROM questions WHERE tournamentId = :tournamentId")
    List<Question> getQuestionsForTournament(int tournamentId);
}
