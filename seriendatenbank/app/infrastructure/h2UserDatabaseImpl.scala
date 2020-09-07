package infrastructure

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import slick.jdbc.H2Profile.api._

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global

object h2UserDatabaseImpl {

  var currentId = 0

  object Tables {
    type Show = (String, Int,  Int) // Name + Season + Episode
    type ShowList = ListBuffer[Show]
    type Person = (Int, String, ShowList)
    type database = ListBuffer[Person]
    var myDatabase: database = ListBuffer()



    /*class People(tag: Tag) extends Table[Person](tag, "PERSON") {
      def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)

      def name = column[String]("NAME")

      def shows = column[showlist]("SHOWS")


      def * = (name, id, shows)

      */
  }

  import Tables._

  def addUser(user: String): Unit={
    if(!myDatabase.exists(y => y._2 == user)){
      var newUser: Person = (currentId, user, ListBuffer())
      myDatabase += newUser
      currentId = getID()}

  }

  def getID() = {
    currentId = currentId +1
    currentId
  }

  def printDatabase()={
    println("Database Currently: ")
    println(myDatabase)
  }

  def addShow(user: String, showName: String, season: Int, episode: Int): Unit={

    var newShow: Show = (showName, season, episode)
    var tempPerson: Person = null

    for (aPerson <- myDatabase) {
      if(aPerson._2.equals(user)){
            println("adding: " + showName + " to " + aPerson)
            var tempShowlist: ShowList = aPerson._3.filter(_._1 != showName)
            var tempShow: Show = (showName, season, episode)
            tempShowlist+= tempShow
            tempPerson = ( aPerson._1, aPerson._2, tempShowlist)

          }
    }
    //println("Debug: ")
    //println(myDatabase.filter(!_._2.equals(username)))
    //println("/Debug ")
    if (tempPerson != null)
      myDatabase = myDatabase.filter(!_._2.equals(user)) += tempPerson

    printDatabase()
    //print(myDatabase)

  }

  def howFar(user: String, showname: String)= {
    if (myDatabase.exists(y => y._2.equals(user) && y._3.exists(y => y._1.equals(showname)))) {
      var tempUser: Person = myDatabase.filter(y => y._2.equals(user)).head
      var tempEpisode: Int = tempUser._3.filter(y => y._1.equals(showname)).head._3
      var tempSeason: Int = tempUser._3.filter(y => y._1.equals(showname)).head._2
      println("The User " + user + " has watched the show " + showname + " til episode "
        + tempEpisode + " of season " + tempSeason)
      (tempEpisode, tempSeason)
    }
    else {
      println("The User" + user + " has not watched " + showname)
      (0, 0)
    }
  }

  def equalyfar(user1: String, user2: String, showname: String)= {
     if (howFar(user1, showname).equals(howFar(user2, showname))){
        true
    }
     else
       false

  }

  def withWhom(user: String, showname: String)={


    var tempUsers: ListBuffer[Person] = myDatabase.filter( y => equalyfar(user, y._2, showname) && !user.equals(y._2))

    tempUsers.foreach(y => println(y._2))
    var result: String = ""
    tempUsers.foreach(y => result += y._2 + ", ")
    try{
    result = result.substring(0, result.length-2)
    }  catch{
      case e: StringIndexOutOfBoundsException => result = "No matching Users found"
    }

    result
  }



}





  /*val randomlist: List[Int] = List(1,2,3)
  val d: Person2 = (3, "bla", randomlist)

  val db = Database.forConfig("h2mem1")

  val inserts = DBIO.seq(
      people += (0,"C. Vogt",999),
      people += (1,"J. Vogt",1001),
      people += (2,"J. Doe",18))


  try{
  Await.result(db.run(DBIO.seq(
        people.schema.create
        //,inserts
      )), Duration.Inf)}
  catch{
    case e: org.h2.jdbc.JdbcSQLException =>  println("Database already initialized.")
    case e: Exception => println("Something unspecified went wrong: " + e.getClass)
   */






  /*def doSomething(): Unit={

    print("something")
    import Tables._

    Await.result(db.run(DBIO.seq(
      inserts)), Duration.Inf)


    val action = sql"select ID, NAME from PERSON".as[(Int,String)]
    print(Await.result(db.run(action), Duration.Inf))
    println(x)


}*/

