package cs.amelie.assign2_task2_quizapp_ameliehemmerich.auth;

import android.content.Intent;
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

import cs.amelie.assign2_task2_quizapp_ameliehemmerich.R;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.admin.AdminDashboardActivity;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.database.AppDatabase;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.model.User;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.player.PlayerDashboardActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword;
    private Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtEmail = findViewById(R.id.edtEmailLogin);
        edtPassword = findViewById(R.id.edtPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnSwitchRegisterLogin);

        AppDatabase db = AppDatabase.getInstance(this);

        if(db.userDao().countUsers() == 0) {
            db.userDao().insert(new User("admin@test.com", "hello", "ADMIN"));
            db.userDao().insert(new User("player@test.com", "hello", "PLAYER"));
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        AppDatabase db = AppDatabase.getInstance(this);
        User user = db.userDao().login(email, password);

        if (user == null) {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            return;
        }

        if(user.getRole().equals("ADMIN")) {
            Intent intent = new Intent(getApplicationContext(), AdminDashboardActivity.class);
            startActivity(intent);
        } else if (user.getRole().equals("PLAYER")) {
            Intent intent = new Intent(getApplicationContext(), PlayerDashboardActivity.class);
            startActivity(intent);
        }

        finish();
    }
}