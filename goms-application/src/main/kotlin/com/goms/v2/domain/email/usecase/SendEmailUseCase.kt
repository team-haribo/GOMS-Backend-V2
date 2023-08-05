package com.goms.v2.domain.email.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.domain.email.EmailAuth
import com.goms.v2.domain.email.data.dto.EmailRequestDto
import com.goms.v2.domain.email.exception.EmailSendFailException
import com.goms.v2.domain.email.exception.ManyEmailRequestException
import com.goms.v2.repository.email.EmailAuthRepository
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import javax.mail.MessagingException

@UseCaseWithTransaction
class SendEmailUseCase(
    private val mailSender: JavaMailSender,
    private val emailAuthRepository: EmailAuthRepository
) {

    @Async
    fun execute(emailRequestDto: EmailRequestDto) {
        val authKey = generateCertificationNumber(9999)
        if (!validationEmail(emailRequestDto.email)) throw AccountNotFoundException()
        sendEmail(emailRequestDto.email, authKey)
    }

    @Async
    fun sendEmail(email: String, authKey: String) {
        val subject = "GOMS 인증번호"
        val content = "GOMS 인증번호는 " + authKey + "입니다"
        val emailAuth = emailAuthRepository.findByIdOrNull(email)
            ?: EmailAuth(email,authKey,false,0,1800)
        if(emailAuth.attemptCount >=5) throw ManyEmailRequestException()

        emailAuth.updateRandomValue(authKey)
        emailAuth.updateAuthentication(true)
        emailAuth.increaseAttemptCount()
        emailAuthRepository.save(emailAuth)

        try {
            val mimeMailMessage = mailSender.createMimeMessage()
            val helper = MimeMessageHelper(mimeMailMessage,true,"utf8")
            helper.setTo(email)
            helper.setSubject(subject)
            helper.setText(content)
            mailSender.send(mimeMailMessage)
        } catch (e : MessagingException) {
            throw EmailSendFailException()
        }

    }

    fun validationEmail(email: String): Boolean =
        emailAuthRepository.existByEmail(email)

    fun generateCertificationNumber(number: Int = 9999) = (0..number).random()
        .toString()
        .padStart(number.toString().length, '0')

}