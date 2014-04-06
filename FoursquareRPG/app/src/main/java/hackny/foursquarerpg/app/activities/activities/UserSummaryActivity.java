package hackny.foursquarerpg.app.activities.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;

import hackny.foursquarerpg.app.R;
import hackny.foursquarerpg.app.activities.http.HttpRequest;
import hackny.foursquarerpg.app.activities.http.Server;
import hackny.foursquarerpg.app.activities.model.FoursquareUser;

public class UserSummaryActivity extends Activity {


    private ListView listView;
    private LinearLayout linearLayout;
    private ProgressBar pb;
    private TextView tv;
    private Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_summary);


        listView = (ListView) findViewById(R.id.userStatsLV);
        linearLayout = (LinearLayout) findViewById(R.id.statsLayout);
        pb = (ProgressBar) findViewById(R.id.userHttpProgressBar);
        tv = (TextView) findViewById(R.id.userName);


        //Hide ActionBar
        this.getActionBar().setTitle("User Stats");

        Typeface face = Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Thin.ttf");
        tv.setTypeface(face);

        this.activity = this;

    }

    @Override
    protected void onResume() {
        super.onResume();
        listView.invalidate();
        linearLayout.invalidate();
        doHttpGetUser(MainActivity.getSavedUser(this).username);
    }

    @Override
    protected void onPause() {
        super.onPause();

        linearLayout.setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);


    }

    private void doHttpGetUser(int id){

        //Perform http request

        Uri.Builder urlB = Uri.parse(Server.User.getUser).buildUpon();
        urlB.appendQueryParameter("username", Integer.toString(id));

        Bundle params = new Bundle();
        params.putString("method", "GET");
        params.putString("url", urlB.toString().trim());

        HttpRequest request = new HttpRequest(params, new HttpRequest.HttpCallback() {

            @Override
            public void onSucess(JSONObject json) {

                pb.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);

                    FoursquareUser user = new FoursquareUser(json);

                    tv.setText(Integer.toString(user.username));

                    listView.setAdapter(new StatsAdapter(getBaseContext(), user));


                    Log.v("Token", json.toString());

            }

            @Override
            public void onFailed() {
                Log.e("Error", "Receiving Value Value");
            }

            @Override
            public void onDone() {
            }
        });
        request.execute();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_summary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void logout(){
        SharedPreferences.Editor editor = this.getPreferences(Context.MODE_PRIVATE).edit().clear();
        editor.commit();
        editor.apply();
        finish();

    }


    class StatsAdapter extends ArrayAdapter<StatsContainter>{


        public StatsAdapter(Context context, FoursquareUser user){
            super(context, android.R.layout.simple_list_item_1);

            this.addAll(new StatsContainter[]{

                    new StatsContainter(R.drawable.sword, "Attack", user.attack),
                    new StatsContainter(R.drawable.defense, "Defense", user.defense),
                    new StatsContainter(R.drawable.exp, "Experience", user.experience),
                    new StatsContainter(R.drawable.hp, "HP", user.hp),
                    new StatsContainter(R.drawable.gold, "Gold", user.gold),
                    new StatsContainter(R.drawable.stamina, "Stamina", user.stamina),
            });


        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            StatsContainter stat = getItem(position);
            LayoutInflater inflater = activity.getLayoutInflater();
            View row     = inflater.inflate(R.layout.row_stats, parent,false);

            ImageView imageView = (ImageView) row.findViewById(R.id.ivIcon);
            imageView.setImageDrawable(getResources().getDrawable(stat.imageID));

            TextView tv = (TextView) row.findViewById(R.id.tvTitle);
            tv.setText(stat.title);

            TextView tv2 = (TextView) row.findViewById(R.id.tvValue);
            tv2.setText(Integer.toString(stat.value));


            return row;
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }
    }

    class StatsContainter{

        int imageID;
        String title;
        int value;

        public StatsContainter(int imageID, String title, int value){
            this.imageID = imageID;
            this.title = title;
            this.value = value;
        }

    }







}
