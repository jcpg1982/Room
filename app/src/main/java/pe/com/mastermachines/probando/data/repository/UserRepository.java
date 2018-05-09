package pe.com.mastermachines.probando.data.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import pe.com.mastermachines.probando.data.AppDatabase;
import pe.com.mastermachines.probando.data.dao.UserDao;
import pe.com.mastermachines.probando.data.entity.UserEntity;

/**
 * @author Anonymous on 9/05/2018.
 * @version 1.0
 */
public class UserRepository {
    private UserDao muserDao;
    private LiveData<List<UserEntity>> mAllUser;
    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        muserDao = db.userDao();
        mAllUser = muserDao.getAllUser();
    }

    public LiveData<List<UserEntity>> getAllUsers() {
        return mAllUser;
    }

    public void insert (UserEntity userEntity) {
        new insertAsyncTask(muserDao).execute(userEntity);
    }

    public void deleteAll () {
        new deleteAsyncTask(muserDao).execute();
    }

    private static class deleteAsyncTask extends AsyncTask<UserEntity, Void, Void> {

        private UserDao mAsyncTaskDao;

        deleteAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final UserEntity... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class insertAsyncTask extends AsyncTask<UserEntity, Void, Void> {

        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final UserEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
