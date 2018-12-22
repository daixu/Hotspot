package com.jxs.hotspot.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDataBase.class)
public class SearchModel extends BaseModel {

    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    public String name;

}