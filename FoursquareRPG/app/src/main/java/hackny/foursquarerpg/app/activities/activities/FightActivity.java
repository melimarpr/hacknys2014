package hackny.foursquarerpg.app.activities.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import hackny.foursquarerpg.app.R;
import hackny.foursquarerpg.app.activities.gsm.Config;
import hackny.foursquarerpg.app.activities.http.HttpRequest;
import hackny.foursquarerpg.app.activities.http.Server;
import hackny.foursquarerpg.app.activities.model.FoursquareUser;

public class FightActivity extends Activity {

    private FoursquareUser user;
    private TextView playerActive;
    private TextView enemyActive;
    private TextView enemyHP;
    private TextView playerStamina;
    private TextView playerHP;
    private Button btnAttack;
    private Button btnDefense;
    private Button btnRun;

    private ProgressDialog pd;

    private boolean isPlayerActive;


    private String monsterId;
    private String playerId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);

        this.getActionBar().hide();


        Bundle bnd = getIntent().getExtras();

        monsterId = bnd.getString(Config.MONSTER_KEY);
        playerId = bnd.getString(Config.USER_KEY);
        
        

        init();

        pd.show();
        doGetInitialValue(playerId, monsterId);
        Random r = new Random();
        this.isPlayerActive = r.nextBoolean();


    }

    private void doGetInitialValue(String playerId, String monsterId) {

        //Perform http request

        Uri.Builder urlB = Uri.parse(Server.Battle.initBattleValue).buildUpon();
        urlB.appendQueryParameter("enemyId", monsterId);
        urlB.appendQueryParameter("userId", playerId);

        Bundle params = new Bundle();
        params.putString("method", "GET");
        params.putString("url", urlB.toString().trim());

        HttpRequest request = new HttpRequest(params, new HttpRequest.HttpCallback() {

            @Override
            public void onSucess(JSONObject json) {

                pd.dismiss();
                updateInitValues(json);
                //setActive(isPlayerActive);



                Log.v("Token", json.toString());

            }

            @Override
            public void onFailed() {
                Log.e("Error", "Receiving Value Value");
            }

            @Override
            public void onDone() {
                pd.dismiss();
            }
        });
        request.execute();
    }

    private void updateInitValues(JSONObject json) {

        try {
            JSONObject user = new JSONObject(json.getString("user"));
            JSONObject enemy =new JSONObject(json.getString("enemy"));

            enemyHP.setText("HP: " + enemy.getString("hp"));
            playerStamina.setText("Stamina: "+user.getString("stamina"));
            playerHP.setText("HP: "+user.getString("hp"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateValues(JSONObject json){
        try {

            int eHp = json.getInt("enemyHP");
            int uSt = json.getInt("userStam");
            int pHp = json.getInt("userHP");


            if(eHp <= 0 ){
                Toast.makeText(this, "Congrats You Killed the Enemy", Toast.LENGTH_LONG).show();
                finish();
            }
            else if( uSt <= 0){
                Toast.makeText(this, "Your exhausted, refill stamina check-in more", Toast.LENGTH_LONG).show();
                finish();
            }
            else if(pHp <=0){
                Toast.makeText(this, "Your Dead", Toast.LENGTH_LONG).show();
                finish();
            }


            enemyHP.setText("HP: "+json.getString("enemyHP"));
            playerStamina.setText("Stamina: "+json.getString("userStam"));
            playerHP.setText("HP: "+json.getString("userHP"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void init() {

        pd = new ProgressDialog(this);
        pd.setMessage("Running Game");

        btnRun = (Button) findViewById(R.id.btnRun);
        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        playerActive = (TextView) findViewById(R.id.playerActive);
        enemyActive = (TextView) findViewById(R.id.enemyActive);
        btnAttack = (Button) findViewById(R.id.btnAttack);
        btnAttack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                doAction("playerAttack");

            }
        });
        btnDefense = (Button) findViewById(R.id.btnDefense);
        btnDefense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                doAction("playerDefense");
            }
        });

        enemyHP = (TextView) findViewById(R.id.enemyHp);
        playerStamina = (TextView) findViewById(R.id.playerStamina);
        playerHP = (TextView) findViewById(R.id.playerHP);







    }

    private void toogleActive(){

        isPlayerActive = !isPlayerActive;
        setActive(isPlayerActive);


    }

    private void setActive(boolean active){
        if(active){

            playerActive.setVisibility(View.VISIBLE);
            enemyActive.setVisibility(View.INVISIBLE);
        }else{
            playerActive.setVisibility(View.INVISIBLE);
            enemyActive.setVisibility(View.VISIBLE);
            //pd.show();
           // doAction("enemyAttack");
        }
    }

    private void doAction(String type) {
        //Perform http request

        Uri.Builder urlB = Uri.parse(Server.Battle.doBattle).buildUpon();
        urlB.appendQueryParameter("battleType", type);
        urlB.appendQueryParameter("enemyId", monsterId);
        urlB.appendQueryParameter("userId", playerId);

        Bundle params = new Bundle();
        params.putString("method", "GET");
        params.putString("url", urlB.toString().trim());

        HttpRequest request = new HttpRequest(params, new HttpRequest.HttpCallback() {

            @Override
            public void onSucess(JSONObject json) {

                pd.dismiss();
                updateValues(json);
                //toogleActive();
                //setActive(isPlayerActive);



                Log.v("Token", json.toString());

            }

            @Override
            public void onFailed() {
                Log.e("Error", "Receiving Value Value");
            }

            @Override
            public void onDone() {
                pd.dismiss();
            }
        });
        request.execute();


    }


}
