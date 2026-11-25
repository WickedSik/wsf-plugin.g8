// ---------------------------------------------------------------------------
// $name$ — external plugin for ws-forge.
//
// This is a THIN plugin JAR. Every class the host already owns is marked
// `Provided`, so `sbt package` bundles ONLY this project's own classes. At load
// time the host classpath supplies the shared classes (parent-first), so the
// plugin shares the host's single copy of Tool/ToolPlugin/ZIO/circe. Bundling
// any of them would produce class-identity mismatches (ClassCastException).
//
// The API is pinned to ws-forge tag v0.4.0 (plugin API version 3). Do not use
// `latest.integration`: it cannot order dynver versions such as
// 0.1.0+10-de7bb5b8 and 0.1.0+54-763a29cc. Bump the pin with each host tag.
//
// See docs/plugins.md in ws-forge for the full plugin contract.
// ---------------------------------------------------------------------------

ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.9.0" // same version as the host (TASTy compatibility)
ThisBuild / organization := "$organization$"

lazy val zioVersion = "2.1.21"
lazy val circeVersion = "0.14.15"

lazy val root = (project in file("."))
  .settings(
    name := "$name$",
    description := "$description$",
    libraryDependencies ++= Seq(
      // The plugin SPI — compile against it, never bundle it.
      "io.github.wickedsik" %% "ws-forge-api" % "0.4.0" % Provided,

      // Shared runtime the host already provides.
      "dev.zio" %% "zio" % zioVersion % Provided,
      "io.circe" %% "circe-core" % circeVersion % Provided,
      "io.circe" %% "circe-generic" % circeVersion % Provided,

      // Testing (host does not provide these; scoped to Test only).
      "dev.zio" %% "zio-test" % zioVersion % Test,
      "dev.zio" %% "zio-test-sbt" % zioVersion % Test
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
