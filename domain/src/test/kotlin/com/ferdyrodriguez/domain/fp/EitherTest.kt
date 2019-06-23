package com.ferdyrodriguez.domain.fp

import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EitherTest {

    @Test fun `Either Right should return correct type`() {
        val result = Either.Right("test_string")

        result shouldBeInstanceOf Either::class.java
        result.isRight shouldBe true
        result.isLeft shouldBe false
        result.either({},
            { right ->
                right shouldBeInstanceOf String::class.java
                right shouldBeEqualTo "test_string"
            })
    }

    @Test fun `Either Left should return correct type`() {
        val result = Either.Left("test_string")

        result shouldBeInstanceOf Either::class.java
        result.isLeft shouldBe true
        result.isRight shouldBe false
        result.either(
            { left ->
                left shouldBeInstanceOf String::class.java
                left shouldBeEqualTo "test_string"
            }, {})
    }

}