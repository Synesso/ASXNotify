package net.badgerhunt.quotes

case class NotifyRule(email: String, stock: String, range: NonEmptyRange) {

  private val formatter = new java.text.DecimalFormat("0.00")
  private val aboveBreach = (price: Double, limit: Double) => if (price > limit) {
    Some("Price of %s of %s is above %s".format(stock, formatter.format(price), formatter.format(limit)))
  } else None
  private val belowBreach = (price: Double, limit: Double) => if (price < limit) {
    Some("Price of %s of %s is below %s".format(stock, formatter.format(price), formatter.format(limit)))
  } else None

  def notification(price: Double): List[String] = range.fold(belowBreach(price, _), aboveBreach(price, _)).flatMap(_.toList)

}
