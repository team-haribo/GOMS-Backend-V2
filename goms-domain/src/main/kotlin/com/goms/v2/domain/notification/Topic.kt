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
        title = "오늘 수요 외출제 시행하지 않습니다.",
        content = "저번주 외출제 지각생이 3명 이상이여서 외출제 없습니다. \n" +
                "오늘 나가면 무단 외출로 간주합니다."
    ),

    AFTER_OUTING(
        title = "금일 외출 시간이 5분 남았습니다.",
        content = "외출중이신 분들은 빠르게 학교로 복귀해주세요\n! \n"
    );

}

