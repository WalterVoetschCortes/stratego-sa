name := "FileIO"
organization  := "de.htwg.se.stratego"
version       := "0.2.0"
scalaVersion := "3.1.2"

crossScalaVersions ++= Seq("2.13.6", "3.1.1")

libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.11"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % "test"

libraryDependencies += "com.google.inject" % "guice" % "5.1.0"

libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.0.1"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.10.0-RC6"

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4"

libraryDependencies += ("com.typesafe.akka" %% "akka-http" % "10.2.4").cross(CrossVersion.for3Use2_13)
libraryDependencies += ("com.typesafe.akka" %% "akka-stream" % "2.6.8").cross(CrossVersion.for3Use2_13)
libraryDependencies +=( "com.typesafe.akka" %% "akka-actor-typed" % "2.6.8").cross(CrossVersion.for3Use2_13)
libraryDependencies +=( "com.typesafe.akka" %% "akka-actor" % "2.6.8").cross(CrossVersion.for3Use2_13)

libraryDependencies +=("com.typesafe.slick" %% "slick-hikaricp" % "3.3.3").cross(CrossVersion.for3Use2_13)
libraryDependencies +=("com.github.slick.slick" % "slick_3" % "nafg~dottyquery-SNAPSHOT")

resolvers += "jitpack" at "https://jitpack.io"
libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.7.36"
libraryDependencies += ("org.postgresql" % "postgresql" % "9.4-1200-jdbc41")



