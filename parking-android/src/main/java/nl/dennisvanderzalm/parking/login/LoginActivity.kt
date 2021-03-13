package nl.dennisvanderzalm.parking.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import nl.dennisvanderzalm.parking.R
import nl.dennisvanderzalm.parking.databinding.ActivityLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity(R.layout.activity_login) {

    private val viewModel: LoginViewModel by viewModel()

    private val binding: ActivityLoginBinding by viewBinding(ActivityLoginBinding::bind, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val identifier = binding.editTextIdentifier.text.toString()
            val password = binding.editTextPassword.text.toString()

            viewModel.login(identifier, password)
        }
    }
}