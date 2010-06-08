package net.badgerhunt.quotes.notify

import net.badgerhunt.quotes.ConfigParser
import javax.mail._
import internet.{InternetAddress, MimeMessage}
import java.util.{Date, Properties}
import javax.mail.Message.RecipientType.TO

object Email {
  val properties = new Properties
  val (host, from) = ConfigParser.smtp
  properties.put("mail.smtp.host", host)
  println("SMTP Host is %s".format(host))

  def remind(address: String, messages: List[String]) = email(address, "Reminder: %d triggered alerts".format(messages.size), 
    "This is a daily reminder about the stocks which have breached alert levels:", messages)

  def notify(address: String, messages: List[String]) = email(address, "%d alerts".format(messages.size), 
    "The following stocks have recently breached their alert level:", messages)

  private def email(address: String, subject: String, intro: String, messages: List[String]) = {
    print("Sending email from %s to %s ... ".format(from, address))
    val session = Session.getDefaultInstance(properties, null)
    val msg = new MimeMessage(session)
    msg.setFrom(new InternetAddress(from))
    msg.setSubject("[ASXNotify] %s".format(subject))
    msg.setText("%s\n\n%s".format(intro, messages.sort(_<_).mkString("\n")))
    msg.setSentDate(new Date)
    msg.setRecipients(TO, address)
    Transport.send(msg)
    println("email sent")
  }
}

