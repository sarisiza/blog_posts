package com.upakon.randomquote.utils

object RequestError : Exception("Error in request")

object EmptyResponse : Exception("Response is empty")

object NullResponseBody : Exception("Body is null")