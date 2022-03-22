name := "Stratego"
organization  := "de.htwg.se.stratego"
version       := "0.2.0"
ThisBuild / scalaVersion := "3.1.1"

libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.11"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % "test"

libraryDependencies += "com.google.inject" % "guice" % "5.1.0"

libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.0.1"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.10.0-RC6"

coverageExcludedPackages := ".*gui.*;.*Stratego.*"
