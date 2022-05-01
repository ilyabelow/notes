package com.github.ilyabelow.notes.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.ilyabelow.notes.Note;
import com.github.ilyabelow.notes.NoteRepository;
import com.github.ilyabelow.notes.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ViewNoteActivity extends AppCompatActivity {

    private static final String ID_ARG_KEY = "ID_ARG_KEY";
    public static final String DELETED_POS_KEY = "DELETED_POS_KEY";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        int noteId = getIntent().getIntExtra(ID_ARG_KEY, 0);

        // Populating activity with text and stuff
        Note n = NoteRepository.getNote(noteId);
        setTitle(n.getTitle());
        TextView text = findViewById(R.id.viewText);
        text.setText(n.getText());
        TextView date = findViewById(R.id.viewDate);
        date.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(n.getDate()));
        TextView time = findViewById(R.id.viewTime);
        time.setText(new SimpleDateFormat("hh:mm:ss", Locale.US).format(n.getDate()));

        // Listeners init

        // TODO ask conformation
        findViewById(R.id.backButton).setOnClickListener(view -> finish());

        // TODO ask conformation
        findViewById(R.id.deleteButton).setOnClickListener(view -> {
            NoteRepository.removeNote(noteId);
            finish();
        });

        findViewById(R.id.editButton).setOnClickListener(
                view -> {
                    startActivity(EditNoteActivity.getIntent(this, noteId));
                    finish();
                }
        );
    }

    public static Intent getIntent(final Context context, @NonNull final int id) {
        final Intent intent = new Intent(context, ViewNoteActivity.class);
        intent.putExtra(ID_ARG_KEY, id);
        return intent;
    }
}
