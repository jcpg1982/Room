package pe.com.mastermachines.probando.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import pe.com.mastermachines.probando.data.entity.UserEntity;

/**
 * @author Anonymous on 9/05/2018.
 * @version 1.0
 */
@Dao
public interface UserDao {

    //probando git con benito -- Benito estuvo aqui !!
    @Insert
    void insert(UserEntity userEntity);

    @Query("DELETE FROM user")
    void deleteAll();

    @Query("SELECT * from user")
    LiveData<List<UserEntity>> getAllUser();
}
