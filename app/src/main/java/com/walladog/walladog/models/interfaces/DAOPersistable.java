package com.walladog.walladog.models.interfaces;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by hadock on 14/12/15.
 *
 */

public interface DAOPersistable<T> {
    long insert(@NonNull T data);
    void update(long id, @NonNull T data);
    void delete(long id);
    void delete(@NonNull T data);
    void deleteAll();
    @Nullable
    Cursor queryCursor();
    T query(long id);
}