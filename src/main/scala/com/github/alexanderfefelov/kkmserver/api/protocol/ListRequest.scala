/*
 * Copyright (c) 2017 Alexander Fefelov <alexanderfefelov@yandex.ru>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished
 * to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 *
 */

package com.github.alexanderfefelov.kkmserver.api.protocol

import org.joda.time.DateTime
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class ListRequest (
  // Meta
  //
  Command: String = COMMAND_LIST,
  IdCommand: String = createUuid,
  // Device selector
  //
  NumDevice: Option[Int] = None,
  InnKkm: Option[String] = None,
  IP: Option[String] = None,
  Active: Option[Boolean] = None,
  OnOff: Option[Boolean] = None,
  OFD_Error: Option[Boolean] = None,
  OFD_DateErrorDoc: Option[DateTime] = None,
  FN_DateEnd: Option[DateTime] = None,
  FN_MemOverflowl: Option[Boolean] = None,
  FN_IsFiscal: Option[Boolean] = None
)

object ListRequest {

  implicit val listRequestWrites: Writes[ListRequest] = (
    (__ \ "Command").write[String] and
    (__ \ "IdCommand").write[String] and
    (__ \ "NumDevice").writeNullable[Int] and
    (__ \ "InnKkm").writeNullable[String] and
    (__ \ "IP").writeNullable[String] and
    (__ \ "Active").writeNullable[Boolean] and
    (__ \ "OnOff").writeNullable[Boolean] and
    (__ \ "OFD_Error").writeNullable[Boolean] and
    (__ \ "OFD_DateErrorDoc").writeNullable[DateTime] and
    (__ \ "FN_DateEnd").writeNullable[DateTime] and
    (__ \ "FN_MemOverflowl").writeNullable[Boolean] and
    (__ \ "FN_IsFiscal").writeNullable[Boolean]
    )(unlift(ListRequest.unapply))

}
