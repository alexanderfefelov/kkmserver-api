name := "kkmserver-api"
organization := "com.github.alexanderfefelov"

scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-ws" % "2.4.11",
  "joda-time" % "joda-time" % "2.9.9",
  "nl.grons" %% "metrics-scala" % "3.5.9",
  "io.dropwizard" % "dropwizard-metrics-graphite" % "1.2.0",
  "org.mpierce.metrics.reservoir" % "hdrhistogram-metrics-reservoir" % "1.1.2",
  "ca.aaronlevin" %% "scala-gitrev" % "0.1.1",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
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
    buildInfoPackage := "com.github.alexanderfefelov.kkmserver.api"
  )

doc in Compile := target.map(_ / "none").value

logBuffered in Test := false
