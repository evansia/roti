import controllers.UserController
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.apibuilder.ApiBuilder.put

fun main(args: Array<String>) {
    val app = Javalin.create().apply {
        exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
        error(404) { ctx -> ctx.json("Oops! Something went wrong here...") }
    }.start(7000)
    app.get("/") { ctx -> ctx.result("Hello World") }
    app.routes {
        path("users") {
            get(UserController::getAllUsers)
            post(UserController::createUser)
            path(":id") {
                get(UserController::getUserById)
                put(UserController::updateUser)
            }
        }
    }
}