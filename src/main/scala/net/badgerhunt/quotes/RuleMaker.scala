package net.badgerhunt.quotes

class RuleMaker(email: String, val rules: List[NotifyRule]) {
  def this(email: String) = this(email, Nil)

  def notify(stock: String, trigger: (Double) => Boolean): RuleMaker = {
    new RuleMaker(email, new NotifyRule(email, stock, trigger) :: rules)
  }
}