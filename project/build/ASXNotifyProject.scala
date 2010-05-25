import sbt._

class ASXNotifyProject(info: ProjectInfo) extends DefaultProject(info) {
  val mail = "javax.mail" % "mail" % "1.4.1"
  val activation = "javax.activation" % "activation" % "1.1.1"

   val specs = "org.scala-tools.testing" % "specs" % "1.6.2.1" % "test->default"
}

