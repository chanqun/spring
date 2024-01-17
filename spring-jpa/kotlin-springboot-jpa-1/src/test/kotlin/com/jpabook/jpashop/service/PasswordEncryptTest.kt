package com.jpabook.jpashop.service

import org.assertj.core.api.Assertions.assertThat
import org.jasypt.util.password.StrongPasswordEncryptor
import org.junit.jupiter.api.Test

internal class PasswordEncryptTest {
    @Test
    fun `Encryptor 기능 확인`() {
        val encryptor = StrongPasswordEncryptor()
        val encryptPassword = encryptor.encryptPassword("1234")

        assertThat(encryptor.checkPassword("1234", encryptPassword)).isTrue
        assertThat(encryptor.checkPassword("12345", encryptPassword)).isFalse
    }
}
