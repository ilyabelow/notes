package com.github.ilyabelow.notes.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ilyabelow.notes.NoteListAdapter;
import com.github.ilyabelow.notes.NoteRepository;
import com.github.ilyabelow.notes.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("All Notes");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 5);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        NoteListAdapter adapter = new NoteListAdapter(this, NoteRepository.getList(),
                id -> startActivity(ViewNoteActivity.getIntent(this, id))
        );
        recyclerView.setAdapter(adapter);
        NoteRepository.subscribeAdapter(adapter);

        placeListeners();
    }

    private void placeListeners(){
        findViewById(R.id.createNoteButton).setOnClickListener(
                view -> startActivity(new Intent(this, CreateNoteActivity.class))
        );
    }
}