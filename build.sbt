name := "Issuestant"

version := "1.0"

scalaVersion := "2.11.8"

val http4sVersion = "0.15.6"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "3.0.0" % "test",

  "io.circe" %% "circe-core" % "0.5.1",
  "io.circe" %% "circe-generic" % "0.5.1",
  "io.circe" %% "circe-parser" % "0.5.1",

  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion
)

wartremoverErrors in (Compile, compile) ++= Warts.unsafe

wartremoverErrors in Test ++= Warts.unsafe diff Seq(
  // We exclude only the Warts that are broken by idiomatically
  // correct ScalaTest code

  Wart.NonUnitStatements // All ScalaTest assertions return Assertion trait marker instead of Unit
)

enablePlugins(JavaAppPackaging)
