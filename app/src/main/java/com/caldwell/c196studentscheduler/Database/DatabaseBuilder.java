package com.caldwell.c196studentscheduler.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.caldwell.c196studentscheduler.DAO.CourseDAO;
import com.caldwell.c196studentscheduler.DAO.TermDAO;
import com.caldwell.c196studentscheduler.Entity.Course;
import com.caldwell.c196studentscheduler.Entity.Term;

@Database(entities = {Term.class, Course.class}, version=3, exportSchema = false)
public abstract class DatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();

    private static volatile DatabaseBuilder INSTANCE;

    static DatabaseBuilder getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (DatabaseBuilder.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseBuilder.class, "scheduleDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
