package com.gamecodeschool.project.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.RoomDatabase;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class CreatorNoteActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private Spinner spinnerDayOfWeek;
    private RadioGroup radioGroupPriority;
    private MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_note);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        editTextTitle = findViewById(R.id.editTextTextTitle);
        editTextDescription = findViewById(R.id.editTextTextDescription);
        spinnerDayOfWeek = findViewById(R.id.spinnerDayOfWeek);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);
    }

    public void createNote(View view) {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int dayOfWeek = spinnerDayOfWeek.getSelectedItemPosition() + 1;
        int radioButtonId = radioGroupPriority.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioButtonId);
        String priorityString = radioButton.getText().toString();
        int priority = Integer.parseInt(priorityString);
        if (isFilled(title, description)) {
            Note note = new Note(title, description, dayOfWeek, priority);
            viewModel.insertNote(note);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.warning_fill_fields, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isFilled(String title, String description) {
        return !title.isEmpty() && !description.isEmpty();
    }
}