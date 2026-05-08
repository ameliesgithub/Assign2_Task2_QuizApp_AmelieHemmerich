package cs.amelie.assign2_task2_quizapp_ameliehemmerich.player;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cs.amelie.assign2_task2_quizapp_ameliehemmerich.R;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.database.AppDatabase;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.model.Tournament;

public class PlayerDashboardActivity extends AppCompatActivity {

    private LinearLayout llOngoingTournaments, llUpcomingTournaments, llPastTournaments, llParticipatedTournaments;

    private AppDatabase db;
    private int currentUser = 2;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_player_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = AppDatabase.getInstance(this);

        llOngoingTournaments = findViewById(R.id.llOngoingTournaments);
        llUpcomingTournaments = findViewById(R.id.llUpcomingTournaments);
        llPastTournaments = findViewById(R.id.llPastTournaments);
        llParticipatedTournaments = findViewById(R.id.llParticipatedTournaments);

        llOngoingTournaments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTournamentList("ongoing");
            }
        });
        llUpcomingTournaments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTournamentList("upcoming");
            }
        });
        llPastTournaments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTournamentList("past");
            }
        });
        llParticipatedTournaments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTournamentList("participated");
            }
        });
    }

    private void openTournamentList(String type) {
        Intent intent = new Intent(this, TournamentListActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

}