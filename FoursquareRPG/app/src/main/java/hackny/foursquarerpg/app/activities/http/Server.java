package hackny.foursquarerpg.app.activities.http;

/**
 * Created by enrique on 4/5/14.
 */
public class Server {

    public static final String URL = "http://jluzon.com/foursquarerpg/";


    public static class Login{

        public static final String sendToken = URL+"sendToken/";

    }

    public static class User{

        public static final String getUser = URL+"getUser/";

    }

    public static class Battle{
        public static final String initBattleValue = URL+"getBattleInfo/";
        public static final String doBattle = URL+"doBattle/";

    }


}
