package cs.amelie.assign2_task2_quizapp_ameliehemmerich.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import cs.amelie.assign2_task2_quizapp_ameliehemmerich.model.Question;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.model.Tournament;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.model.User;

@Database(
        entities = {User.class, Tournament.class, Question.class},
        version = 3
)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    public abstract UserDao userDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "quiz_database"
            )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }

    public abstract TournamentDao tournamentDao();
    public abstract QuestionDao questionDao();
}
