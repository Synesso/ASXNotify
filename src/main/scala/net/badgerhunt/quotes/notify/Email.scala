package net.badgerhunt.quotes.notify

import net.badgerhunt.quotes.ConfigParser
import javax.mail._
import javax.mail.internet._
import java.util.{Date, Properties}

object Email {
  val properties = new Properties
  val (host, from) = ConfigParser.smtp
  properties.put("smtp.mail.host", host)
  val session = Session.getDefaultInstance(properties, null)


  def notify(s: String) = {
    val msg = new MimeMessage(session)
    msg.setFrom(new InternetAddress(from))
   //o  msg.setRecipients
    msg
  }
}
