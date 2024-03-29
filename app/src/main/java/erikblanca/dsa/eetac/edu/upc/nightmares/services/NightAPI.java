package erikblanca.dsa.eetac.edu.upc.nightmares.services;

/**
 * Created by erikb on 1/13/2019.
 */

import erikblanca.dsa.eetac.edu.upc.nightmares.models.LogSignTemplate;
import erikblanca.dsa.eetac.edu.upc.nightmares.models.ScoreTemplate;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import java.util.List;

public interface NightAPI {
    @POST("authentication/LogIn")
    Call<LogSignTemplate> authorize(@Body LogSignTemplate credentials);

    @POST("authentication/SignUp")
    Call<LogSignTemplate> register(@Body LogSignTemplate credentials);

    @GET("users/byScore")
    Call<List<ScoreTemplate>> getScoreList();

}
