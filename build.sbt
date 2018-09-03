name := "kkmserver-api"
organization := "com.github.alexanderfefelov"

crossScalaVersions := Seq("2.11.12", "2.12.6")

resolvers += Resolver.jcenterRepo

libraryDependencies ++= Seq(
  "com.lihaoyi" %% "requests" % "0.1.4",
  "com.typesafe.play" %% "play-json" % "2.6.10",
  "com.typesafe.play" %% "play-json-joda" % "2.6.10",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe" % "config" % "1.3.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
  "joda-time" % "joda-time" % "2.10",
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
