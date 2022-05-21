package de.htwg.se.stratego.model.fileIODatabaseComponent.fileIOSlick

import slick.jdbc.PostgresProfile.api.*
import slick.lifted.ProvenShape

class SlickMatchfield(tag: Tag) extends Table[(Int, Int, Int, Option[String], Option[Int], Option[Int])](tag, "matchfield") :

  def id: Rep[Int] = column[Int]("ID")
  def row: Rep[Int] = column[Int]("ROW")
  def col: Rep[Int] = column[Int]("COL")
  def figName: Rep[Option[String]] = column[Option[String]]("FIGNAME")
  def figValue: Rep[Option[Int]] = column[Option[Int]]("FIGVALUE")
  def colour: Rep[Option[Int]] = column[Option[Int]]("COLOUR")

  override def * = (id, row, col, figName, figValue, colour)// <>(Matchfield.tupled, Matchfield.unapply)

