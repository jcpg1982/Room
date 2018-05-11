package pe.com.mastermachines.probando.retrofit.apiService;

import pe.com.mastermachines.probando.retrofit.TimeFromWeb;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author Anonymous on 10/05/2018.
 * @version 1.0
 */
public interface ApiService {

    @GET("api/json/west/now")
    Call<TimeFromWeb> loadTime();
}
