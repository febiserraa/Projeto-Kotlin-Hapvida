package activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import br.com.fiap.global_solution_2sem_hapvida.databinding.ActivityLoginBinding
import database.AppDatabase
import database.PacienteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Paciente

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var pacienteDao: PacienteDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pacienteDao = AppDatabase.getDatabase(this).pacienteDao()

        binding.btnLogin.setOnClickListener(View.OnClickListener {
            if (!isEditTextNotNull()) {
                Toast.makeText(
                    this@MainActivity,
                    "Por favor, preencha todos os campos",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val cpf = binding.editTextCPF.text.toString()
                val senha = binding.editTextPassword.text.toString()

                GlobalScope.launch(Dispatchers.IO) {
                    val loginSuccessful = pacienteDao.login(cpf, senha)

                    withContext(Dispatchers.Main) {
                        if (loginSuccessful) {
                            // Login bem-sucedido
                            Toast.makeText(
                                this@MainActivity,
                                "Login bem-sucedido",
                                Toast.LENGTH_SHORT
                            ).show()
                            startHomeActivity(cpf)
                        } else {
                            // Credenciais inválidas
                            Toast.makeText(
                                this@MainActivity,
                                "Credenciais inválidas",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        })

        binding.btnCadastrar.setOnClickListener(View.OnClickListener {
            startRegisterActivity()
        })
    }

    private fun isEditTextNotNull(): Boolean {
        return binding.editTextCPF.text.isNotEmpty() &&
                binding.editTextPassword.text.isNotEmpty()
    }

    private fun startRegisterActivity() {
        val intent = Intent(this@MainActivity, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun startHomeActivity(cpf: String) {
        val intent = Intent(this@MainActivity, HomeActivity::class.java)
        intent.putExtra("paciente_cpf" , cpf)
        startActivity(intent)
        finish()
    }

}