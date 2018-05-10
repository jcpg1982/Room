package pe.com.mastermachines.probando.retrofit.services;

import pe.com.mastermachines.probando.retrofit.modelos.Catalogo;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author Anonymous on 10/05/2018.
 * @version 1.0
 */
public interface Service {

    public static final String BASE_URL = "https://www.udacity.com/public-api/v0/";

    @GET("courses")
    Call<Catalogo> listaCatalogo();
}
