package net.badgerhunt.quotes

object NonEmptyRange {
  def apply(bottom: Double, top: Double) = new NonEmptyRange(Some(bottom), Some(top))
  def above(bottom: Double) = new NonEmptyRange(Some(bottom), None)
  def below(top: Double) = new NonEmptyRange(None, Some(top))
}

case class NonEmptyRange(bottom: Option[Double], top: Option[Double]) {
  assert(bottom != None || top != None)

  def fold[T](bottomFunction: Double => T, topFunction: Double => T): List[T] = {
    List(bottom.map(bottomFunction), top.map(topFunction)).flatMap(_.toList)
  }

  override def toString = fold("above %f".format(_), "below %f".format(_)).mkString(" and ")
}