name := "scala-nm-3"

version := "0.1"

scalaVersion := "2.13.5"

libraryDependencies  ++= Seq(
  // Last stable release
  "org.scalanlp" %% "breeze" % "1.2",

  // Native libraries are not included by default. add this if you want them
  // Native libraries greatly improve performance, but increase jar sizes.
  // It also packages various blas implementations, which have licenses that may or may not
  // be compatible with the Apache License. No GPL code, as best I know.
  "org.scalanlp" %% "breeze-natives" % "1.2",

  // The visualization library is distributed separately as well.
  // It depends on LGPL code
  "org.scalanlp" %% "breeze-viz" % "1.2"
)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.7" % "test"
// https://mvnrepository.com/artifact/com.github.haifengl/smile-scala
libraryDependencies += "com.github.haifengl" %% "smile-scala" % "2.6.0"

libraryDependencies ++= Seq(
  "org.bytedeco" % "openblas"  % "0.3.10-1.5.4" classifier "windows-x86_64" ,
  "org.bytedeco" % "javacpp"   % "1.5.4"        classifier "windows-x86_64" ,
  "org.bytedeco" % "arpack-ng" % "3.7.0-1.5.4"  classifier "windows-x86_64"
)