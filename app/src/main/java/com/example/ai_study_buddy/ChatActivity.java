package com.example.ai_study_buddy;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ChatActivity extends AppCompatActivity {

    private TextView subjectTitle, aiResponseText;
    private EditText queryInput;
    private Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        subjectTitle = findViewById(R.id.subjectTitle);
        aiResponseText = findViewById(R.id.aiResponseText);
        queryInput = findViewById(R.id.queryInput);
        sendBtn = findViewById(R.id.sendBtn);

        // Dashboard se jo subject name bheja tha woh get karein
        String subject = getIntent().getStringExtra("subject_name");
        subjectTitle.setText("Studying: " + subject);

        sendBtn.setOnClickListener(v -> {
            String question = queryInput.getText().toString();
            if(!question.isEmpty()) {
                // Yahan future mein AI API call hogi
                aiResponseText.setText("Thinking about: " + question + "...");
                queryInput.setText("");
            }
        });
    }
}