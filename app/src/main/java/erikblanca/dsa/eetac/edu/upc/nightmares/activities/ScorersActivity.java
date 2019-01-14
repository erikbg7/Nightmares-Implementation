package erikblanca.dsa.eetac.edu.upc.nightmares.activities;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by erikb on 1/14/2019.
 */
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import erikblanca.dsa.eetac.edu.upc.nightmares.utils.MyAdapter;
import erikblanca.dsa.eetac.edu.upc.nightmares.services.NightAPI;
import erikblanca.dsa.eetac.edu.upc.nightmares.R;
import erikblanca.dsa.eetac.edu.upc.nightmares.models.ScoreTemplate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ScorersActivity extends AppCompatActivity {

    private final String BaseURL = "http://10.0.2.2:8080/dsaApp/";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private NightAPI nightAPI;
    private List<ScoreTemplate> scoresList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorers);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BaseURL)

                .addConverterFactory(GsonConverterFactory.create())
                .build();

        nightAPI = retrofit.create(NightAPI.class);
        actualizarListaScore();
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
                        scoresList.remove(viewHolder.getAdapterPosition());
                        mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


    }

    // CALL para pedir la lista de tracks al servidor
    private void actualizarListaScore() {
        Call<List<ScoreTemplate>> callTrackList = nightAPI.getScoreList();
        callTrackList.enqueue(new Callback<List<ScoreTemplate>>() {
            @Override
            public void onResponse(Call<List<ScoreTemplate>> call, Response<List<ScoreTemplate>> response) {
                if (response.isSuccessful()) {
                    scoresList = response.body();
                    Log.d("QuestionsCallback", "//////////////////////////////////  SUCCES!:  we have  " + scoresList.size() + "  players  ///////////////////////////////");

                    for (int i = 0; i < scoresList.size(); i++) {
                        Log.d("QuestionsCallback", "//////////////////////////////////  Username : " + scoresList.get(i).getName() + "///////////////////////////////");
                    }
                    iniciarControles();
                    mAdapter = new MyAdapter(scoresList);
                    recyclerView.setAdapter(mAdapter);
                } else
                    Log.d("QuestionsCallback", "////////////////////////////////////   NO SUCCESFUL RESPONSE   /////////////////////////////////////");
            }

            @Override
            public void onFailure(Call<List<ScoreTemplate>> call, Throwable t) {
                Log.d("QuestionsCallback", "////////////////////////////////////////   ERROR   /////////////////////////////////");
                t.printStackTrace();
            }
        });

    }



    /*
    private final String BaseURL = "http://147.83.7.207:8080/dsaApp/";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TrackAPI trackAPI;

    private List<Track> trackList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorers);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BaseURL)

                .addConverterFactory(GsonConverterFactory.create())
                .build();

        trackAPI = retrofit.create(TrackAPI.class);
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
    }*/


}
