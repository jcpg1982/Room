package pe.com.mastermachines.probando.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * @author Anonymous on 9/05/2018.
 * @version 1.0
 */
@Entity(tableName = "user")
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idUser")
    private
    int idUser;

    @ColumnInfo(name = "name")
    private
    String name;

    @ColumnInfo(name = "fullname")
    private
    String fullname;

    public UserEntity(@NonNull String name,@NonNull String fullname) {
        this.name = name;
        this.fullname = fullname;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public String toString() {
        return this.getName()+" "+this.getFullname();
    }
}
