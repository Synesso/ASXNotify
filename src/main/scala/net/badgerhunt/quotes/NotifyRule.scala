package net.badgerhunt.quotes

case class NotifyRule(email: String, stock: String, trigger: (Double) => Boolean)