package be.ucll.thirdtimeisacharm;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(UserEntity userEntity);

    @Query("SELECT * FROM users")
    LiveData<List<UserEntity>> getUsers();

    @Query("SELECT * FROM users where userId =(:userId) and password =(:password)")
    LiveData<UserEntity> login(String userId, String password);
}
