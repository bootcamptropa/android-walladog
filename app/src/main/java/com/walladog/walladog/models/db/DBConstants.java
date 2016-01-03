package com.walladog.walladog.models.db;

/**
 * Created by hadock on 2/01/16.
 *
 */

public class DBConstants {

    public static final String DROP_DATABASE = "";

    public static final String TABLE_RACES = "RACES";
    public static final String TABLE_CATEGORIES = "CATEGORIES";


    // Table field constants
    public static final String KEY_RACES_ID = "_id";
    public static final String KEY_RACES_NAME = "name";
    public static final String KEY_RACES_CREATION_DATE = "creationDate";
    public static final String KEY_RACES_MODIFICATION_DATE = "modificationDate";

    public static final String KEY_CATEGORIES_ID = "_id";
    public static final String KEY_CATEGORIES_NAME = "name";
    public static final String KEY_CATEGORIES_CREATION_DATE = "creationDate";
    public static final String KEY_CATEGORIES_MODIFICATION_DATE = "modificationDate";


    public static final String SQL_CREATE_RACES_TABLE =
            "create table "
                    + TABLE_RACES + "( " + KEY_RACES_ID
                    + " integer primary key autoincrement, "
                    + KEY_RACES_NAME + " text not null unique,"
                    + KEY_RACES_CREATION_DATE + " INTEGER, "
                    + KEY_RACES_MODIFICATION_DATE + " INTEGER "
                    + ");";

    public static final String SQL_CREATE_CATEGORIES_TABLE =
            "create table "
                    + TABLE_CATEGORIES + "( " + KEY_CATEGORIES_ID
                    + " integer primary key autoincrement, "
                    + KEY_CATEGORIES_NAME + " text not null unique,"
                    + KEY_CATEGORIES_CREATION_DATE + " INTEGER, "
                    + KEY_CATEGORIES_MODIFICATION_DATE + " INTEGER "
                    + ");";

    public static final String[] CREATE_DATABASE = {
            SQL_CREATE_RACES_TABLE,
            SQL_CREATE_CATEGORIES_TABLE
    };

}
