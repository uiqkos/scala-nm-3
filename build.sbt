name := "scala-nm-3"

version := "0.1"

scalaVersion := "2.13.5"

libraryDependencies  ++= Seq(
  "org.scalanlp" %% "breeze" % "1.2",
  "org.scalanlp" %% "breeze-natives" % "1.2",
  "org.scalanlp" %% "breeze-viz" % "1.2"
)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.7" % "test"
libraryDependencies += "com.github.haifengl" %% "smile-scala" % "2.6.0"

libraryDependencies ++= Seq(
  "org.bytedeco" % "openblas"  % "0.3.10-1.5.4" classifier "windows-x86_64" ,
  "org.bytedeco" % "javacpp"   % "1.5.4"        classifier "windows-x86_64" ,
  "org.bytedeco" % "arpack-ng" % "3.7.0-1.5.4"  classifier "windows-x86_64"
)