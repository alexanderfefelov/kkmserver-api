name := "demo"
organization := "alexanderfefelov.github.com"

scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  "alexanderfefelov.github.com" %% "kkmserver-api" % "0.0-SNAPSHOT",
  "com.github.nikita-volkov" % "sext" % "0.2.4"
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)

doc in Compile := target.map(_ / "none").value
