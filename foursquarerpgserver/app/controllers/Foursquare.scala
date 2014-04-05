package controllers

import fi.foyt.foursquare.api.FoursquareApi
import other._
import play.api._
import play.api.mvc._
import org.json.JSONObject
import java.io.IOException
import org.json.JSONException
import fi.foyt.foursquare.api.FoursquareApiException
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.http.GenericUrl

object Foursquare extends Controller {
	val fsq = new FoursquareApi(FoursquareCredentials.CLIENT_ID, FoursquareCredentials.CLIENT_SECRET, FoursquareCredentials.PUSH_SECRET);
	var values: Map[String, Seq[String]] = _;
	val fact = new NetHttpTransport().createRequestFactory();
	def redirectGet(code: String) = Action {
		println(code)
		fsq.authenticateCode(code)
		Ok("{\"token\":" +fsq.getOAuthToken() +  "}").as("application/json");
	}
	def redirectGetAndroid(code: String) = Action {
		val urlBuilder = new StringBuilder("https://foursquare.com/oauth2/access_token?client_id=").append(FoursquareCredentials.CLIENT_ID).append("&client_secret=").append(FoursquareCredentials.CLIENT_SECRET).append("&grant_type=authorization_code").append("&code=")
				.append(code);
		try {
		    val http = fact.buildGetRequest(new GenericUrl(urlBuilder.toString));
			val response = http.execute();
			if (response.getStatusCode() == 200) {
				val responseObject = new JSONObject(response.getContent());
				println(responseObject.toString())
				val token = responseObject.getString("access_token");
				Ok("{\"token\":" +token +  "}").as("application/json");
			} else {
				throw new IOException(response.getStatusMessage());
			}
		} catch {
		case e: JSONException => throw new FoursquareApiException(e);
		case e: IOException => throw new FoursquareApiException(e);
		}
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