package be.ucll.thirdtimeisacharm;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WorkOrderDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(WorkOrderEntity workOrderEntity);

    @Query("SELECT * FROM workorders")
    LiveData<List<WorkOrderEntity>> getAllWorkOrders();

    @Query("SELECT * FROM workorders WHERE assignedUser=(:userEntity)")
    LiveData<List<WorkOrderEntity>> getOrders(String userEntity);

    @Query("SELECT * FROM workorders WHERE id=(:id)")
    LiveData<WorkOrderEntity> getDetails(int id);

    @Update
    void updateWorkOrder(WorkOrderEntity workOrderEntity);


}


