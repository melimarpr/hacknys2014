package hackny.foursquarerpg.app.activities.model;

import org.json.JSONObject;

import hackny.foursquarerpg.app.activities.keys.Keys;

/**
 * Created by enrique on 4/5/14.
 */
public class FoursquareUser {

    public int id;
    public int defense;
    public int attack;
    public int hp;
    public int username;
    public int stamina;
    public String token;
    public int experience;
    public int gold;
    public boolean valid = false;


    public FoursquareUser(JSONObject json){

        try {
            id = json.getInt(Keys.JsonKeys.ID);
            defense = json.getInt(Keys.JsonKeys.DEFENSE);
            attack = json.getInt(Keys.JsonKeys.Attack);
            hp = json.getInt(Keys.JsonKeys.HP);
            username = json.getInt(Keys.JsonKeys.USERNAME);
            stamina = json.getInt(Keys.JsonKeys.STAMINA);
            token = json.getString(Keys.JsonKeys.TOKEN);
            experience = json.getInt(Keys.JsonKeys.EXPERIENCE);
            gold = json.getInt(Keys.JsonKeys.GOLD);

            valid = true;



        }catch (Exception e){};

    }

    public boolean isValid(){
        return valid;
    }


}
