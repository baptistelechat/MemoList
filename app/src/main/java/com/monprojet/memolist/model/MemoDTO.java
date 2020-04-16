package com.monprojet.memolist.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

// Create Memo object structure
@Entity(tableName = "memoTable")
public class MemoDTO
{
    @PrimaryKey(autoGenerate = true)
    public long memoId = 0;
    public String name;
    public String description;

    public MemoDTO() {}

    public MemoDTO(String name, String description)
    {
        this.name = name;
        this.description = description;
    }
}
