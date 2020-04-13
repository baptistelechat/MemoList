package com.monprojet.memolist.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Create Memo object structure
@Entity(tableName = "memoTable")
public class MemoDTO
{
    @PrimaryKey(autoGenerate = true)
    public long memoId = 0;
    public String name;

    public MemoDTO() {}

    public MemoDTO(String name)
    {
        this.name = name;
    }
}
