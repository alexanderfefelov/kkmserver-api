package ru.kkmserver

import org.joda.time.DateTime
import play.api.libs.json.Reads

package object protocol {

  val jodaReadsShort: Reads[DateTime] = Reads.jodaDateReads("yyyy-MM-dd'T'HH:mm:ss")
  val jodaReadsLong: Reads[DateTime] = Reads.jodaDateReads("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSZ")

}
