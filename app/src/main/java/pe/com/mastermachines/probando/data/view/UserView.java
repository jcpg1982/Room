package pe.com.mastermachines.probando.data.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import pe.com.mastermachines.probando.data.entity.UserEntity;
import pe.com.mastermachines.probando.data.repository.UserRepository;

/**
 * @author Anonymous on 9/05/2018.
 * @version 1.0
 */
public class UserView extends AndroidViewModel {

    private UserRepository mRepository;

    private LiveData<List<UserEntity>> mAllWords;

    public UserView (Application application) {
        super(application);
        mRepository = new UserRepository(application);
        mAllWords = mRepository.getAllUsers();
    }

    public LiveData<List<UserEntity>> getAllUser() { return mAllWords; }

    public void insert(UserEntity userEntity) { mRepository.insert(userEntity); }
}