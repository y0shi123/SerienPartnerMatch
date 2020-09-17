package controllers

import infrastructure.h2UserDatabaseImpl
import javax.inject._
import play.api.mvc._


@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  var databaseObject = h2UserDatabaseImpl

  def addUser(user: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    databaseObject.addUser(user)
    Ok(views.html.index("User " + user + " added to database"))

  }


  def addShow(user: String, showname: String, episode: Int, season: Int): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    databaseObject.addShow(user, showname, episode, season)
    Ok(views.html.index("Show " + showname + " added to database for user " + user))

  }

  def withWhom(user: String, showname: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    var matchingUsers: String = databaseObject.withWhom(user = user, showname = showname)
    Ok(views.html.index("User " + user + " can be spoiler-free with users: " + matchingUsers))

  }

  def howFar(user: String, showname: String): Action[AnyContent] = Action { implicit  request: Request[AnyContent] =>
    var result = databaseObject.howFar(user, showname)
    Ok(views.html.index("User " + user + " has watched " + showname + " til episode " + result._1 + " of season " + result._2))
  }


  def whosFurther(user1: String, user2: String,  showname: String): Action[AnyContent] = Action { implicit  request: Request[AnyContent] =>
    var result1 = databaseObject.howFar(user1, showname)
    var result2 = databaseObject.howFar(user2, showname)
    if(result1._2 > result2._2)
      Ok(views.html.index("User " + user1 + " has watched " + showname + " further then " + user2))
    else if(result1._2 < result2._2)
      Ok(views.html.index("User " + user2 + " has watched " + showname + " further then " + user1))
    else if(result1._1 > result2._1)
      Ok(views.html.index("User " + user1 + " has watched " + showname + " further then " + user2))
    else if(result1._1 < result2._1)
      Ok(views.html.index("User " + user2 + " has watched " + showname + " further then " + user1))
    else Ok(views.html.index("User " + user2 + " has watched " + showname + " equaly far as " + user1))

  }


  def index() = Action { implicit request: Request[AnyContent] =>
    // This initialzes the database with some users.
    databaseObject.addUser("Peter")
    databaseObject.addShow("Peter", "Simpsons", 1, 1)
    databaseObject.addUser("Hans")
    databaseObject.addShow("Hans", "Simpsons", 1, 2)
    databaseObject.addUser("Detlef")
    databaseObject.addShow("Detlef", "Simpsons", 1, 1)

    Ok(views.html.index("Dummy Users added to database"))
  }


}
