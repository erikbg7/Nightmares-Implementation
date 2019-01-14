package erikblanca.dsa.eetac.edu.upc.nightmares.activities;

/**
 * Created by erikb on 1/14/2019.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.view.View;

import erikblanca.dsa.eetac.edu.upc.nightmares.R;


public class MenuActivity extends AppCompatActivity {

    //Sirve para encontrar el contexto desde el Callback
    public Context getContext() {
        return (Context)this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }


    // EVENTO del button para lanzar un dialogo y añadir un nuevo track
    public void topScorersButtonClicked(View view){
        Intent intent = new Intent(getContext(), ScorersActivity.class);
        startActivity(intent);
    }
}



