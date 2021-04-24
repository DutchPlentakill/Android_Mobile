package be.ucll.thirdtimeisacharm;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UserRepository {
    private final UserDao userDao;
    private final LiveData<List<UserEntity>> allUsers;
    private WorkOrderDao workOrderDao;
    private LiveData<List<WorkOrderEntity>> allWorkOrders;

    public UserRepository(Application application) {
        DatabaseRoom databaseRoom = DatabaseRoom.getDatabase(application);
        userDao = databaseRoom.userDao();
        allUsers = userDao.getUsers();
    }

    LiveData<List<UserEntity>> getAllUsers() {
        return allUsers;
    }

    void insert(UserEntity userEntity) {
        DatabaseRoom.databaseWriteExecutor.execute(() -> {
            userDao.insert(userEntity);
        });
    }

    LiveData<UserEntity> login(String username, String password) {
        return userDao.login(username, password);
    }

}
