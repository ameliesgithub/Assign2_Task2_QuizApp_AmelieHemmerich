package cs.amelie.assign2_task2_quizapp_ameliehemmerich.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import cs.amelie.assign2_task2_quizapp_ameliehemmerich.model.Tournament;

@Dao
public interface TournamentDao {

    @Insert
    long insert(Tournament tournament);

    @Query("SELECT * FROM tournaments")
    List<Tournament> getAllTournaments();
}
