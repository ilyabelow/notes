package com.github.ilyabelow.notes.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.github.ilyabelow.notes.NoteRepository;
import com.github.ilyabelow.notes.R;

import java.util.Calendar;

public class CreateNoteActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText textEditText;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        setTitle("Create note");

        titleEditText = findViewById(R.id.titleEditText);
        textEditText = findViewById(R.id.textEditText);
        radioGroup = findViewById(R.id.radioGroup);

        placeListeners();
    }

    private void placeListeners() {
        findViewById(R.id.cancelButton).setOnClickListener(view -> {
            finish();
        });

        findViewById(R.id.saveButton).setOnClickListener(view -> {
            addNote();
            finish();
        });
    }

    private void addNote(){
        int clicked = 0;
        int id = radioGroup.getCheckedRadioButtonId();
        switch (id){
            case R.id.radio0: clicked = 0; break;
            case R.id.radio1: clicked = 1; break;
            case R.id.radio2: clicked = 2; break;
            case R.id.radio3: clicked = 3; break;
            case R.id.radio4: clicked = 4; break;
            case R.id.radio5: clicked = 5; break;
        }
        NoteRepository.addNote(titleEditText.getText().toString(),
                textEditText.getText().toString(),
                Calendar.getInstance().getTime(),
                clicked );
        NoteRepository.push(this);
    }
}