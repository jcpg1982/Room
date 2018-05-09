package pe.com.mastermachines.probando;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import pe.com.mastermachines.probando.data.adapter.UserAdapter;
import pe.com.mastermachines.probando.data.entity.UserEntity;
import pe.com.mastermachines.probando.data.repository.UserRepository;
import pe.com.mastermachines.probando.data.view.UserView;

public class ViewUser extends AppCompatActivity implements View.OnClickListener{

    private UserView mUserView;
    private Button delete;
    UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        delete = (Button) findViewById(R.id.btn_delete);
        userRepository = new UserRepository(getApplication());
        final UserAdapter adapter = new UserAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Get a new or existing ViewModel from the ViewModelProvider.
        mUserView = ViewModelProviders.of(this).get(UserView.class);
        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mUserView.getAllUser().observe(this, new Observer<List<UserEntity>>() {
            @Override
            public void onChanged(@Nullable final List<UserEntity> userEntities) {
                // Update the cached copy of the words in the adapter.
                adapter.setUsers(userEntities);
            }
        });

        delete.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,InsertUser.class);
        startActivity(intent);
        finish();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_delete:
                userRepository.deleteAll();
                break;
        }

    }
}
