import net.thunderklaus.GwtPlugin._

seq(gwtSettings: _*)

name := "ngram4web"

version := "0.1-SNAPSHOT"

organization := "net.thunderklaus"

 libraryDependencies ++= Seq(
  "com.google.appengine" % "appengine-api-1.0-sdk" % "1.5.3",
  "com.google.appengine" % "appengine-testing" % "1.5.3" % "test",
  "com.google.appengine" % "appengine-api-stubs" % "1.5.3" % "test",
  "com.google.appengine" % "appengine-api-labs" % "1.5.3" % "test")