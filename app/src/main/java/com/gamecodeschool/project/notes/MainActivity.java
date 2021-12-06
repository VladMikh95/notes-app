package com.gamecodeschool.project.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private final ArrayList<Note> notes = new ArrayList<>();
    private NoteAdapter adapter;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new NoteAdapter(notes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        getData();
        adapter.setOnNoteClickListener(new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
                Toast toast = Toast.makeText(MainActivity.this, R.string.text_click_element, Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onNoteLongClick(int position) {
                remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                remove(viewHolder.getAdapterPosition());
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void onClickCreateNote(View view) {
        Intent intent = new Intent(this, CreatorNoteActivity.class);
        startActivity(intent);
    }

    private void remove(int position) {
        Note note = adapter.getNotes().get(position);
        viewModel.deleteNote(note);
    }

    private void getData() {
        LiveData<List<Note>> notesDB = viewModel.getNotes();
        notesDB.observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notesFromLiveData) {
                adapter.setNotes(notesFromLiveData);
            }
        });
    }


}