package de.htwg.se.stratego.model.fileIODatabaseComponent.fileIOMongo

import de.htwg.se.stratego.model.fileIODatabaseComponent.FileIODatabaseInterface
import org.mongodb.scala._
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.Updates.set
import org.mongodb.scala.result.{DeleteResult, InsertOneResult, UpdateResult}
import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global

class FileIOMongo extends FileIODatabaseInterface:
  //Cloud:
//  val connectionString: String = "mongodb+srv://stratego:stratego@strategodb.sduh7.mongodb.net/strategodb?retryWrites=true&w=majority"
//  System.setProperty("org.mongodb.async.type", "netty")
//  val mongoClient: MongoClient = MongoClient(connectionString)
//  val database: MongoDatabase = mongoClient.getDatabase("strategodb")
//  val collection: MongoCollection[Document] = database.getCollection("FileIO")

  //Local:
  val connectionString: String = "mongodb://localhost:27017"
  val mongoClient: MongoClient = MongoClient(connectionString)
  val database: MongoDatabase = mongoClient.getDatabase("Stratego")
  val collection: MongoCollection[Document] = database.getCollection("FileIO")

  override def update(id: Int, game: String): Unit =
    collection.deleteOne(equal("_id", 0)).toFuture()
    val doc: Document = Document("_id" -> id, "game" -> game)
    collection.insertOne(doc).subscribe(new Observer[InsertOneResult] {
      override def onNext(result: InsertOneResult): Unit = println(s"inserted: $result")

      override def onError(e: Throwable): Unit = println(s"onError: $e")

      override def onComplete(): Unit = println("completed")
    })

  override def delete(): Future[Any] =
    collection.deleteOne(equal("_id", 0)).toFuture()

  override def read(id: Int): Future[String] =
    val resultgame: Document = Await.result(collection.find(equal("_id", id)).first().head(), atMost = 10.second)
    val game: Future[String] = Future(resultgame("game").asString().getValue)
    game
