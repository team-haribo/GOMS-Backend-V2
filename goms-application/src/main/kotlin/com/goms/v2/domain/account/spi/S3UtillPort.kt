package com.goms.v2.domain.account.spi

import org.springframework.web.multipart.MultipartFile

interface S3UtillPort {
    fun upload(image: MultipartFile): String
}