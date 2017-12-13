name := "kkmserver-api-demo"
organization := "alexanderfefelov.github.com"

scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  "alexanderfefelov.github.com" %% "kkmserver-api" % "0.2.0",
  "com.github.nikita-volkov" % "sext" % "0.2.4",
  "org.webjars" % "jquery" % "3.2.1",
  "org.webjars" % "bootstrap" % "3.3.7"
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)

doc in Compile := target.map(_ / "none").value
