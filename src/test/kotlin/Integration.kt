import com.greenmen.config.JavalinConfig
import com.greenmen.di.appModule
import com.greenmen.models.auth.AuthRequest
import com.greenmen.models.auth.SignUpRequest
import com.greenmen.repository.CardioDAO
import com.greenmen.repository.UserDAO
import com.greenmen.repository.WeightDAO
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext.startKoin
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class Integration : KoinComponent {
    val userDao: UserDAO by inject()
    val weightDAO: WeightDAO by inject()
    val cardioDAO: CardioDAO by inject()

    init {
        startKoin {
            modules(appModule())
            JavalinConfig(port = 7000).startJavalinService()
        }

        val testuser = SignUpRequest.fromJsonFile("src/test/resources/TestUser.json")

        try {
            println("adding test user")
            userDao.new(testuser.email, testuser.name, testuser.password)
        } catch (e: Exception) {
            println(e.message)
        }

    }

    @Test
    fun `User Login Success`() {
        var success = false
        val request = AuthRequest.fromJsonFile("src/test/resources/AuthRequest.json")
        val user = userDao.findByEmail(request.email)

        require(user != null) {
            "there is no user with email ${request.email} in the database"
        }

        if (request.password == user.password) {
            success = true
        }

        assertEquals(true, success)
        println("${user.name} logged in succesfully")
    }

    @Test
    fun `Find User by Email`() {
        val user = userDao.findByEmail("testuser@email.com")
        assertNotNull(user)
    }

    companion object {
        @BeforeAll
        @JvmStatic
        fun setUp() {

        }
    }
}