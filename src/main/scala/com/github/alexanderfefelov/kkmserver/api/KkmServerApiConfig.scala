package com.github.alexanderfefelov.kkmserver.api

import com.typesafe.config.ConfigFactory

object KkmServerApiConfig {

  private val config = ConfigFactory.load()

  val url: String = config.getString("kkmserver.url")
  val username: String = config.getString("kkmserver.username")
  val password: String = config.getString("kkmserver.password")

}
