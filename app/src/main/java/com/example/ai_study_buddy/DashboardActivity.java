package com.example.ai_study_buddy; // Path check karo, underscore lazmi hai agar folder name yehi hai

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> subjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // 1. Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 2. Dummy Data for List
        subjects = Arrays.asList("Android Development", "Artificial Intelligence", "Database Systems", "Machine Learning");

        // 3. Adapter set karna
        // Humne interface add kiya hai taaki click karne par ChatActivity khule
        SubjectAdapter adapter = new SubjectAdapter(subjects, subject -> {
            Intent intent = new Intent(DashboardActivity.this, ChatActivity.class);
            intent.putExtra("subject_name", subject);
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }
}