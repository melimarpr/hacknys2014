package controllers

import play.api._
import play.api.mvc._
import fi.foyt.foursquare.api.FoursquareApi
import other._

object Foursquare extends Controller {
		val fsq = new FoursquareApi(FoursquareCredentials.CLIENT_ID, FoursquareCredentials.CLIENT_SECRET, FoursquareCredentials.PUSH_SECRET);
		var values: Map[String, Seq[String]] = _;
	def redirectGet(code: String) = Action {
    println(code)
    	fsq.authenticateCode(code)
		Ok("{\"token\":" +fsq.getOAuthToken() +  "}");
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