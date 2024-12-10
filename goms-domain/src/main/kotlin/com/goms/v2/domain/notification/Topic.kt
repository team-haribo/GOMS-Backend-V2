package com.goms.v2.domain.notification

import com.goms.v2.common.annotation.Aggregate

@Aggregate
enum class Topic(
    val title: String,
    val content: String
) {

    FIRST_NOTIFICATION(
        title = "[GOMS] 개발팀",
        content = "✅ㅣ오늘 외출제 시행합니다!\n" +
            "추가사항은 GSM 디스코드를 확인해주세요."
    ),

    FINAL_NOTIFICATION(
        title = "[GOMS] 개발팀",
        content = "⌛ㅣ잠시 후 외출제가 시작됩니다!\n" +
            "신발을 꼭 착용해주세요. (크록스 X)"
    ),

    DENIED_NOTIFICATION(
        title = "[GOMS] 개발팀",
        content = "❌ㅣ오늘 외출제는 시행하지 않습니다!\n" +
            "외출 시 무단 외출 처리됩니다."
    ),
}

