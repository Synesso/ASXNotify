package net.badgerhunt.quotes

import java.text.SimpleDateFormat

object QuoteSource {
  val dateFormat = new SimpleDateFormat("\"MM/dd/yyyy\"")
  def current(code: String): Option[Quote] = {
    try {
      val url = "http://download.finance.yahoo.com/d/quotes.csv?s=%s.AX&f=sl1d1t1c1ohgv&e=.csv".format(code)
      val data = io.Source.fromURL(url).mkString.trim.split(",")
      Some(Quote(code, dateFormat.parse(data(2)), data(1).toDouble))
    } catch {
      case _ => None
    }
  }

  def current(codes: Set[String]): List[Quote] = {
    val stockParam = codes.map(c => "%s.AX".format(c)).mkString(",")
    val url = "http://download.finance.yahoo.com/d/quotes.csv?s=%s&f=sl1d1&e=.csv".format(stockParam)
    println(">>%s".format(url))
    val data = io.Source.fromURL(url).getLines
    data.map(_.trim.split(",")).map(line => Quote(line(0).substring(1,3), dateFormat.parse(line(2)), line(1).toDouble)).toList
  }
}
