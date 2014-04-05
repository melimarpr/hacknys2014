package controllers

import fi.foyt.foursquare.api.FoursquareApi
import other._
import play.api._
import play.api.mvc._

object Foursquare extends Controller {
		val fsq = new FoursquareApi(FoursquareCredentials.CLIENT_ID, FoursquareCredentials.CLIENT_SECRET, FoursquareCredentials.PUSH_SECRET);
		var values: Map[String, Seq[String]] = _;
		
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
	def handlePush = Action {
	  	      request => values = request.body.asFormUrlEncoded.get;
    println(values)
		Ok("This is /handle_push.");
	}
}