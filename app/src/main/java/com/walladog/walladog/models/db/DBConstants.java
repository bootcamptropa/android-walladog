package com.walladog.walladog.models.db;

/**
 * Created by hadock on 2/01/16.
 *
 */

public class DBConstants {

    public static final String DROP_DATABASE = "";

    public static final String TABLE_RACES = "RACES";
    public static final String TABLE_CATEGORIES = "CATEGORIES";
    public static final String TABLE_NOTIFICATIONS = "NOTIFICATIONS";


    // Table field constants
    public static final String KEY_RACES_ID = "_id";
    public static final String KEY_RACES_IDRACE = "id_race";
    public static final String KEY_RACES_NAME = "name";
    public static final String KEY_RACES_CREATION_DATE = "creationDate";
    public static final String KEY_RACES_MODIFICATION_DATE = "modificationDate";

    public static final String KEY_CATEGORIES_ID = "_id";
    public static final String KEY_CATEGORIES_IDCATEGORY = "id_category";
    public static final String KEY_CATEGORIES_NAME = "name";
    public static final String KEY_CATEGORIES_CREATION_DATE = "creationDate";
    public static final String KEY_CATEGORIES_MODIFICATION_DATE = "modificationDate";

    public static final String KEY_NOTIFICATIONS_ID = "_id";
    public static final String KEY_NOTIFICATIONS_TITLE = "title";
    public static final String KEY_NOTIFICATIONS_MESSAGE = "message";
    public static final String KEY_NOTIFICATIONS_AUTHOR = "author";
    public static final String KEY_NOTIFICATIONS_READ = "read";
    public static final String KEY_NOTIFICATIONS_CREATION_DATE = "creationDate";
    public static final String KEY_NOTIFICATIONS_MODIFICATION_DATE = "modificationDate";

    public static final String SQL_CREATE_RACES_TABLE =
            "create table "
                    + TABLE_RACES + "( " + KEY_RACES_ID
                    + " integer primary key autoincrement, "
                    + KEY_RACES_NAME + " text not null unique,"
                    + KEY_RACES_IDRACE + " INTEGER not null unique,"
                    + KEY_RACES_CREATION_DATE + " INTEGER, "
                    + KEY_RACES_MODIFICATION_DATE + " INTEGER "
                    + ");";

    public static final String SQL_CREATE_CATEGORIES_TABLE =
            "create table "
                    + TABLE_CATEGORIES + "( " + KEY_CATEGORIES_ID
                    + " integer primary key autoincrement, "
                    + KEY_CATEGORIES_NAME + " text not null unique,"
                    + KEY_CATEGORIES_IDCATEGORY + " INTEGER not null unique,"
                    + KEY_CATEGORIES_CREATION_DATE + " INTEGER, "
                    + KEY_CATEGORIES_MODIFICATION_DATE + " INTEGER "
                    + ");";

    public static final String SQL_CREATE_NOTIFICATIONS_TABLE =
            "create table "
                    + TABLE_NOTIFICATIONS + "( " + KEY_NOTIFICATIONS_ID
                    + " integer primary key autoincrement, "
                    + KEY_NOTIFICATIONS_TITLE + " text not null,"
                    + KEY_NOTIFICATIONS_MESSAGE + " text not null,"
                    + KEY_NOTIFICATIONS_AUTHOR + " DOUBLE,"
                    + KEY_NOTIFICATIONS_READ + " BOOLEAN,"
                    + KEY_NOTIFICATIONS_CREATION_DATE + " INTEGER, "
                    + KEY_NOTIFICATIONS_MODIFICATION_DATE + " INTEGER "
                    + ");";

    public static final String[] CREATE_DATABASE = {
            SQL_CREATE_RACES_TABLE,
            SQL_CREATE_CATEGORIES_TABLE,
            SQL_CREATE_NOTIFICATIONS_TABLE
    };

}
