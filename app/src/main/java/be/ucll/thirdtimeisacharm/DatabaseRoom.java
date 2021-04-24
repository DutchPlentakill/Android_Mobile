package be.ucll.thirdtimeisacharm;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities =
        {UserEntity.class,
                WorkOrderEntity.class},
        version = 1,
        exportSchema = false)
public abstract class DatabaseRoom extends androidx.room.RoomDatabase {
    static final ExecutorService databaseWriteExecutor =
            Executors.newSingleThreadExecutor();

    private static DatabaseRoom INSTANCE;

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                UserDao userDao = INSTANCE.userDao();
                WorkOrderDao workOrderDao = INSTANCE.workOrderDao();
                UserEntity userEntity = new UserEntity("alanjan", "alanjan", "alan", "jan");
                userDao.insert(userEntity);
                WorkOrderEntity workOrderEntity1 = new WorkOrderEntity("Alan", "iPhone 6", "Charging port", "Tom", "Brussels", "Charging port not functioning correctly, only charges in certain position.");
                WorkOrderEntity workOrderEntity2 = new WorkOrderEntity("Alan", "OnePlus 5T", "Touchscreen", "Henk", "Leuven", "Touchscreen not functioning correctly, right upper corner does not react to input.");
                WorkOrderEntity workOrderEntity3 = new WorkOrderEntity("Alan", "iPhone 7+", "Main speaker", "Femke", "Genk", "MainSpeaker not functioning correctly, makes weird noises when playing music.");
                WorkOrderEntity workOrderEntity4 = new WorkOrderEntity("Alan", "Galaxy S10", "Ear speaker", "Nick", "Aarschot", "EarSpeaker not functioning correctly, can't hear people when calling, only on loudspeaker.");
                WorkOrderEntity workOrderEntity5 = new WorkOrderEntity("Alan", "Huawei P20", "Battery", "Bianca", "Brugge", "Battery drains to quickly, falls out at 20%.");
                workOrderDao.insert(workOrderEntity1);
                workOrderDao.insert(workOrderEntity2);
                workOrderDao.insert(workOrderEntity3);
                workOrderDao.insert(workOrderEntity4);
                workOrderDao.insert(workOrderEntity5);
            });
        }
    };

    static DatabaseRoom getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseRoom.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(),
                            DatabaseRoom.class)
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract UserDao userDao();

    public abstract WorkOrderDao workOrderDao();
}
