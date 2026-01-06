package com.example.ai_study_buddy;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailReg, passReg;
    private Button regBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Yaad se activity_sign_up layout banana hoga
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        emailReg = findViewById(R.id.emailReg);
        passReg = findViewById(R.id.passReg);
        regBtn = findViewById(R.id.regBtn);

        regBtn.setOnClickListener(v -> {
            String email = emailReg.getText().toString().trim();
            String pass = passReg.getText().toString().trim();

            if (!email.isEmpty() && pass.length() >= 6) {
                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                                finish(); // Wapis Login screen par jane ke liye
                            } else {
                                Toast.makeText(SignUpActivity.this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(this, "Valid email and 6-char password required", Toast.LENGTH_SHORT).show();
            }
        });
    }
}