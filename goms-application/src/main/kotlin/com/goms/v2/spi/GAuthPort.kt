package com.goms.v2.spi

import gauth.GAuthToken

interface GAuthPort {
    fun receiveGAuthToken(code: String): GAuthToken
}