package be.ucll.thirdtimeisacharm;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WorkOrderRepository {
    private final UserDao userDao;
    private final WorkOrderDao workOrderDao;
    private final LiveData<List<WorkOrderEntity>> allWorkOrders;

    WorkOrderRepository(Application application) {
        DatabaseRoom databaseRoom = DatabaseRoom.getDatabase(application);
        userDao = databaseRoom.userDao();
        workOrderDao = databaseRoom.workOrderDao();
        allWorkOrders = workOrderDao.getAllWorkOrders();
    }

    LiveData<List<WorkOrderEntity>> getAllWorkOrders() {
        return allWorkOrders;
    }

    LiveData<UserEntity> login(String userId, String password) {
        return userDao.login(userId, password);
    }

    LiveData<List<WorkOrderEntity>> getUserWorkOrders(String userEntity) {
        return workOrderDao.getOrders(userEntity);
    }

    LiveData<WorkOrderEntity> getDetails(int id) {
        return workOrderDao.getDetails(id);
    }

    LiveData<List<WorkOrderEntity>> getWorkOrders(String user) {
        return workOrderDao.getOrders(user);
    }

    public void insertUser(UserEntity userEntity) {
        DatabaseRoom.databaseWriteExecutor.execute(() -> {
            userDao.insert(userEntity);
        });
    }

    public void insertWorkorder(WorkOrderEntity workOrder) {
        DatabaseRoom.databaseWriteExecutor.execute(() -> {
            workOrderDao.insert(workOrder);
        });
    }

    public void updateWorkOrder(WorkOrderEntity workOrderEntity) {
        DatabaseRoom.databaseWriteExecutor.execute(() -> {
            workOrderDao.updateWorkOrder(workOrderEntity);
        });
    }


}
