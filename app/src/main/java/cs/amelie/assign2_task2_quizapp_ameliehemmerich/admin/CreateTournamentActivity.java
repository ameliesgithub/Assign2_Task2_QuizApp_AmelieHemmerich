package cs.amelie.assign2_task2_quizapp_ameliehemmerich.admin;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cs.amelie.assign2_task2_quizapp_ameliehemmerich.R;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.database.AppDatabase;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.database.TournamentDao;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.model.Question;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.model.Tournament;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.network.OpenTdbApi;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.network.OpenTdbResponse;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.network.QuestionResponse;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTournamentActivity extends AppCompatActivity {
    private EditText edtName, edtCategory, edtStartDate, edtEndDate;
    private Spinner spDifficulty;
    private Button btnCreate;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_tournament);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = AppDatabase.getInstance(this);

        edtName = findViewById(R.id.edtTourNameInput);
        edtCategory = findViewById(R.id.edtCategoryInput);
        edtStartDate = findViewById(R.id.edtStartDateInput);
        edtEndDate = findViewById(R.id.edtEndDateInput);
        spDifficulty = findViewById(R.id.spDifficultyInput);
        btnCreate = findViewById(R.id.btnCreateTour);

        setupDifficultySpinner();
        setupDatePickers();

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTournament();
            }
        });
    }

    private void setupDifficultySpinner() {
        String[] difficulties = {"easy", "medium", "hard"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                difficulties
        );

        spDifficulty.setAdapter(adapter);
    }

    private void setupDatePickers() {
        edtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(edtStartDate);
            }
        });
        edtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(edtEndDate);
            }
        });
    }

    private void showDatePicker(EditText targetInput) {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    String date = year + "-" + String.format("%02d", month + 1)
                            + "-" + String.format("%02d", dayOfMonth);

                    targetInput.setText(date);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        dialog.show();
    }

    private void createTournament() {
        String name = edtName.getText().toString().trim();
        String categoryText = edtCategory.getText().toString().trim();
        String difficulty = spDifficulty.getSelectedItem().toString();
        String startDate = edtStartDate.getText().toString().trim();
        String endDate = edtEndDate.getText().toString().trim();

        if(name.isEmpty() || categoryText.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        int category = Integer.parseInt(categoryText);

        fetchQuestionsAndSaveTournament(name, category, difficulty, startDate, endDate);
    }

    private void fetchQuestionsAndSaveTournament(String name, int category, String difficulty, String startDate, String endDate) {
        OpenTdbApi api = RetrofitClient.getClient().create(OpenTdbApi.class);

        Call<OpenTdbResponse> call = api.getQuestions(
                10,
                category,
                difficulty
        );

        btnCreate.setEnabled(false);

        call.enqueue(new Callback<OpenTdbResponse>() {
            @Override
            public void onResponse(Call<OpenTdbResponse> call, Response<OpenTdbResponse> response) {
                btnCreate.setEnabled(true);

                if(!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(CreateTournamentActivity.this,
                            "Failed to load questions",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                List<QuestionResponse> questionResponses = response.body().getResults();

                if(questionResponses == null || questionResponses.size() < 10) {
                    Toast.makeText(CreateTournamentActivity.this,
                            "Only found " +
                                    (questionResponses == null ? 0 : questionResponses.size()) +
                            " questions. Try another category or difficulty.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!isValidDateRange(startDate, endDate)) {
                    Toast.makeText(CreateTournamentActivity.this, "Start date must be before or equal to end date", Toast.LENGTH_SHORT).show();
                    return;
                }

                Tournament tournament = new Tournament(
                        name,
                        category,
                        difficulty,
                        startDate,
                        endDate
                );

                long tournamentId = db.tournamentDao().insert(tournament);

                for(QuestionResponse qr : questionResponses) {
                    String incorrectAnswers = String.join(",", qr.getIncorrect_answers());

                    Question question = new Question(
                            (int) tournamentId,
                            qr.getQuestion(),
                            qr.getCorrect_answer(),
                            incorrectAnswers,
                            qr.getType()
                    );

                    db.questionDao().insert(question);
                }

                Toast.makeText(CreateTournamentActivity.this,
                        "Tournament created successfully!",
                        Toast.LENGTH_SHORT).show();

                finish();
            }

            @Override
            public void onFailure(Call<OpenTdbResponse> call, Throwable t) {
                btnCreate.setEnabled(true);

                Toast.makeText(CreateTournamentActivity.this,
                        "Error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidDateRange(String startDate, String endDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);

            return start != null && end != null && !start.after(end);
        } catch (Exception e) {
            return false;
        }
    }
}