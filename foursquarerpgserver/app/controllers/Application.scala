package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

  def index = Action {
    
    Ok(views.html.index("Your new application is ready."))
  }
  def redirect = Action {
    Ok("This is /redirect");
    }
  def handlePush = Action {
    Ok("This is /handle_push.");
  }
  

}