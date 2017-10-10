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

package com.github.alexanderfefelov.kkmserver.api.metrics

import java.net.{InetAddress, InetSocketAddress}
import java.util.concurrent.TimeUnit

import com.codahale.metrics.graphite.{GraphiteReporter, GraphiteUDP}
import com.codahale.metrics.{MetricFilter, MetricRegistry}
import com.github.alexanderfefelov.kkmserver.api.KkmServerApiConfig

object MetricsManager {

  val metricRegistry: MetricRegistry = new MetricRegistry()
  if (KkmServerApiConfig.graphiteEnabled) {
    val graphite: GraphiteUDP = new GraphiteUDP(new InetSocketAddress(KkmServerApiConfig.graphiteHost, KkmServerApiConfig.graphitePort))
    val graphiteReporter = GraphiteReporter.forRegistry(metricRegistry)
      .prefixedWith(s"${KkmServerApiConfig.graphitePrefix}.${InetAddress.getLocalHost.getHostName}")
      .convertRatesTo(TimeUnit.SECONDS)
      .convertDurationsTo(TimeUnit.MILLISECONDS)
      .filter(MetricFilter.ALL)
      .build(graphite)
    graphiteReporter.start(1, TimeUnit.MINUTES)
  }

}
