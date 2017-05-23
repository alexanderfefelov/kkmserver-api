name := "kkmserver-api"
organization := "alexanderfefelov.github.com"

scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  "com.typesafe.play" % "play-ws_2.11" % "2.4.11",
  "joda-time" % "joda-time" % "2.9.9",
  "ca.aaronlevin" %% "scala-gitrev" % "0.1.1"
)

lazy val root = (project in file("."))
  .enablePlugins(BuildInfoPlugin)
  .settings(
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion,
      "builtBy" -> {System.getProperty("user.name")},
      "builtOn" -> {java.net.InetAddress.getLocalHost.getHostName},
      "builtAt" -> {new java.util.Date()},
      "builtAtMillis" -> {System.currentTimeMillis()}
    ),
    buildInfoPackage := "ru.kkmserver"
  )

doc in Compile := target.map(_ / "none").value
