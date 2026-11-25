// Template project for `sbt new WickedSik/wsf-plugin.g8`. Run `sbt g8Test` to
// generate a plugin from the defaults and run src/test/g8/test against it.
//
// Do not enable ScriptedPlugin here. sbt-giter8 already applies its settings, and
// enabling it resets them: g8Test then finds no tests, passes, and runs publishLocal.
lazy val root = (project in file("."))
  .settings(
    name := "wsf-plugin.g8",
    scalaVersion := "2.12.21", // the template itself has no sources; this only silences a warning
    publish / skip := true,
    publishLocal / skip := true,
    Test / test := (Test / g8Test).toTask("").value,
    scriptedLaunchOpts ++= Seq("-Xmx1024m"),
    scriptedBufferLog := false
  )
