package com.goms.v2.common.spi

import gauth.GAuthToken

interface GAuthPort {
    fun receiveGAuthToken(code: String): GAuthToken
}