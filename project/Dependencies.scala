import play.sbt.PlayImport.{evolutions, guice, jdbc, ws}
import sbt._

object Dependencies {

  val databaseDependencies = Seq(
    "org.postgresql" % "postgresql" % "42.2.5",
    "com.typesafe.play" %% "anorm" % "2.6.0-M1"
  )

  val jsonDependencies = Seq(
    "com.typesafe.play" %% "play-json" % "2.7.3"
  )

  val testDependencies = Seq(
    "org.scalatest" %% "scalatest" % "3.0.7",
    "org.scalacheck" %% "scalacheck" % "1.14.0",
    "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.2" % Test
  )

  val logDependencies = Seq(
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
  )

  val catsDependencies = Seq(
    "org.typelevel"                %% "cats-effect"              % "1.1.0"
  )

  val apiDependencies = Seq(
    guice,
    jdbc,
    ws,
    evolutions,
    "com.beachape" %% "enumeratum" % "1.5.13",
    "com.typesafe" % "config" % "1.3.4"
  ) ++ databaseDependencies ++ testDependencies ++logDependencies ++ jsonDependencies ++ catsDependencies ++ catsDependencies

}
