package com.cometchat.pro.androiduikit;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * RunningLogDB database class for connecting with device database
 */
@Database(entities = {com.cometchat.pro.androiduikit.RunningLog.class}, version = 1, exportSchema = false)
public abstract class RunningLogDB extends RoomDatabase {
    private static final String DATABASE_NAME = "runninglog_db";
    private static RunningLogDB DBINSTANCE;

    /**
     * RunningLog Data Access Object
     * @return Dao object
     */
    public abstract com.cometchat.pro.androiduikit.RunningLogDao RunningLogDao();

    /**
     * Get database of "runninglog_db"
     * @param context
     * @return database instance
     */
    public static com.cometchat.pro.androiduikit.RunningLogDB getDatabase(Context context) {
        if (DBINSTANCE == null) {
            synchronized (com.cometchat.pro.androiduikit.RunningLogDB.class) {
                DBINSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        RunningLogDB.class, DATABASE_NAME).build();
            }
        }
        return DBINSTANCE;
    }

    /**
     * Destroy database instance
     */
    public static void destroyInstance() {
        DBINSTANCE = null;
    }
}
