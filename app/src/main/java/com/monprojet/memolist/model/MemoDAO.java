package com.monprojet.memolist.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class MemoDAO {
    @Query("SELECT * FROM memoTable")
    public abstract List<MemoDTO> getListeMemo();

    @Query("SELECT COUNT(*) FROM memoTable WHERE name = :name")
    public abstract long countMemoParName(String name);

    @Insert
    public abstract void insert(MemoDTO... memo);

    @Update
    public abstract void update(MemoDTO... memo);

    @Delete
    public abstract void delete(MemoDTO... memo);
}
