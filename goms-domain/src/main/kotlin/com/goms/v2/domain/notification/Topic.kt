package com.goms.v2.domain.notification

import com.goms.v2.common.annotation.Aggregate

@Aggregate
enum class Topic(
    val title: String,
    val content: String
) {

    BEFORE_OUTING(
        title = "오늘 수요 외출제 시행합니다!",
        content = "꼭 나가기 전 후로 GOMS 앱을 통해 QR을 찍어주세요! \n"
    ),

    GROUNDED(
        title = "외출제 금지",
        content = "저번주 외출제 지각생이므로 외출 금지 대상입니다. \n"
    ),

    AFTER_OUTING(
        title = "금일 외출 시간이 5분 남았습니다.",
        content = "외출중이신 분들은 빠르게 학교로 복귀해주세요\n! \n"
    );

}

