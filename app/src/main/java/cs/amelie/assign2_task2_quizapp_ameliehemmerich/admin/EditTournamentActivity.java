package cs.amelie.assign2_task2_quizapp_ameliehemmerich.admin;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

import cs.amelie.assign2_task2_quizapp_ameliehemmerich.R;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.database.AppDatabase;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.model.Tournament;

public class EditTournamentActivity extends AppCompatActivity {

    private EditText edtName, edtStartDate, edtEndDate;
    private Button btnSave;
    private AppDatabase db;
    private int tournamentId, category;
    private String difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_tournament);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = AppDatabase.getInstance(this);

        edtName = findViewById(R.id.edtTourNameEdit);
        edtStartDate = findViewById(R.id.edtStartDateEdit);
        edtEndDate = findViewById(R.id.edtEndDateEdit);
        btnSave = findViewById(R.id.btnUpdateTour);

        loadIntentData();
        setupDatePickers();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTournament();
            }
        });
    }

    private void loadIntentData() {
        tournamentId = getIntent().getIntExtra("id", -1);

        String name = getIntent().getStringExtra("name");
        String startDate = getIntent().getStringExtra("startDate");
        String endDate = getIntent().getStringExtra("endDate");

        category = getIntent().getIntExtra("category", 9);
        difficulty = getIntent().getStringExtra("difficulty");

        if (difficulty == null) {
            difficulty = "easy";
        }

        edtName.setText(name);
        edtStartDate.setText(startDate);
        edtEndDate.setText(endDate);
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
                    String date = year + "-" +
                            String.format("%02d", month + 1) + "-" +
                            String.format("%02d", dayOfMonth);

                    targetInput.setText(date);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        dialog.show();
    }

    private void updateTournament() {
        String name = edtName.getText().toString().trim();
        String startDate = edtStartDate.getText().toString().trim();
        String endDate = edtEndDate.getText().toString().trim();

        if(name.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Tournament updatedTournament = new Tournament(
                name,
                category,
                difficulty,
                startDate,
                endDate
        );

        updatedTournament.setId(tournamentId);

        db.tournamentDao().update(updatedTournament);

        Toast.makeText(this, "Tournament updated", Toast.LENGTH_SHORT).show();
        finish();
    }
}