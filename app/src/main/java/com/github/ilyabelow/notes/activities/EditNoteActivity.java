package com.github.ilyabelow.notes.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.ilyabelow.notes.Note;
import com.github.ilyabelow.notes.NoteRepository;
import com.github.ilyabelow.notes.R;

import java.util.Calendar;

public class EditNoteActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText textEditText;
    private RadioGroup radioGroup;

    private static final String ID_ARG_KEY = "ID_ARG_KEY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        titleEditText = findViewById(R.id.titleEditText);
        textEditText = findViewById(R.id.textEditText);
        radioGroup = findViewById(R.id.radioGroup);

        int noteId = getIntent().getIntExtra(ID_ARG_KEY, 0);
        prepareViews(noteId);
        placeListeners(noteId);
    }

    private void placeListeners(int noteId) {
        findViewById(R.id.saveButton).setOnClickListener(view -> {
            editNote(noteId);
            // recreating here so changes in the note will be seen in View activity
            startActivity(ViewNoteActivity.getIntent(this, noteId));
            finish();
        });

        findViewById(R.id.cancelButton).setOnClickListener(view -> {
            startActivity(ViewNoteActivity.getIntent(this, noteId));
            finish();
        });
    }

    private void prepareViews(int noteId) {
        Note n = NoteRepository.getNote(noteId);

        titleEditText.setText(n.getTitle());
        textEditText.setText(n.getText());

        int radioId = 0;
        switch (n.getColor()) {
            case 0:
                radioId = R.id.radio0;
                break;
            case 1:
                radioId = R.id.radio1;
                break;
            case 2:
                radioId = R.id.radio2;
                break;
            case 3:
                radioId = R.id.radio3;
                break;
            case 4:
                radioId = R.id.radio4;
                break;
            case 5:
                radioId = R.id.radio5;
                break;
        }
        RadioButton button = findViewById(radioId);
        button.toggle();

    }

    private void editNote(int noteId){
        int clicked = 0;
        int id = radioGroup.getCheckedRadioButtonId();
        switch (id){
            // warning here, how else?
            case R.id.radio0: clicked = 0; break;
            case R.id.radio1: clicked = 1; break;
            case R.id.radio2: clicked = 2; break;
            case R.id.radio3: clicked = 3; break;
            case R.id.radio4: clicked = 4; break;
            case R.id.radio5: clicked = 5; break;
        }
        NoteRepository.editNote(noteId, titleEditText.getText().toString(),
                textEditText.getText().toString(),
                Calendar.getInstance().getTime(),
                clicked );
        NoteRepository.push(this);
    }

    public static Intent getIntent(final Context context, final int id) {
        final Intent intent = new Intent(context, EditNoteActivity.class);
        intent.putExtra(ID_ARG_KEY, id);
        return intent;
    }
}
