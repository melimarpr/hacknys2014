name := "foursquarerpgserver"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  	 "com.google.code.gson"%"gson"%"2.2.4",
	 "com.google.http-client"%"google-http-client"%"1.17.0-rc",
	 "com.google.http-client"%"google-http-client-gson"%"1.17.0-rc",
	 "com.google.http-client"%"google-http-client-jackson"%"1.17.0-rc"
)     

play.Project.playScalaSettings
