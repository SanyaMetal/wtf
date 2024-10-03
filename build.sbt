import sbt.Keys.libraryDependencies

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.4"

lazy val root = (project in file("."))
  .settings(
    name := "todolist",
      libraryDependencies += "org.postgresql" % "postgresql" % "42.7.3",
  )


