name := """ngs2-user-router"""

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  guice,
  evolutions,
  "com.h2database" % "h2" % "1.4.193",
  "org.mindrot" % "jbcrypt" % "0.4",
  "org.apache.commons" % "commons-csv" % "1.4"
)

playEbeanModels in Compile := Seq("models.*")
playEbeanDebugLevel := 4