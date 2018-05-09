package pe.com.mastermachines.probando;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import pe.com.mastermachines.probando.data.entity.UserEntity;
import pe.com.mastermachines.probando.data.repository.UserRepository;
import pe.com.mastermachines.probando.data.view.UserView;

public class InsertUser extends AppCompatActivity implements View.OnClickListener{

    TextInputEditText name,fullname;
    Button save,view;
    public static String EXTRA_NAME = "";
    public static String EXTRA_FULLNAME = "";
    UserRepository userRepository ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_user);

        userRepository = new UserRepository(getApplication());

        name = (TextInputEditText) findViewById(R.id.txt_name);
        fullname = (TextInputEditText) findViewById(R.id.txt_fullname);

        save = (Button) findViewById(R.id.btn_save);
        view = (Button) findViewById(R.id.btn_view);

        save.setOnClickListener(this);
        view.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Intent intent;
        Intent replyIntent = new Intent();
        switch (v.getId()){
            case R.id.btn_save:
                EXTRA_NAME = name.getText().toString();
                EXTRA_FULLNAME = fullname.getText().toString();
                if(TextUtils.isEmpty(name.getText())){
                    name.setError("Debe colocar su nombre");
                    name.requestFocus();
                }else if(TextUtils.isEmpty(fullname.getText())){
                    fullname.setError("Debe colocar su apellido");
                    fullname.requestFocus();
                }else{
                    UserEntity userEntity = new UserEntity(EXTRA_NAME,EXTRA_FULLNAME);
                    userRepository.insert(userEntity);
                    Log.e("name",EXTRA_NAME);
                    Log.e("fullname",EXTRA_FULLNAME);
                    intent = new Intent(this,ViewUser.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.btn_view:
                intent = new Intent(this,ViewUser.class);
                startActivity(intent);
                finish();
                break;
        }

    }
}
