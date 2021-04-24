package be.ucll.thirdtimeisacharm;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    private final LiveData<List<WorkOrderEntity>> allUserWorkOrders;
    private final WorkOrderRepository workOrderRepository;
    private WorkOrderDao workOrderDao;
    private UserDao userDao;

    public ViewModel(Application application) {
        super(application);
        workOrderRepository = new WorkOrderRepository(application);
        allUserWorkOrders = workOrderRepository.getAllWorkOrders();
    }

    LiveData<List<WorkOrderEntity>> getAllUsers() {
        return allUserWorkOrders;
    }

    public LiveData<WorkOrderEntity> getDetails(int id) {
        return workOrderRepository.getDetails(id);
    }

    public LiveData<UserEntity> login(String user, String pass) {
        return workOrderRepository.login(user, pass);
    }

    public LiveData<List<WorkOrderEntity>> getWorkOrderUser(String username) {
        return workOrderRepository.getUserWorkOrders(username);
    }

    public void insertUser(UserEntity userEntity) {
        workOrderRepository.insertUser(userEntity);
    }

    public void insertWorkOrder(WorkOrderEntity workOrderEntity) {
        workOrderRepository.insertWorkorder(workOrderEntity);
    }

    public void updateWorkOrder(WorkOrderEntity workOrderEntity) {
        workOrderRepository.updateWorkOrder(workOrderEntity);
    }


}
