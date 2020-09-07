name := """SerienDatenBank"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

libraryDependencies ++= List(
  "com.typesafe.slick" %% "slick" % "3.3.2",
)
libraryDependencies += "com.h2database" % "h2" % "1.4.192"
