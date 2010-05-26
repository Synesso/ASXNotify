package net.badgerhunt.quotes

import java.util.{Timer, TimerTask, Date}
import notify.Email

object Watcher extends Application {
  val TWENTY_MINUTES = 1000 * 60 * 20l

  val config = ConfigParser.parse
  val emails = config.foldLeft(Set(): Set[String]) { _ + _.email }
  val stocks = config.foldLeft(Set(): Set[String]) { _ + _.stock }
  val rulesByEmail = config.foldLeft(Map[String, List[NotifyRule]]()) { (acc, next) => acc(next.email) = next :: acc.getOrElse(next.email, Nil) }

  println("Notification rules:")
  rulesByEmail.foreach{rule =>
    println(rule._1);
    rule._2.foreach{n =>
      println("\t%s when not (%s)".format(n.stock, n.range))
    }
  }

  val timer = new Timer(false)
  timer.schedule(UpdateAndEmailTask, new Date, TWENTY_MINUTES)

  object UpdateAndEmailTask extends TimerTask {
    def run() = {
      if (MarketTimes.marketIsOpen) {
        println("Updating prices")
        val quotes = QuoteSource.current(stocks).foldLeft(Map[String,Double]()){(acc,next)=>acc(next.code)=next.closingPrice}
        quotes.map(quote => "\t%s -> %f".format(quote._1, quote._2)).toList.sort(_<_).foreach(println)

        rulesByEmail.foreach{rulesForEmail =>
          val (email, rules) = rulesForEmail
          val messages = rules.flatMap(rule => rule.notification(quotes(rule.stock)))
          println("%s -> %s".format(email, messages))
          Email.notify(email, messages)
        }
      } else {
        println("Market is closed")
      }
    }
  }
}


