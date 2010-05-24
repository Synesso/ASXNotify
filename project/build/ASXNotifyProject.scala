import sbt._

class ASXNotifyProject(info: ProjectInfo) extends DefaultProject(info) {
  val mail = "javax.mail" % "mail" % "1.4.1"
  val activation = "javax.activation" % "activation" % "1.1.1"
}

