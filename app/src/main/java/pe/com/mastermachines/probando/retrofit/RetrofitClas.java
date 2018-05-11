package pe.com.mastermachines.probando.retrofit;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import pe.com.mastermachines.probando.R;
import pe.com.mastermachines.probando.retrofit.Dto.UserDTO;
import pe.com.mastermachines.probando.retrofit.apiAdapter.UserApiAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClas extends AppCompatActivity {

    private static String TAG_RETROFIT_GET_POST = RetrofitClas.class.getSimpleName();

    private EditText userNameEditText = null;
    private EditText passwordEditText = null;
    private EditText emailEditText = null;
    private Button registerButton = null;
    private Button getAllUserButton = null;
    private Button getUserByNameButton = null;
    private ListView userListView = null;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_clas);

        initControls();

        /* When register user account button is clicked. */
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userNameValue = userNameEditText.getText().toString();
                String passwordValue = passwordEditText.getText().toString();
                String emailValue = emailEditText.getText().toString();
                if(TextUtils.isEmpty(userNameEditText.getText())){
                    userNameEditText.setError("Debe colocar su nombre");
                    userNameEditText.requestFocus();
                }else if(TextUtils.isEmpty(passwordEditText.getText())){
                    passwordEditText.setError("Debe colocar su password");
                    passwordEditText.requestFocus();
                }else if(TextUtils.isEmpty(emailEditText.getText())){
                    emailEditText.setError("Debe colocar su correo");
                    emailEditText.requestFocus();
                }else{
                    // Use default converter factory, so parse response body text to okhttp3.ResponseBody object.
                    Call<ResponseBody> call = UserApiAdapter.getUserApiService(null).registerUser(userNameValue, passwordValue, emailValue);
                    // Create a Callback object, because we do not set JSON converter, so the return object is ResponseBody be default.
                    retrofit2.Callback<ResponseBody> callback = new Callback<ResponseBody>(){
                        /* When server response. */
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            hideProgressDialog();
                            StringBuffer messageBuffer = new StringBuffer();
                            int statusCode = response.code();
                            if(statusCode == 200){
                                try {
                                    // Get return string.
                                    String returnBodyText = response.body().string();
                                    // Because return text is a json format string, so we should parse it manually.
                                    Gson gson = new Gson();
                                    TypeToken<List<RegisterResponse>> typeToken = new TypeToken<List<RegisterResponse>>(){};
                                    // Get the response data list from JSON string.
                                    List<RegisterResponse> registerResponseList = gson.fromJson(returnBodyText, typeToken.getType());
                                    if(registerResponseList!=null && registerResponseList.size() > 0) {
                                        RegisterResponse registerResponse = registerResponseList.get(0);
                                        if (registerResponse.isSuccess()) {
                                            messageBuffer.append(registerResponse.getMessage());
                                        } else {
                                            messageBuffer.append("User register failed.");
                                        }
                                    }
                                }catch(IOException ex){
                                    Log.e(TAG_RETROFIT_GET_POST, ex.getMessage());
                                }
                            }else{
                                // If server return error.
                                messageBuffer.append("Server return error code is ");
                                messageBuffer.append(statusCode);
                                messageBuffer.append("\r\n\r\n");
                                messageBuffer.append("Error message is ");
                                messageBuffer.append(response.message());
                            }
                            // Show a Toast message.
                            Toast.makeText(getApplicationContext(), messageBuffer.toString(), Toast.LENGTH_LONG).show();
                            Log.e(TAG_RETROFIT_GET_POST, messageBuffer.toString());
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            hideProgressDialog();
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e(TAG_RETROFIT_GET_POST, t.getMessage());
                        }
                    };
                    // Use callback object to process web server return data in asynchronous mode.
                    call.enqueue(callback);
                }
            }
        });
        /* When get user by name button is clicked. */
        getUserByNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                String userNameValue = userNameEditText.getText().toString();
                // Create Gson converter to convert returned JSON string..
                GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();
                // Get call object.
                Call<UserDTO> call = UserApiAdapter.getUserApiService(gsonConverterFactory).getUserByName(userNameValue);
                // Create a Callback object.
                retrofit2.Callback<UserDTO> callback = new Callback<UserDTO>() {
                    @Override
                    public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                        hideProgressDialog();
                        try {
                            if (response.isSuccessful()) {
                                UserDTO userDto = response.body();
                                List<UserDTO> userDtoList = new ArrayList<UserDTO>();
                                userDtoList.add(userDto);
                                showUserInfoInListView(userDtoList);
                            } else {
                                String errorMessage = response.errorBody().string();
                                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                                Log.e(TAG_RETROFIT_GET_POST, errorMessage);
                            }
                        }catch(IOException ex){
                            Log.e(TAG_RETROFIT_GET_POST, ex.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDTO> call, Throwable t) {
                        hideProgressDialog();
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e(TAG_RETROFIT_GET_POST, t.getMessage());
                    }
                };
                // Send request to web server and let callback to process the response.
                call.enqueue(callback);
            }
        });

        /* When click the get all user list button. */
        getAllUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                // Create and set Gson converter to parse response JSON string.
                GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();
                Call<List<UserDTO>> call = UserApiAdapter.getUserApiService(gsonConverterFactory).getUserAllList();
                retrofit2.Callback<List<UserDTO>> callback = new Callback<List<UserDTO>>(){
                    @Override
                    public void onResponse(Call<List<UserDTO>> call, Response<List<UserDTO>> response) {
                        hideProgressDialog();
                        try {
                            if (response.isSuccessful()) {
                                List<UserDTO> userDtoList = response.body();
                                showUserInfoInListView(userDtoList);
                            } else {
                                String errorMessage = response.errorBody().string();
                                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                                Log.e(TAG_RETROFIT_GET_POST, errorMessage);
                            }
                        }catch(IOException ex){
                            Log.e(TAG_RETROFIT_GET_POST, ex.getMessage());
                        }
                    }
                    @Override
                    public void onFailure(Call<List<UserDTO>> call, Throwable t) {
                        hideProgressDialog();
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e(TAG_RETROFIT_GET_POST, t.getMessage());
                    }
                };
                // Send request to web server and process response with the callback object.
                call.enqueue(callback);
            }
        });

    }

    /* Display web service returned UserDTO list in a list view at screen bottom. */
    private void showUserInfoInListView(List<UserDTO> userDtoList){
        if(userDtoList != null) {
            ArrayList<Map<String, Object>> itemDataList = new ArrayList<Map<String, Object>>();
            int size = userDtoList.size();
            for(int i=0;i<size;i++){
                UserDTO userDto = userDtoList.get(i);
                Map<String,Object> listItemMap = new HashMap<String,Object>();
                listItemMap.put("userId", "User ID : " + userDto.getUserId() + " , User Name : " + userDto.getUserName());
                listItemMap.put("userData", "Password : " + userDto.getPassword() + " , Email : " + userDto.getEmail());
                itemDataList.add(listItemMap);
            }
            SimpleAdapter simpleAdapter = new SimpleAdapter(this,itemDataList,android.R.layout.simple_list_item_2,
                    new String[]{
                        "userId","userData"
                    },
                    new int[]{
                        android.R.id.text1,android.R.id.text2
            });
            userListView.setAdapter(simpleAdapter);
        }
    }
    /* Initialize all UI controls. */
    private void initControls(){
        if(userNameEditText==null){
            userNameEditText = (EditText)findViewById(R.id.retrofit_user_name);
        }if(passwordEditText==null){
            passwordEditText = (EditText)findViewById(R.id.retrofit_password);
        }if(emailEditText==null){
            emailEditText = (EditText)findViewById(R.id.retrofit_email);
        }if(registerButton == null){
            registerButton = (Button)findViewById(R.id.retrofit_register_button);
        }if(getAllUserButton == null){
            getAllUserButton = (Button)findViewById(R.id.retrofit_get_all_user_button);
        }if(getUserByNameButton == null){
            getUserByNameButton = (Button)findViewById(R.id.retrofit_get_by_name_button);
        }if(userListView == null){
            userListView = (ListView)findViewById(R.id.retrofit_user_list_view);
        }if(progressDialog == null) {
            progressDialog = new ProgressDialog(RetrofitClas.this);
        }
    }
    /* Show progress dialog. */
    private void showProgressDialog(){
        // Set progress dialog display message.
        progressDialog.setMessage("Please Wait");
        // The progress dialog can not be cancelled.
        progressDialog.setCancelable(false);
        // Show it.
        progressDialog.show();
    }
    /* Hide progress dialog. */
    private void hideProgressDialog(){
        // Close it.
        progressDialog.hide();
    }
}
