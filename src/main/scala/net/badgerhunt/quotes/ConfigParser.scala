package net.badgerhunt.quotes

import xml.XML

object ConfigParser {
  def parse: List[NotifyRule] = {
    val xml = XML.load(getClass.getResourceAsStream("/config.xml"))
    val recipientBlocks = xml \\ "recipient"
    recipientBlocks.foldLeft(Nil: List[NotifyRule]) { (rules, nextRecipient) =>
      val email = (nextRecipient \ "@email").text
      val rulesForRecipient = (nextRecipient \ "trigger").foldLeft(Nil: List[NotifyRule]) { (triggers, nextTrigger) =>
        val stock = (nextTrigger \ "@stock").text
        val threshold = (nextTrigger \ "@price").text.toDouble
        val rule: (Double) => Boolean = {
          (nextTrigger \ "@when").text match {
            case "below" => Triggers.lessThan(threshold)
            case "above" => Triggers.greaterThan(threshold)
            case x => throw new RuntimeException("Unknown @when '%s'".format(x))
          }
        }
        NotifyRule(email, stock, rule) :: triggers
      }
      rulesForRecipient ::: rules
    }
  }
}