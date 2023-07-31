package com.goms.v2.thirdparty.gauth.config

import gauth.GAuth
import gauth.impl.GAuthImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GAuthConfig {

    @Bean
    fun gauth(): GAuth = GAuthImpl()

}