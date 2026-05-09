package cs.amelie.assign2_task2_quizapp_ameliehemmerich.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import cs.amelie.assign2_task2_quizapp_ameliehemmerich.model.User;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    User login(String email, String password);

    @Query("SELECT COUNT(*) FROM users")
    int countUsers();

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);
}
