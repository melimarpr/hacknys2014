package controllers

import play.api._
import play.api.mvc._
import fi.foyt.foursquare.api.FoursquareApi
import other._

object Foursquare extends Controller {
		val fsq = new FoursquareApi(FoursquareCredentials.CLIENT_ID, FoursquareCredentials.CLIENT_SECRET, FoursquareCredentials.PUSH_SECRET);
		fsq.setoAuthToken(FoursquareCredentials.OAUTH_KEY);
		var values: Map[String, Seq[String]] = _;
	def redirectGet(code: String) = Action {
//	      request => values = request.body.asFormUrlEncoded.get;
    println(code)
    	
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