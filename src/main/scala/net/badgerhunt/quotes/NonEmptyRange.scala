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

  val sortOptions = (first: Option[Double], second: Option[Double]) =>
      (first, second) match {
        case (Some(a), Some(b)) => a < b
        case (Some(_), _) => true
        case _ => false
      }

  private def min(one: Option[Double], other: Option[Double]) = List(one, other).sort(sortOptions).head
  private def max(one: Option[Double], other: Option[Double]) = List(one, other).sort(sortOptions).filter(_.isDefined).reverse.head

  def intersection(range: NonEmptyRange) = NonEmptyRange(max(bottom, range.bottom), min(top, range.top))
  def union(range: NonEmptyRange) = NonEmptyRange(
      if (bottom == None || range.bottom == None) None else Some(Math.min(bottom.get, range.bottom.get)),
      if (top == None || range.top == None) None else Some(Math.max(top.get, range.top.get)))

  override def toString = fold("above %f".format(_), "below %f".format(_)).mkString(" and ")
}