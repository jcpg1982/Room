package pe.com.mastermachines.probando.retrofit.apiService;

import java.util.List;

import okhttp3.ResponseBody;
import pe.com.mastermachines.probando.retrofit.Dto.UserDTO;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author Anonymous on 10/05/2018.
 * @version 1.0
 */
public interface UserApiService {

    @FormUrlEncoded
    @POST("demo/user/register.html")
    public Call<ResponseBody> registerUser(@Field("userName") String userNameValue,
                                           @Field("password") String passwordValue,
                                           @Field("email") String emailValue);
    /*
     *  @GET : Indicate this method will perform a http get action with the specified url.
     *  @Query("userName") : Parse out the userName query parameter in the url and
     *  assign the parsed out value to userNameValue parameter.
     * */
    @GET("demo/user/listUser.html")
    public Call<UserDTO> getUserByName(@Query("userName") String userNameValue);


    /*
     *  Similar with getUserByName method, this method will return all users in a list.
     * */
    @GET("demo/user/listAllUser.html")
    public Call<List<UserDTO>> getUserAllList();
}
