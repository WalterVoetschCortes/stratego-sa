lazy val root = (project in file(".")).aggregate(fileio).dependsOn(matchfield, fileio)
lazy val matchfield = project in file("MatchField")
lazy val fileio = (project in file("FileIO")).dependsOn(matchfield)
lazy val tui = (project in file("Tui"))

name := "Stratego"
organization  := "de.htwg.se.stratego"
version       := "0.2.0"
ThisBuild / scalaVersion := "3.1.1"

crossScalaVersions ++= Seq("2.13.6", "3.1.1")

libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.11"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % "test"

libraryDependencies += "com.google.inject" % "guice" % "5.1.0"

libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.0.1"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.10.0-RC6"

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4"

coverageExcludedPackages := ".*gui.*;.*Stratego.*"

val AkkaVersion = "2.6.18"
val AkkaHttpVersion = "10.2.9"
libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
    "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
    "com.typesafe.akka" %% "akka-actor" % AkkaVersion,
)

libraryDependencies += ("com.typesafe.akka" %% "akka-http" % AkkaHttpVersion).cross(CrossVersion.for3Use2_13)

