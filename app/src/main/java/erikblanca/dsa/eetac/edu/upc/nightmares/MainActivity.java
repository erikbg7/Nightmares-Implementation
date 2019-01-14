package erikblanca.dsa.eetac.edu.upc.nightmares;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements NewTrkDialog.OnDialogListener {

    //private final String BaseURL = "http://147.83.7.207:8080/dsaApp/";
    private final String BaseURL = "http://10.0.2.2:8080/dsaApp/";


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TrackAPI trackAPI;
    private NightAPI nightAPI;

    private List<Track> trackList = new ArrayList<>();

    //Sirve para encontrar el contexto desde el Callback
    public Context getContext() {
        return (Context)this;
    }


    //EVENTO del button del dialogo para confirmar la creación de un track
    @Override
    public void OnPositiveButtonClicked(String singer, String title) {

//        Integer id = mAdapter.getItemCount();
        Integer id = 1;
        Track newTrack = new Track(id, singer, title);
        createNewTrack(newTrack);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BaseURL)

                .addConverterFactory(GsonConverterFactory.create())
                .build();

        trackAPI = retrofit.create(TrackAPI.class);
        nightAPI = retrofit.create(NightAPI.class);
        actualizarTracks();
    }


    // INICIA el recyclerview y permite borrar del recycler los elementos con un SWAP
    private void iniciarControles(){
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // put this after your definition of your recyclerview
        // input in your data mode in this example a java.util.List, adjust if necessary
        // adapter is your adapter
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                            target) {
                        return false;
                    }
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        trackList.remove(viewHolder.getAdapterPosition());
                        mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


    }

    // CALL para pedir la lista de tracks al servidor
    private void actualizarTracks(){
        Call<List<Track>> callTrackList = trackAPI.getTracks();
        callTrackList.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                if(response.isSuccessful()){
                    trackList = response.body();
                    Log.d("QuestionsCallback", "//////////////////////////////////  SUCCES!:  we have  " +trackList.size() + "  tracks  ///////////////////////////////");

                    for (int i = 0; i < trackList.size(); i++){
                        Log.d("QuestionsCallback", "//////////////////////////////////  Nombre del cantante 3:" +trackList.get(i).getSinger()+ "///////////////////////////////");
                    }
                    iniciarControles();
                    mAdapter = new MyAdapter(trackList);
                    recyclerView.setAdapter(mAdapter);
                }
                else
                    Log.d("QuestionsCallback", "////////////////////////////////////   NO SUCCESFUL RESPONSE   /////////////////////////////////////");
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                Log.d("QuestionsCallback", "////////////////////////////////////////   ERROR   /////////////////////////////////");
                t.printStackTrace();
            }
        });
    }

    // EVENTO del button para lanzar un dialogo y añadir un nuevo track
    public void onNewTrackButtonClicked(View view){
        DialogFragment df = new NewTrkDialog();
        df.show(getSupportFragmentManager(), "dialog");
    }

    // CALL para hacer un POST de un nuevo track
    private void createNewTrack(Track tr){
        /*Call<Track> callNewTrack = trackAPI.createTrack(tr);
        callNewTrack.enqueue(new Callback<Track>() {
            @Override
            public void onResponse(Call<Track> call, Response<Track> response) {
                if(response.isSuccessful()){
                    actualizarTracks();
                    mAdapter = new MyAdapter(trackList);
                    recyclerView.setAdapter(mAdapter);
                }
                else
                    Log.d("QuestionsCallback", "////////////////////////////////////   NO SUCCESFUL RESPONSE   /////////////////////////////////////");
            }

            @Override
            public void onFailure(Call<Track> call, Throwable t) {
                Log.d("QuestionsCallback", "////////////////////////////////////////   ERROR   /////////////////////////////////");
                t.printStackTrace();
            }
        });*/

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

}