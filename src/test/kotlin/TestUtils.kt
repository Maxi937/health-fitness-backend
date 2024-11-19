import com.greenmen.domain.User
import com.greenmen.models.auth.AuthRequest
import com.greenmen.models.auth.SignUpRequest
import kotlinx.serialization.json.Json
import java.io.File

fun AuthRequest.Companion.fromJsonFile(path: String): AuthRequest {
    val jsonStr = File(path).readText(Charsets.UTF_8)
    return Json.decodeFromString(serializer(), jsonStr)
}

fun User.Companion.fromJsonFile(path: String): User {
    val jsonStr = File(path).readText(Charsets.UTF_8)
    return Json.decodeFromString(serializer(), jsonStr)
}

fun SignUpRequest.Companion.fromJsonFile(path: String): SignUpRequest {
    val jsonStr = File(path).readText(Charsets.UTF_8)
    return Json.decodeFromString(serializer(), jsonStr)
}