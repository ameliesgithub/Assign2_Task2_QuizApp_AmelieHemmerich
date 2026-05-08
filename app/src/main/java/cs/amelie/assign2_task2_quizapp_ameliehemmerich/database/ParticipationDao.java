package cs.amelie.assign2_task2_quizapp_ameliehemmerich.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import cs.amelie.assign2_task2_quizapp_ameliehemmerich.model.Participation;

@Dao
public interface ParticipationDao {

    @Insert
    void insert(Participation participation);

    @Query("SELECT COUNT(*) FROM participations WHERE userId = :userId AND tournamentId = :tournamentId")
    int hasParticipated(int userId, int tournamentId);

    @Query("SELECT * FROM participations WHERE userId = :userId")
    List<Participation> getParticipationsForUser(int userId);

    @Query("SELECT AVG(rating) FROM participations WHERE tournamentId = :tournamentId")
    float getAverageRaitngForTournament(int tournamentId);

    @Query("SELECT score FROM participations WHERE userId = :userId AND tournamentId = :tournamentId LIMIT 1")
    int getScoreForTournament(int userId, int tournamentId);
}
