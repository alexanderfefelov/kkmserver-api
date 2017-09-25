package com.github.alexanderfefelov.kkmserver.api.protocol

case class KkmServerApiException(message: String) extends Exception(message)
