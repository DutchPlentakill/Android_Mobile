package be.ucll.thirdtimeisacharm;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWorkOrder {
    @Embedded
    public UserEntity user;
    @Relation(
            parentColumn = "userId",
            entityColumn = "users"
    )
    public List<WorkOrderEntity> workOrderList;
}
