name := "Stratego"
organization  := "de.htwg.se.stratego"
version       := "0.2.0"
ThisBuild / scalaVersion := "3.1.1"

libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.11"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % "test"

// libraryDependencies += "com.google.inject" % "guice" % "4.2.3"
libraryDependencies += "com.google.inject" % "guice" % "5.1.0"

// libraryDependencies += "net.codingwell" %% "scala-guice" % "4.2.10"
// libraryDependencies += ("net.codingwell" %% "scala-guice" % "5.0.2").cross(CrossVersion.for3Use2_13)

// libraryDependencies += "org.scala-lang.modules" % "scala-xml_2.12" % "1.0.6"
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.0.1"

// libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.6"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.10.0-RC6"

coverageExcludedPackages := ".*gui.*;.*Stratego.*"
