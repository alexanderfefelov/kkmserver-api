name := "kkmserver-api"
organization := "com.github.alexanderfefelov"

crossScalaVersions := Seq("2.11.12", "2.12.8")

resolvers += Resolver.jcenterRepo

libraryDependencies ++= Seq(
  "ca.aaronlevin" %% "scala-gitrev" % "0.1.1",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.lihaoyi" %% "requests" % "0.1.8",
  "com.typesafe.play" %% "play-json-joda" % "2.7.3",
  "com.typesafe.play" %% "play-json" % "2.7.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "com.typesafe" % "config" % "1.3.4",
  "io.dropwizard" % "dropwizard-metrics-graphite" % "1.3.11",
  "io.seruco.encoding" % "base62" % "0.1.2",
  "joda-time" % "joda-time" % "2.10.2",
  "nl.grons" %% "metrics4-scala" % "4.0.5",
  "nl.grons" %% "metrics4-scala-hdr" % "4.0.5",
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
