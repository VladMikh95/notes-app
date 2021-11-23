package com.gamecodeschool.project.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private final ArrayList<Note> notes = new ArrayList<>();
    private SQLiteDatabase database;
    private NotesDBHelper helper;
    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        helper = new NotesDBHelper(this);
        database = helper.getWritableDatabase();
        /*
        if (notes.isEmpty()) {
            setData();
        }
        for (Note note : notes) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, note.getTitle());
            contentValues.put(NotesContract.NotesEntry.COLUMN_DESCRIPTION, note.getDescription());
            contentValues.put(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK, note.getDayOfWeek());
            contentValues.put(NotesContract.NotesEntry.COLUMN_PRIOTOTY, note.getPriority());
            database.insert(NotesContract.NotesEntry.TABLE_NAME, null, contentValues);
        }*/
        getData();
        adapter = new NoteAdapter(notes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
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

    /*
    private void setData() {
        notes.add(new Note("Парикмахерская", "Постричься", "Четверг", 1));
        notes.add(new Note("Строительный магазин", "Купить вязальную проволоку сечением 3мм", "Вторник", 2));
        notes.add(new Note("Новая скульптура", "Посмотреть в интернете идеи скульптур", "Понедельник", 3));
        notes.add(new Note("Уборка", "Навести порядок в квартире", "Среда", 1));
        notes.add(new Note("Регистрация в facebook", "Зарегистрироваться в facebook", "Вторник", 2));
        notes.add(new Note("Побриться", "Побриться", "Вторник", 2));
        notes.add(new Note("Инвестиции", "Прочитать статью в интернете про инвестиции", "Среда", 3));
        notes.add(new Note("Корм для попугаев", "Купить корм для попугаев", "Вторник", 2));
    }*/

    public void onClickCreateNote(View view) {
        Intent intent = new Intent(this, CreatorNoteActivity.class);
        startActivity(intent);
    }

    private void remove(int position) {
        int id = notes.get(position).getId();
        String where = NotesContract.NotesEntry._ID  + " = ?";
        String[] whereArgs = new String[]{Integer.toString(id)};
        database.delete(NotesContract.NotesEntry.TABLE_NAME, where, whereArgs);
        getData();
        adapter.notifyDataSetChanged();
    }

    private void getData() {
        notes.clear();
        Cursor cursor = database.query(NotesContract.NotesEntry.TABLE_NAME, null, null, null, null, null,NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry._ID));
            String title = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DESCRIPTION));
            int dayOfWeek = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK));
            int priority = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_PRIOTOTY));
            notes.add(new Note(id, title, description, dayOfWeek, priority));
        }
        cursor.close();
    }
}