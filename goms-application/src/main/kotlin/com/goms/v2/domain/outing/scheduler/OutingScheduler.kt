package com.goms.v2.domain.outing.scheduler

import com.goms.v2.domain.late.usecase.SaveLateAccountUseCase
import com.goms.v2.domain.outing.usecase.DeleteOutingStudentsUseCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class OutingScheduler(
    private val saveLateAccountUseCase: SaveLateAccountUseCase,
    private val deleteOutingStudentsUseCase: DeleteOutingStudentsUseCase,
) {

    @Scheduled(cron = "0 26 19 ? * 3") // 매주 수요일 7시 26분에 지각자를 저장한다.
    fun checkRateStudent() = saveLateAccountUseCase.execute()

    @Scheduled(cron = "0 50 19 ? * 3") // 매주 수요일 7시 50분에 외출자를 삭제한다.
    fun deleteOutingStudents() = deleteOutingStudentsUseCase.execute()

}