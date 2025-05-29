import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object FirebaseAuthManager {
    private val auth = FirebaseAuth.getInstance()

    fun register(
        email: String,
        password: String,
        onResult: (Boolean, FirebaseUser?, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                val user = auth.currentUser
                onResult(task.isSuccessful, user, task.exception?.message)
            }
    }

    fun login(
        email: String,
        password: String,
        onResult: (Boolean, FirebaseUser?, String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                val user = auth.currentUser
                onResult(task.isSuccessful, user, task.exception?.message)
            }
    }

    fun logout() {
        auth.signOut()
    }

    fun resetPassword(email: String, onResult: (Boolean, String?) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                onResult(task.isSuccessful, task.exception?.message)
            }
    }

    fun currentUser(): FirebaseUser? = auth.currentUser
}
