package pl.kossa.akainotes.repository

import kotlinx.coroutines.flow.flowOf

class UsersRepository {

    val email = "jakub@akai.org"
    val password = "password"

    val userAlreadyLoggedIn = flowOf(true)
}