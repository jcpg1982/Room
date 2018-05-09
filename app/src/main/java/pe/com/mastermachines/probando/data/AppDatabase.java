package pe.com.mastermachines.probando.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import pe.com.mastermachines.probando.R;
import pe.com.mastermachines.probando.data.dao.UserDao;
import pe.com.mastermachines.probando.data.entity.UserEntity;

/**
 * @author Anonymous on 9/05/2018.
 * @version 1.0
 */
@Database(entities = {UserEntity.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    static String DATABASE = String.valueOf(R.string.database);
    public abstract UserDao userDao();
    private static AppDatabase INSTANCE;
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
