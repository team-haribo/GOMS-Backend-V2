package com.goms.v2.domain.outingDate.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.domain.outing.DeniedOutingDate
import com.goms.v2.domain.outingDate.data.dto.SetTodayOutingDto
import com.goms.v2.domain.outingDate.exception.DuplicatedOutingDateException
import com.goms.v2.domain.outingDate.exception.UnauthorizedDiscordClientTokenException
import com.goms.v2.repository.outingDate.DeniedOutingDateRepository
import org.springframework.beans.factory.annotation.Value
import java.time.LocalDate

@UseCaseWithTransaction
class SetTodayOutingUseCase(
	@Value("\${discord.api.token}")
	private val discordToken: String,
	private val deniedOutingDateRepository: DeniedOutingDateRepository,
) {

	fun execute(setTodayOutingDto: SetTodayOutingDto) {
		if(discordToken != setTodayOutingDto.token){
			throw UnauthorizedDiscordClientTokenException()
		}

		val nowDate = LocalDate.now()
		val isExistDeniedOutingDate = deniedOutingDateRepository.existsByOutingDate(nowDate)

		if(isExistDeniedOutingDate){
			if(!setTodayOutingDto.outingStatus) deniedOutingDateRepository.deleteByOutingDate(nowDate)
			else throw DuplicatedOutingDateException()
		} else {
			if(setTodayOutingDto.outingStatus){
				val newDeniedOutingDate = DeniedOutingDate(
					idx = -1,
					outingDate = LocalDate.now()
				)
				deniedOutingDateRepository.save(newDeniedOutingDate)
			} else throw DuplicatedOutingDateException()
		}
	}

}
