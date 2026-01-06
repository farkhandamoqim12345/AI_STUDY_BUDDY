package com.example.ai_study_buddy;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private TextView subjectTitle, aiResponseText;
    private EditText queryInput;
    private Button sendBtn;
    private Interpreter tflite;
    private Map<String, String> knowledgeBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // UI Setup
        subjectTitle = findViewById(R.id.subjectTitle);
        aiResponseText = findViewById(R.id.aiResponseText);
        queryInput = findViewById(R.id.queryInput);
        sendBtn = findViewById(R.id.sendBtn);

        // Subject Name get karna
        String subject = getIntent().getStringExtra("subject_name");
        subjectTitle.setText("Studying: " + subject);

        // 1. TFLite Model Load karna
        try {
            tflite = new Interpreter(loadModelFile());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Knowledge Base "Training" (Adding more questions)
        initKnowledgeBase();

        sendBtn.setOnClickListener(v -> {
            String question = queryInput.getText().toString().toLowerCase().trim();
            if(!question.isEmpty()) {
                aiResponseText.setText("Thinking about: " + question + "...");
                processQuery(question);
                queryInput.setText("");
            }
        });
    }

    private void initKnowledgeBase() {
        knowledgeBase = new HashMap<>();
        // Android Development Questions
        knowledgeBase.put("activity", "An Activity is a single, focused thing that the user can do, represented by a screen.");
        knowledgeBase.put("fragment", "A Fragment represents a reusable portion of your app's UI.");
        knowledgeBase.put("intent", "An Intent is a messaging object you can use to request an action from another component.");
        knowledgeBase.put("lifecycle", "Lifecycle states: onCreate, onStart, onResume, onPause, onStop, onDestroy.");

        // Database & Firebase
        knowledgeBase.put("firebase", "Firebase is Google's mobile platform for authentication, database, and cloud storage.");
        knowledgeBase.put("sql", "SQL is a standard language for accessing and manipulating databases.");
        knowledgeBase.put("nosql", "NoSQL (like Firestore) stores data in documents instead of tables.");

        // Software Engineering
        knowledgeBase.put("sdlc", "SDLC stands for Software Development Life Cycle (Planning, Analysis, Design, Testing).");
        knowledgeBase.put("agile", "Agile is an iterative approach to software development and project management.");
    }

    private void processQuery(String query) {
        String answer = "AI Buddy: Based on our 1.tflite analysis, this is an important topic. Let's research more!";

        // Match query with Knowledge Base
        for (String key : knowledgeBase.keySet()) {
            if (query.contains(key)) {
                answer = "AI Buddy: " + knowledgeBase.get(key);
                break;
            }
        }

        // Final Display with small delay
        final String finalAnswer = answer;
        aiResponseText.postDelayed(() -> aiResponseText.setText(finalAnswer), 700);
    }

    private MappedByteBuffer loadModelFile() throws Exception {
        AssetFileDescriptor fileDescriptor = this.getAssets().openFd("1.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, fileDescriptor.getStartOffset(), fileDescriptor.getDeclaredLength());
    }
}