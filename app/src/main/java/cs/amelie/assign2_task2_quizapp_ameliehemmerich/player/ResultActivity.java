package cs.amelie.assign2_task2_quizapp_ameliehemmerich.player;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cs.amelie.assign2_task2_quizapp_ameliehemmerich.R;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.database.AppDatabase;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.model.Participation;

public class ResultActivity extends AppCompatActivity {

    private TextView tvScore;
    private RatingBar ratingBar;
    private Button btnFinish;
    private AppDatabase db;
    private int score, total, tournamentId, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = AppDatabase.getInstance(this);

        tvScore = findViewById(R.id.tvScoreResults);
        ratingBar = findViewById(R.id.ratingBar);
        btnFinish = findViewById(R.id.btnFinish);

        score = getIntent().getIntExtra("score", 0);
        total = getIntent().getIntExtra("total", 10);
        tournamentId = getIntent().getIntExtra("tournamentId", -1);
        userId = getIntent().getIntExtra("userId", -1);

        tvScore.setText("Your score: " + score + "/" + total);

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveParticipation();
            }
        });
    }

    private void saveParticipation() {
        int rating = (int) ratingBar.getRating();

        String completedAt = new SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
        ).format(new Date());

        Participation participation = new Participation(
                userId,
                tournamentId,
                score,
                rating,
                completedAt
        );

        db.participationDao().insert(participation);

        Toast.makeText(this, "Result saved", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, PlayerDashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}