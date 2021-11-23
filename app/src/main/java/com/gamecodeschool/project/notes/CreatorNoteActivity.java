package com.gamecodeschool.project.notes;

import androidx.appcompat.app.AppCompatActivity;

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
    SQLiteDatabase database;
    NotesDBHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_note);

        helper = new NotesDBHelper(this);
        database = helper.getWritableDatabase();

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
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, title);
            contentValues.put(NotesContract.NotesEntry.COLUMN_DESCRIPTION, description);
            contentValues.put(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK, dayOfWeek);
            contentValues.put(NotesContract.NotesEntry.COLUMN_PRIOTOTY, priority);
            database.insert(NotesContract.NotesEntry.TABLE_NAME, null, contentValues);
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