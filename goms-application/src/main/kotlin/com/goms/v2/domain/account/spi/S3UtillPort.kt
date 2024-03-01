package com.goms.v2.domain.account.spi

import com.goms.v2.domain.account.Account
import org.springframework.web.multipart.MultipartFile

interface S3UtilPort {
    fun validImage(image: MultipartFile): Account
    fun upload(image: MultipartFile): String
}