package erikblanca.dsa.eetac.edu.upc.nightmares;

/**
 * Created by erikb on 1/14/2019.
 */

public class Main {
}

/*package erikblanca.dsa.eetac.edu.upc.nightmares.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import erikblanca.dsa.eetac.edu.upc.nightmares.NewTrkDialog;
import erikblanca.dsa.eetac.edu.upc.nightmares.services.NightAPI;
import erikblanca.dsa.eetac.edu.upc.nightmares.R;
import erikblanca.dsa.eetac.edu.upc.nightmares.Track;
import erikblanca.dsa.eetac.edu.upc.nightmares.TrackAPI;
import erikblanca.dsa.eetac.edu.upc.nightmares.models.LogSignTemplate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements NewTrkDialog.OnDialogListener {

    private final String BaseURL = "http://10.0.2.2:8080/dsaApp/";
    private NightAPI nightAPI;

    //Sirve para encontrar el contexto desde el Callback
    public Context getContext() {
        return (Context)this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //EVENTO del button del dialogo para confirmar la creación de un track
    @Override
    public void OnPositiveButtonClicked(String singer, String title) {
        Integer id = 1;
        Track newTrack = new Track(id, singer, title);
        //createNewTrack(newTrack);
    }

    // EVENTO del button para lanzar un dialogo y añadir un nuevo track
    public void onLogInButtonClicked(View view){

        createNewTrack();

    }



    // EVENTO del button para lanzar un dialogo y añadir un nuevo track
    public void onNewTrackButtonClicked(View view){
        DialogFragment df = new NewTrkDialog();
        df.show(getSupportFragmentManager(), "dialog");
    }

    // CALL para hacer un POST del Login del usuario
    private void createNewTrack(){
        LogSignTemplate credentials = new LogSignTemplate("Tfue", "123");
        Call<LogSignTemplate> callAuthorization = nightAPI.authorize(credentials);
        callAuthorization.enqueue(new Callback<LogSignTemplate>() {
            @Override
            public void onResponse(Call<LogSignTemplate> call, Response<LogSignTemplate> response) {
                if(response.isSuccessful()){
                    Log.d("QuestionsCallback", "////////////////////////////////////   SUCCES!!!!!!!!!!   /////////////////////////////////////");
                    Intent intent = new Intent(getContext(), MenuActivity.class);
                    startActivity(intent);
                }
                else
                    Log.d("QuestionsCallback", "////////////////////////////////////   NO RESPONSE   /////////////////////////////////////");
            }

            @Override
            public void onFailure(Call<LogSignTemplate> call, Throwable t) {
                Log.d("QuestionsCallback", "////////////////////////////////////////   ERROR   /////////////////////////////////");
                t.printStackTrace();
            }
        });
    }

}*/