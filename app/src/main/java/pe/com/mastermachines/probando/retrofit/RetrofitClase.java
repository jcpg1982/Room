package pe.com.mastermachines.probando.retrofit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import pe.com.mastermachines.probando.InsertUser;
import pe.com.mastermachines.probando.R;
import pe.com.mastermachines.probando.retrofit.modelos.Catalogo;
import pe.com.mastermachines.probando.retrofit.modelos.Course;
import pe.com.mastermachines.probando.retrofit.modelos.Instructor;
import pe.com.mastermachines.probando.retrofit.services.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClase extends AppCompatActivity {

    String TAG = RetrofitClase.class.getSimpleName();
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_clase);

        retrofit = new Retrofit.Builder()
                .baseUrl(Service.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        Call<Catalogo> requesCatalogo = service.listaCatalogo();

        requesCatalogo.enqueue(new Callback<Catalogo>() {
            @Override
            public void onResponse(Call<Catalogo> call, Response<Catalogo> response) {
                if (!response.isSuccessful()){
                    Log.e(TAG, "Error" + response.code());
                }else {
                    Catalogo catalogo = response.body();
                    for (Course c : catalogo.courses){
                        Log.i(TAG, String.format("%s: %s", c.title, c.subtitle));
                        for (Instructor i : c.instructors){
                            Log.i(TAG, i.name);
                        }
                        Log.i(TAG, "-------------");
                    }
                }
            }

            @Override
            public void onFailure(Call<Catalogo> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, InsertUser.class);
        startActivity(intent);
        finish();
    }
}
