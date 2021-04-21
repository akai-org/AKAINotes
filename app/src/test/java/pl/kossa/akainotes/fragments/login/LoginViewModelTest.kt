package pl.kossa.akainotes.fragments.login

import app.cash.turbine.test
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Assert.*
import org.junit.Test
import pl.kossa.akainotes.repository.UsersRepository
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class LoginViewModelTest {

    private val userRepository = mockk<UsersRepository>()

    private val goodEmail = "jakub@akai.org"
    private val goodPassword = "password"
    private val badEmail = "bartek@akai.org"
    private val badPassword = "pass"
    private val badPatternEmail = "kuba"

    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        clearAllMocks()
        Dispatchers.setMain(dispatcher)

        every { userRepository.userAlreadyLoggedIn } returns flow { emit(false) }
        every { userRepository.email } returns goodEmail
        every { userRepository.password } returns goodPassword
    }

    private fun createSUT(): LoginViewModel {
        return LoginViewModel(userRepository)
    }

    @Test
    fun `when user already logged in, then emit login success`() = runBlocking {
        //given
        every { userRepository.userAlreadyLoggedIn } returns flowOf(true)

        //when
        val loginSuccess = createSUT().loginSuccessOrFailure

        //then
        loginSuccess.test {
            assertEquals(true, expectItem())
            cancel()
        }
    }

    @Test
    fun `when given correct email and password, then emit login success`() = runBlocking {
        //given

        //when
        val loginSuccessOrFailure = createSUT().apply {
            setEmail(goodEmail)
            setPassword(goodPassword)
            requestLogin()
        }.loginSuccessOrFailure

        //then
        loginSuccessOrFailure.test {
            assertEquals(true, expectItem())
            cancel()
        }
    }

    @Test
    fun `when given wrong email and password, then emit login failure`() = runBlocking {
        //given

        //when
        val loginSuccessOrFailure = createSUT().apply {
            setEmail(badEmail)
            setPassword(badPassword)
            requestLogin()
        }.loginSuccessOrFailure

        //then
        loginSuccessOrFailure.test {
            assertEquals(false, expectItem())
            cancel()
        }
    }

    @Test
    fun `when given email with bad pattern, then emit unenable login`() = runBlocking {
        //given

        //when
        val isLoginEnabled = createSUT().apply {
            setEmail(badPatternEmail)
            setPassword(goodPassword)
        }.isLoginEnabled

        //then
        isLoginEnabled.test {
            assertEquals(false, expectItem())
            assertEquals(false, expectItem())
            cancel()
        }
    }

    @Test
    fun `when given email with good pattern, then emit enable login`() = runBlocking {
        //given

        //when
        val isLoginEnabled = createSUT().apply {
            setEmail(goodEmail)
            setPassword(goodPassword)
        }.isLoginEnabled

        //then
        isLoginEnabled.test {
            assertEquals(false, expectItem())
            assertEquals(true, expectItem())
            cancel()
        }
    }
}