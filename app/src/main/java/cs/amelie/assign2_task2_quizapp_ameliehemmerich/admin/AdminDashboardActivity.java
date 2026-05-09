package cs.amelie.assign2_task2_quizapp_ameliehemmerich.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import cs.amelie.assign2_task2_quizapp_ameliehemmerich.R;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.auth.LoginActivity;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.network.OpenTdbApi;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.network.OpenTdbResponse;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.network.QuestionResponse;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDashboardActivity extends AppCompatActivity {

    private LinearLayout llCreateTournament, llViewAllTournaments;
    private TextView tvLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        llCreateTournament = findViewById(R.id.llCreateTournament);
        llViewAllTournaments = findViewById(R.id.llViewAllTournaments);
        tvLogout = findViewById(R.id.tvLogoutAdminDash);

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        llCreateTournament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateTournamentActivity.class);
                startActivity(intent);
            }
        });

        llViewAllTournaments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewTournamentsActivity.class);
                startActivity(intent);
            }
        });

    }
}