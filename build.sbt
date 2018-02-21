// shadow sbt-scalajs' crossProject and CrossType until Scala.js 1.0.0 is released
import sbtcrossproject.{crossProject, CrossType}

val scalajvm = List("2.11.12", "2.12.4", "2.13.0-M1")
val scalajs = List("2.11.12", "2.12.4")
val scalanative = List("2.11.12")

version in ThisBuild := {
  import sys.process._
  ("git describe --always --dirty=-SNAPSHOT --match v[0-9].*" !!).tail.trim
}

lazy val core = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .settings(
    name := "spray-json",
    organization := "io.crashbox",
    scalacOptions ++= Seq("-feature", "-language:_", "-unchecked", "-deprecation", "-Xlint", "-encoding", "utf8"),
    libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value,
    libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaVersion.value
  )
  .jvmSettings(
    crossScalaVersions := scalajvm
  )
  .jsSettings(
    crossScalaVersions := scalajs
  )
  .nativeSettings(
    crossScalaVersions := scalanative
  )

lazy val coreJVM = core.jvm
lazy val coreJS = core.js
lazy val coreNative = core.native

lazy val tests = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .settings(
    name := "spray-json-tests",
    scalaVersion := "2.11.12",
    publish := {},
    publishLocal := {}
  )
  .dependsOn(core)
  .jvmSettings(
    crossScalaVersions := scalajvm
  )
  .jsSettings(
    crossScalaVersions := scalajs,
    scalaJSUseMainModuleInitializer := true
  )
  .nativeSettings(
    crossScalaVersions := scalanative
  )

lazy val testJVM = tests.jvm
lazy val testJS = tests.js
lazy val testNative = tests.native
