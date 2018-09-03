/*
 * Copyright (c) 2017-2018 Alexander Fefelov <alexanderfefelov@yandex.ru>
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

package com.github.alexanderfefelov.kkmserver.api

import com.typesafe.config.ConfigFactory

// Default values comes from reference.conf as recommended by official documentation.

object KkmServerApiConfig {

  val CONFIG_PREFIX_API: String = "kkmserver-api"
  val CONFIG_PREFIX_KKMSERVER: String = s"$CONFIG_PREFIX_API.kkmserver"
  val CONFIG_PREFIX_GRAPHITE: String = s"$CONFIG_PREFIX_API.graphite"

  private val config = ConfigFactory.load()

  val kkmServerUrl: String = config.getString(s"$CONFIG_PREFIX_KKMSERVER.url")
  val kkmServerUsername: String = config.getString(s"$CONFIG_PREFIX_KKMSERVER.username")
  val kkmServerPassword: String = config.getString(s"$CONFIG_PREFIX_KKMSERVER.password")
  val kkmServerConnectTimeout: Int = config.getInt(s"$CONFIG_PREFIX_KKMSERVER.connect-timeout")
  val kkmServerReadTimeout: Int = config.getInt(s"$CONFIG_PREFIX_KKMSERVER.read-timeout")

  val graphiteEnabled: Boolean = config.getBoolean(s"$CONFIG_PREFIX_GRAPHITE.enabled")
  val graphiteHost: String = config.getString(s"$CONFIG_PREFIX_GRAPHITE.host")
  val graphitePort: Int = config.getInt(s"$CONFIG_PREFIX_GRAPHITE.port")
  val graphitePrefix: String = config.getString(s"$CONFIG_PREFIX_GRAPHITE.prefix")

}
