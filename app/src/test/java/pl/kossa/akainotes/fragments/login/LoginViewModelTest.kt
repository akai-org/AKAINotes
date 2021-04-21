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

    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        clearAllMocks()

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
        val loginSuccessTest = createSUT().loginSuccess

        //then
        loginSuccessTest.test {
            assertEquals(true, expectItem())
            cancel()
        }
    }

    @Test
    fun `when given correct email and password, then emit login success`() = runBlocking {
        //given

        //when
        val sut = createSUT()

        sut.setEmail(goodEmail)
        sut.setPassword(goodPassword)
        sut.requestLogin()

        //then
        sut.loginSuccess.test {
            assertEquals(true, expectItem())
            cancel()
        }


    }
}