package controllers

import io.javalin.http.Context
import models.User
import java.util.concurrent.atomic.AtomicInteger

object UserController {

    private val users = mutableMapOf<Int, User>(
        0 to User(0, "John", "some_email"),
        1 to User(1, "Jackson", "other_email"),
        2 to User(2, "Jameson", "the_email")
    )
    private var lastId: AtomicInteger = AtomicInteger(users.size - 1)

    fun getAllUsers(ctx: Context) {
        ctx.json(users)
    }

    fun getUserById(ctx: Context) {
        val user = users.getOrDefault(ctx.pathParam("id").toInt(), "¯\\_(ツ)_/¯")
        ctx.json(user)
    }

    fun createUser(ctx: Context) {
        val user = ctx.body<User>()
        val userId = lastId.incrementAndGet()
        users[userId] = User(name = user.name, email = user.email, id = userId)
        ctx.status(201)
    }

    fun updateUser(ctx: Context) {
        val user = ctx.body<User>()
        val userId = ctx.pathParam("id").toInt()
        if (users.containsKey(userId)) {
            users[userId] = user
        }
        ctx.status(204)
    }
}