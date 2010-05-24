package net.badgerhunt.quotes

import xml.XML

object ConfigParser {
  val xml = XML.load(getClass.getResourceAsStream("/config.xml"))

  def smtp: (String, String) = {
    val smtp = xml \\ "smtp"    
    ((smtp \ "host").text, (smtp \ "fromAddress").text)
  }

  def parse: List[NotifyRule] = {
    val recipientBlocks = xml \\ "recipient"
    recipientBlocks.foldLeft(Nil: List[NotifyRule]) { (rules, nextRecipient) =>
      val email = (nextRecipient \ "@email").text
      val rulesForRecipient = (nextRecipient \ "trigger").foldLeft(Nil: List[NotifyRule]) { (triggers, nextTrigger) =>

        def attrToOptionalDouble(attr: String) = (nextTrigger \ attr).map(_.text.toDouble).toList match {
          case Nil => None
          case head :: tail => Some(head)
        }

        val stock = (nextTrigger \ "@stock").text
        val bottom = attrToOptionalDouble("@below")
        val top = attrToOptionalDouble("@above")
        val range = NonEmptyRange(bottom, top)
        NotifyRule(email, stock, range) :: triggers
      }
      rulesForRecipient ::: rules
    }
  }
}
