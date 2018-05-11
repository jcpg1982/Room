package pe.com.mastermachines.probando.retrofit.apiAdapter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Anonymous on 10/05/2018.
 * @version 1.0
 */
public class ApiAdapter {

    Retrofit retrofit;

    public ApiAdapter(){
    }

    public Retrofit getAdapter(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://worldclockapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
