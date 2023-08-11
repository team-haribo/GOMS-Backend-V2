package com.goms.v2.global.config

import com.goms.v2.common.annotation.UseCaseWithReadOnlyTransaction
import com.goms.v2.common.annotation.UseCaseWithTransaction
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ComponentScan.Filter
import org.springframework.context.annotation.FilterType

@Configuration
@ComponentScan(
    basePackages = ["com.goms.v2"],
    includeFilters = [
        Filter(
            type = FilterType.ANNOTATION,
            classes = [
                UseCaseWithTransaction::class,
                UseCaseWithReadOnlyTransaction::class
            ]
        )
    ]
)
class ComponentScanConfig