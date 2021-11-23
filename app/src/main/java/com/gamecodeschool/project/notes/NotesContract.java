package com.gamecodeschool.project.notes;

import android.provider.BaseColumns;

public class NotesContract {

    public static class NotesEntry implements BaseColumns {
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DAY_OF_WEEK = "dayOfWeek";
        public static final String COLUMN_PRIOTOTY = "priority";

        public static final String TYPE_TEXT = "TEXT";
        public static final String TYPE_INTEGER = "INTEGER";

        public static final String CREATE_TABLE_COMMAND = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                + " (_id " + TYPE_INTEGER +" PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " " + TYPE_TEXT + ", "
                + COLUMN_DESCRIPTION + " " + TYPE_TEXT + ", " + COLUMN_DAY_OF_WEEK + " " + TYPE_INTEGER
                + ", " + COLUMN_PRIOTOTY + " " + TYPE_INTEGER + ")";

        public static final  String DROP_TABLE_COMMAND = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }
}
