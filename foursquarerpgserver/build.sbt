name := "foursquarerpgserver"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  javaJpa,
  "mysql" % "mysql-connector-java" % "5.1.18",
  "com.google.code.gson"%"gson"%"2.2.4"
)     

play.Project.playScalaSettings
