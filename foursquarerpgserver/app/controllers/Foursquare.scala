package controllers

import fi.foyt.foursquare.api.FoursquareApi
import other.FoursquareCredentials
import play.api.mvc.Action
import play.api.mvc.Controller
import com.google.gson.Gson

object Foursquare extends Controller {
	val fsq = new FoursquareApi(FoursquareCredentials.CLIENT_ID, FoursquareCredentials.CLIENT_SECRET, FoursquareCredentials.PUSH_SECRET);
	var values: Map[String, Seq[String]] = _;
		  val gson = new Gson();
	def redirectGet(code: String) = Action {
		println(code)
		fsq.authenticateCode(code)
		Ok("{\"token\":" +fsq.getOAuthToken() +  "}").as("application/json");
	}

	def some = Action {
		Redirect(fsq.getAuthenticationUrl());
	}
	def redirectPost = Action {
		//	      request => values = request.body.asFormUrlEncoded.get;
		println(values)

		Redirect(fsq.getAuthenticationUrl());
	}
	def sendToken(token: String) = Action {

	  fsq.setoAuthToken(token);
	  val username = fsq.user("self").getResult().getId();
	  Ok(gson.toJson(Database.addUser(username, token)))
	}
	def handlePush = Action {
		request => values = request.body.asFormUrlEncoded.get;
		println(values)
		Ok("This is /handle_push.");
	}
	
	def getUserGet(username: String) = Action {
	  Ok(gson.toJson(Database.getUser(username)));
	}
}