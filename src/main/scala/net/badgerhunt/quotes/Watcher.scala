package net.badgerhunt.quotes

import java.util.{Timer, TimerTask, Date}

object Watcher extends Application {
  val TWENTY_MINUTES = 1000 * 60 * 20l

  val config = ConfigParser.parse
  val emails = config.foldLeft(Set(): Set[String]) { _ + _.email }
  val stocks = config.foldLeft(Set(): Set[String]) { _ + _.stock }

  println(emails)
  println(stocks)

  val timer = new Timer(false)
  timer.schedule(UpdateAndEmailTask, new Date, TWENTY_MINUTES)

  object UpdateAndEmailTask extends TimerTask {
    def run() = {
      if (MarketTimes.marketIsOpen) {
        val quotes = QuoteSource.current(stocks)
        println(quotes)
      } else {
        println("Market is closed")
      }
    }

  }
}


