package net.badgerhunt.quotes


import java.util.Calendar._
import java.util.{GregorianCalendar}
object MarketTimes {
  val WORKING_DAYS = Set(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY)
  def marketIsOpen = {
    val now = new GregorianCalendar
    WORKING_DAYS.contains(now.get(DAY_OF_WEEK)) && now.get(HOUR_OF_DAY) >= 10 && now.get(HOUR_OF_DAY) < 16
  }
}
