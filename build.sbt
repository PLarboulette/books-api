import sbt.Keys.libraryDependencies
import Dependencies._

name := """book-api"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)

scalaVersion := "2.12.8"

crossScalaVersions := Seq("2.12.8", "2.11.12")

libraryDependencies ++= apiDependencies
