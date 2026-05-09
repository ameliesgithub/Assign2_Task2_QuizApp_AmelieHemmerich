package cs.amelie.assign2_task2_quizapp_ameliehemmerich.player;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cs.amelie.assign2_task2_quizapp_ameliehemmerich.R;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.adapter.PlayerTournamentAdapter;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.auth.LoginActivity;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.database.AppDatabase;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.model.Tournament;

public class TournamentListActivity extends AppCompatActivity {

    private TextView tvTitle, tvEmptyMessage, tvLogout;
    private RecyclerView rvTournamentList;

    private AppDatabase db;
    private int currentUserId;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tournament_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvTitle = findViewById(R.id.tvHeaderTourList);
        tvEmptyMessage = findViewById(R.id.tvEmptyMessage);
        rvTournamentList = findViewById(R.id.rvTournamentList);
        tvLogout = findViewById(R.id.tvLogoutTourList);

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        currentUserId = getIntent().getIntExtra("userId", -1);
        db = AppDatabase.getInstance(this);

        String type = getIntent().getStringExtra("type");

        if(type == null) {
            type = "ongoing";
        }

        loadTournaments(type);
    }

    private void loadTournaments(String type) {
        List<Tournament> allTournaments = db.tournamentDao().getAllTournaments();
        List<Tournament> filteredTournaments = new ArrayList<>();

        Date today = new Date();

        for (Tournament tournament : allTournaments) {
            try {
                Date startDate = dateFormat.parse(tournament.getStartDate());
                Date endDate = dateFormat.parse(tournament.getEndDate());

                if(startDate == null || endDate == null) {
                    continue;
                }

                boolean hasParticipated = db.participationDao().hasParticipated(currentUserId, tournament.getId()) > 0;

                if (type.equals("participated") && hasParticipated) {
                    filteredTournaments.add(tournament);
                } else if (type.equals("ongoing") && !hasParticipated && !today.before(startDate) && !today.after(endDate)) {
                    filteredTournaments.add(tournament);
                } else if (type.equals("upcoming") && !hasParticipated && today.before(endDate)) {
                    filteredTournaments.add(tournament);
                } else if (type.equals("past") && !hasParticipated && today.after(endDate)) {
                    filteredTournaments.add(tournament);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(filteredTournaments.isEmpty()) {
            tvEmptyMessage.setVisibility(View.VISIBLE);
            rvTournamentList.setVisibility(View.GONE);
        } else {
            tvEmptyMessage.setVisibility(View.GONE);
            rvTournamentList.setVisibility(View.VISIBLE);
        }

        setTitleText(type);

        PlayerTournamentAdapter adapter = new PlayerTournamentAdapter(filteredTournaments, type, currentUserId);

        rvTournamentList.setAdapter(adapter);
    }

    private void setTitleText(String type) {
        if(type.equals("ongoing")) {
            tvTitle.setText("Ongoing Tournaments");
        } else if (type.equals("upcoming")) {
            tvTitle.setText("Upcoming Tournaments");
        } else if (type.equals("past")) {
            tvTitle.setText("Past Tournaments");
        } else if (type.equals("participated")) {
            tvTitle.setText("Participated Tournaments");
        }
    }
}