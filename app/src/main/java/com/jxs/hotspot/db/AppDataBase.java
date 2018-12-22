package com.jxs.hotspot.db;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = AppDataBase.NAME, version = AppDataBase.VERSION)
public interface AppDataBase {
    /**
     * 数据库名称
     */
    String NAME = "hot-spot-db";
    /**
     * 数据库版本
     */
    int VERSION = 1;
}