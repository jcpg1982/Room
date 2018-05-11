package pe.com.mastermachines.probando.retrofit.apiAdapter;

import pe.com.mastermachines.probando.retrofit.apiService.UserApiService;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @author Anonymous on 10/05/2018.
 * @version 1.0
 */
public class UserApiAdapter {

    private static String baseUrl = "https://www.dev2qa.com/";//demo/";

    public static UserApiService getUserApiService(Converter.Factory converterFactory)
    {
        // Create retrofit builder.
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();

        // Set base url. All the @POST @GET url is relative to this url.
        retrofitBuilder.baseUrl(baseUrl);

        /* The converter factory can be GsonConverterFactory( Convert response text to json object. ),
           if the value is null then convert response text okhttp3.ResponseBody. */
        if(converterFactory != null ) {
            retrofitBuilder.addConverterFactory(converterFactory);
        }

        // Build the retrofit object.
        Retrofit retrofit = retrofitBuilder.build();

        //Create instance for user manager interface and return it.
        UserApiService UserApiService = retrofit.create(UserApiService.class);
        return UserApiService;
    }
}
