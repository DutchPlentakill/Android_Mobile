package be.ucll.thirdtimeisacharm;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "workorders")
public class WorkOrderEntity {

    @PrimaryKey(autoGenerate = true)
    Integer id;

    @ColumnInfo(name = "assignedUser")
    String assignedUser;

    @ColumnInfo(name = "city")
    String city;

    @ColumnInfo(name = "device")
    String device;

    @ColumnInfo(name = "problemCode")
    String problemCode;

    @ColumnInfo(name = "customerName")
    String customerName;

    @ColumnInfo(name = "detailedProblemDescription")
    String detailedProblemDescription;

    @ColumnInfo(name = "repairInformation")
    String repairInformation;

    @ColumnInfo(name = "charged")
    String charged;

    @ColumnInfo(name = "paymentMethod")
    String paymentMethod;

    private Boolean processed = false;

    public WorkOrderEntity(String assignedUser, String device, String problemCode, String customerName, String city, String detailedProblemDescription) {
        this.assignedUser = assignedUser;
        this.device = device;
        this.problemCode = problemCode;
        this.customerName = customerName;
        this.city = city;
        this.detailedProblemDescription = detailedProblemDescription;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCharged() {
        return charged;
    }

    public void setCharged(String charged) {
        this.charged = charged;
    }

    public String getCity() {
        return city;
    }


    public String getAssignedUser() {
        return assignedUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDevice() {
        return device;
    }

    public String getProblemCode() {
        return problemCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public String getDetailedProblemDescription() {
        return detailedProblemDescription;
    }

    public String getRepairInformation() {
        return repairInformation;
    }

    public void setRepairInformation(String repairInformation) {
        this.repairInformation = repairInformation;
    }
}
