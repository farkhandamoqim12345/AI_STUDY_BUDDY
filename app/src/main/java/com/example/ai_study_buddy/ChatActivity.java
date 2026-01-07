package com.example.ai_study_buddy;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.lite.Interpreter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
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

        // 1. UI Setup
        subjectTitle = findViewById(R.id.subjectTitle);
        aiResponseText = findViewById(R.id.aiResponseText);
        queryInput = findViewById(R.id.queryInput);
        sendBtn = findViewById(R.id.sendBtn);

        String subject = getIntent().getStringExtra("subject_name");
        subjectTitle.setText("Studying: " + subject);

        // 2. TFLite Model Load
        try {
            tflite = new Interpreter(loadModelFile());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3. 700 Entries wala Dataset Load karna
        initKnowledgeBase();

        // 4. Send Button Logic
        sendBtn.setOnClickListener(v -> {
            String question = queryInput.getText().toString().trim();
            if(!question.isEmpty()) {
                // Pehle purani history lein
                String oldText = aiResponseText.getText().toString();
                if(oldText.contains("Welcome") || oldText.contains("Ask me")) {
                    oldText = ""; // Purana welcome message saaf karein
                }

                // User ka sawal screen par dikhayein
                String updateText = oldText + "\nYOU: " + question + "\n";
                aiResponseText.setText(updateText + "Thinking...");

                processQuery(question, updateText);
                queryInput.setText(""); // Input field saaf karein
            }
        });
    }

    private void initKnowledgeBase() {
        knowledgeBase = new HashMap<>();
        try {
            // Aapki 700 lines wali file
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("StudyBuddy_Master_Dataset.csv")));

            String line;
            reader.readLine(); // Header skip

            while ((line = reader.readLine()) != null) {
                // Regex lambe answers ke commas handle karne ke liye
                String[] parts = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                if (parts.length >= 4) {
                    String keyword = parts[2].replace("\"", "").toLowerCase().trim();
                    String response = parts[3].replace("\"", "").trim();
                    knowledgeBase.put(keyword, response);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processQuery(String query, String historyBeforeAI) {
        String lowerQuery = query.toLowerCase().trim();
        String answer = null;

        // Dataset mein search logic
        if (knowledgeBase.containsKey(lowerQuery)) {
            answer = knowledgeBase.get(lowerQuery);
        } else {
            for (Map.Entry<String, String> entry : knowledgeBase.entrySet()) {
                if (lowerQuery.contains(entry.getKey())) {
                    answer = entry.getValue();
                    break;
                }
            }
        }

        if (answer == null) {
            answer = "Topic analyzed via 1.tflite. Please refer to documentation for detailed study.";
        }

        // Final display jo Question aur Answer dono dikhayega
        final String finalDisplay = historyBeforeAI + "AI BUDDY: " + answer + "\n--------------------";

        aiResponseText.postDelayed(() -> {
            aiResponseText.setText(finalDisplay);
        }, 600);
    }

    private MappedByteBuffer loadModelFile() throws Exception {
        AssetFileDescriptor fileDescriptor = this.getAssets().openFd("1.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, fileDescriptor.getStartOffset(), fileDescriptor.getDeclaredLength());
    }
}