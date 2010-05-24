package net.badgerhunt.quotes

import java.text.SimpleDateFormat

object QuoteSource {
  val dateFormat = new SimpleDateFormat("\"MM/dd/yyyy\"")

  def current(codes: Set[String]): List[Quote] = {
    val stockParam = codes.map(c => "%s.AX".format(c)).mkString(",")
    val url = "http://download.finance.yahoo.com/d/quotes.csv?s=%s&f=sl1d1&e=.csv".format(stockParam)
    val data = io.Source.fromURL(url).getLines
    data.map(_.trim.split(",")).map(line => Quote(line(0).substring(1,4), dateFormat.parse(line(2)), line(1).toDouble)).toList
  }
}
