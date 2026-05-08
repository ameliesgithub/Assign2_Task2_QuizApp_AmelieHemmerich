package cs.amelie.assign2_task2_quizapp_ameliehemmerich.admin;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cs.amelie.assign2_task2_quizapp_ameliehemmerich.R;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.adapter.TournamentAdapter;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.database.AppDatabase;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.model.Tournament;

public class ViewTournamentsActivity extends AppCompatActivity {

    private RecyclerView rvTournaments;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_tournaments);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rvTournaments = findViewById(R.id.rvViewAllTournaments);
        db = AppDatabase.getInstance(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTournaments();
    }

    private void loadTournaments() {
        List<Tournament> tournaments = db.tournamentDao().getAllTournaments();

        TournamentAdapter adapter = new TournamentAdapter(tournaments);
        rvTournaments.setAdapter(adapter);
    }
}