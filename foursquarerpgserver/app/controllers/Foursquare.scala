package controllers

import play.api._
import play.api.mvc._
import fi.foyt.foursquare.api.FoursquareApi
import other._

object Foursquare extends Controller {
		val fsq = new FoursquareApi(FoursquareCredentials.CLIENT_ID, FoursquareCredentials.CLIENT_SECRET, FoursquareCredentials.PUSH_SECRET);
		fsq.setoAuthToken(FoursquareCredentials.OAUTH_KEY);

	def redirect = Action {
	  
		Ok("This is /redirect");
	}
	def handlePush = Action {
		Ok("This is /handle_push.");
	}
}