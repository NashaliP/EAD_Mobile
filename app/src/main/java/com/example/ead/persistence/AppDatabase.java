package com.example.ead.persistence;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {CartItem.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    // Provides access to the CartDao for database operations
    public abstract CartDao cartDao();

    private static volatile AppDatabase INSTANCE;

    // Returns the singleton instance of the database
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "cart_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}