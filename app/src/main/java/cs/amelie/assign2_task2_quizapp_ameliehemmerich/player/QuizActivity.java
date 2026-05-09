package cs.amelie.assign2_task2_quizapp_ameliehemmerich.player;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cs.amelie.assign2_task2_quizapp_ameliehemmerich.R;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.database.AppDatabase;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.model.Question;

public class QuizActivity extends AppCompatActivity {

    private TextView tvQuestion, tvQuestionNumber, tvFeedback;
    private RadioGroup rgAnswers;
    private Button btnSubmit, btnNext;
    private AppDatabase db;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int tournamentId;
    private int userId;
    private String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvQuestionNumber = findViewById(R.id.tvQuestionNumber);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvFeedback = findViewById(R.id.tvFeedback);
        rgAnswers = findViewById(R.id.radioGroupAnswers);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnNext = findViewById(R.id.btnNext);

        db = AppDatabase.getInstance(this);

        tournamentId = getIntent().getIntExtra("tournamentId", -1);
        userId = getIntent().getIntExtra("userId", -1);

        questions = db.questionDao().getQuestionsForTournament(tournamentId);

        if(questions == null || questions.isEmpty()) {
            Toast.makeText(this, "No questions found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        showQuestion();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextQuestion();
            }
        });
    }

    private void showQuestion() {
        selectedAnswer = "";
        tvFeedback.setText("");
        rgAnswers.clearCheck();
        rgAnswers.removeAllViews();

        btnSubmit.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.GONE);

        Question currentQuestion = questions.get(currentQuestionIndex);

        tvQuestionNumber.setText(
                "Question " + (currentQuestionIndex + 1) + " of " + questions.size()
        );

        tvQuestion.setText(Html.fromHtml(currentQuestion.getQuestionText(), Html.FROM_HTML_MODE_LEGACY));

        List<String> answers = new ArrayList<>();

        answers.add(currentQuestion.getCorrectAnswer());

        String[] incorrectAnswers = currentQuestion.getIncorrectAnswers().split(",");

        for(String incorrect : incorrectAnswers) {
            answers.add(incorrect);
        }

        Collections.shuffle(answers);

        for(String answer : answers) {
            RadioButton radioBtn = new RadioButton(this);
            radioBtn.setId(View.generateViewId());
            radioBtn.setText(Html.fromHtml(answer, Html.FROM_HTML_MODE_LEGACY));
            radioBtn.setTextSize(18);
            rgAnswers.addView(radioBtn);
        }
    }

    private void checkAnswer() {
        int selectedId = rgAnswers.getCheckedRadioButtonId();

        if(selectedId == -1) {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedRadioButton = findViewById(selectedId);
        selectedAnswer = selectedRadioButton.getText().toString();

        Question currentQuestion = questions.get(currentQuestionIndex);
        String correctAnswer = Html.fromHtml(
                currentQuestion.getCorrectAnswer(),
                Html.FROM_HTML_MODE_LEGACY
        ).toString();

        if(selectedAnswer.trim().equalsIgnoreCase(correctAnswer.trim())) {
            score++;
            tvFeedback.setText("Correct!");
        } else {
            tvFeedback.setText("Incorrect. Correct answer: " + correctAnswer);
        }

        btnSubmit.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }

    private void goToNextQuestion() {
        if(currentQuestionIndex < questions.size() - 1) {
            currentQuestionIndex++;
            showQuestion();
        } else {
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("score", score);
            intent.putExtra("total", questions.size());
            intent.putExtra("tournamentId", tournamentId);
            intent.putExtra("userId", userId);
            startActivity(intent);
            finish();
        }
    }
}