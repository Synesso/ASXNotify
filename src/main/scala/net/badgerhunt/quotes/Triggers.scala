package net.badgerhunt.quotes

object Triggers {

  def lessThan(price: Double): (Double) => Boolean = _ < price
  def greaterThan(price: Double): (Double) => Boolean = _ > price

}