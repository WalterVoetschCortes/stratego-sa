package de.htwg.se.stratego.model.fileIODatabaseComponent.fileIOMongo

import de.htwg.se.stratego.model.fileIODatabaseComponent.FileIODatabaseInterface
import org.mongodb.scala._
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.Updates.set
import org.mongodb.scala.result.{DeleteResult, InsertOneResult, UpdateResult}

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt


class FileIOMongo extends FileIODatabaseInterface:
  val connectionString: String = "mongodb+srv://stratego:stratego@strategodb.sduh7.mongodb.net/strategodb?retryWrites=true&w=majority"
  System.setProperty("org.mongodb.async.type", "netty")
  val mongoClient: MongoClient = MongoClient(connectionString)
  val database: MongoDatabase = mongoClient.getDatabase("strategodb")

  val collection: MongoCollection[Document] = database.getCollection("FileIO")


  override def update(id: Int, game: String): Unit =
    delete()
    val doc: Document = Document("_id" -> id, "game" -> game)
    observerInsertion(collection.insertOne(doc))

  override def delete(): Unit =
    collection.deleteOne(equal("_id", 1)).subscribe(
      (dr: DeleteResult) => print(s"Deleted document with id 1\n"),
      (e: Throwable) => print(s"Error when deleting the document with id 1: $e\n")
    )

  override def read(id: Int): String =
    val resultgame: Document = Await.result(collection.find(equal("_id", id)).first().head(), atMost = 10.second)
    val game: String = resultgame("game").asString().getValue
    game

  private def observerInsertion(insertObservable: SingleObservable[InsertOneResult]): Unit =
    insertObservable.subscribe(new Observer[InsertOneResult] {
      override def onNext(result: InsertOneResult): Unit = println(s"inserted: $result")

      override def onError(e: Throwable): Unit = println(s"onError: $e")

      override def onComplete(): Unit = println("completed")
    })
