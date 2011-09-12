import net.thunderklaus.GwtPlugin._

seq(gwtSettings: _*)

name := "ngram4web"

version := "0.1-SNAPSHOT"

organization := "net.thunderklaus"

autoScalaLibrary := true

scalacOptions ++= Seq("-encoding","UTF-8")

libraryDependencies ++= Seq(
    "org.specs2" %% "specs2" % "1.6.1" % "test",
    "org.specs2" %% "specs2-scalaz-core" % "6.0.1" % "test",
    "org.scala-tools.testing" % "scalacheck_2.9.0" % "1.9" % "test" 
)
