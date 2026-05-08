package cs.amelie.assign2_task2_quizapp_ameliehemmerich.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cs.amelie.assign2_task2_quizapp_ameliehemmerich.model.Tournament;

@Dao
public interface TournamentDao {

    @Insert
    long insert(Tournament tournament);

    @Query("SELECT * FROM tournaments")
    List<Tournament> getAllTournaments();

    @Delete
    void delete(Tournament tournament);

    @Update
    void update(Tournament tournament);
}
