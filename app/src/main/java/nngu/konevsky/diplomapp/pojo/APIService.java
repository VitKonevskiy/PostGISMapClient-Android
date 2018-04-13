package nngu.konevsky.diplomapp.pojo;

/**
 * Created by User on 31.03.2018.
 */
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {
    @GET("values/{value}")
    Call<Pojo> getValues(
            @Path("value") String value
    );

    @GET("database/{value}")
    Call<Pojo> getDatabaseObject(
            @Path("value") String value
    );
}
