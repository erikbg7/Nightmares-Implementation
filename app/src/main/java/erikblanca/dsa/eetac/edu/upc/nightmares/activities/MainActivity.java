package erikblanca.dsa.eetac.edu.upc.nightmares.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import erikblanca.dsa.eetac.edu.upc.nightmares.utils.CredentialsDialog;
import erikblanca.dsa.eetac.edu.upc.nightmares.services.NightAPI;
import erikblanca.dsa.eetac.edu.upc.nightmares.R;
import erikblanca.dsa.eetac.edu.upc.nightmares.models.LogSignTemplate;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements CredentialsDialog.OnDialogListener {

    private final String BaseURL = "http://10.0.2.2:8080/dsaApp/";
    private NightAPI nightAPI;
    private Button logButton;
    private Button signButton;
    private int operation = 0;

    //Devuelve el contexto, lo utilizamos desde los Callbacks
    public Context getContext() {
        return (Context)this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logButton = (Button) findViewById(R.id.buttonLogIn);
        signButton = (Button) findViewById(R.id.buttonSignUp);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BaseURL)

                .addConverterFactory(GsonConverterFactory.create())
                .build();

        nightAPI = retrofit.create(NightAPI.class);

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operation = 1;
                DialogFragment df = new CredentialsDialog();
                df.show(getSupportFragmentManager(), "dialog");
            }
        });

        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operation = 2;
                DialogFragment df = new CredentialsDialog();
                df.show(getSupportFragmentManager(), "dialog");
            }
        });
    }

    //EVENTO del button de Credentials Dialog para enviar el check del login
    @Override
    public void OnPositiveButtonClicked(String name, String password) {

        LogSignTemplate credentials = new LogSignTemplate(name, password);
        switch (operation){
            case 0: break;
            case 1: logIn(credentials); break;
            case 2: signUp(credentials); break;
            default: break;
        }
    }


    // CALL para hacer un POST de login con las credenciales del usuario
    private void logIn(LogSignTemplate credentials){
        //LogSignTemplate credentials2 = new LogSignTemplate("Tfue", "123");
        Call<LogSignTemplate> callNewTrack = nightAPI.authorize(credentials);
        callNewTrack.enqueue(new Callback<LogSignTemplate>() {
            @Override
            public void onResponse(Call<LogSignTemplate> call, Response<LogSignTemplate> response) {
                if(response.isSuccessful()){
                    Log.d("QuestionsCallback", "////////////////////////////////////   SUCCESFUL LOGIN !!!!!!!!!!!!!!!  /////////////////////////////////////");
                    Toasty.success(getContext(), "Succes, welcome.", Toast.LENGTH_SHORT, true).show();
                    Intent intent = new Intent(getContext(), MenuActivity.class);
                    startActivity(intent);
                }
                else{
                    Log.d("QuestionsCallback", "////////////////////////////////////   NO SUCCESFUL RESPONSE   /////////////////////////////////////");
                    Toasty.error(getContext(), "Incorrect username or password.", Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onFailure(Call<LogSignTemplate> call, Throwable t) {
                Log.d("QuestionsCallback", "////////////////////////////////////////   ERROR   /////////////////////////////////");
                Toasty.error(getContext(), "Error while validating..", Toast.LENGTH_SHORT, true).show();
                t.printStackTrace();
            }
        });
    }

    // CALL para hacer un POST de registro con las credenciales del usuario
    private void signUp(LogSignTemplate credentials){
        Call<LogSignTemplate> callNewTrack = nightAPI.register(credentials);
        callNewTrack.enqueue(new Callback<LogSignTemplate>() {
            @Override
            public void onResponse(Call<LogSignTemplate> call, Response<LogSignTemplate> response) {
                if(response.isSuccessful()){
                    Log.d("QuestionsCallback", "////////////////////////////////////   SUCCESFUL SIGN UP !!!!!!!!!!!!!!!  /////////////////////////////////////");
                    Toasty.success(getContext(), "Succes, welcome.", Toast.LENGTH_SHORT, true).show();
                    Intent intent = new Intent(getContext(), MenuActivity.class);
                    startActivity(intent);
                }
                else{
                    Log.d("QuestionsCallback", "////////////////////////////////////   NO SUCCESFUL RESPONSE   /////////////////////////////////////");
                    Toasty.error(getContext(), "Name already in use.", Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onFailure(Call<LogSignTemplate> call, Throwable t) {
                Log.d("QuestionsCallback", "////////////////////////////////////////   ERROR SIGNING   /////////////////////////////////");
                Toasty.error(getContext(), "Error while validating.", Toast.LENGTH_SHORT, true).show();
                t.printStackTrace();
            }
        });
    }
}