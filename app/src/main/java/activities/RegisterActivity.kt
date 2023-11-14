package activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.fiap.global_solution_2sem_hapvida.databinding.ActivityRegisterBinding
import database.AppDatabase
import database.PacienteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Paciente

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var pacienteDao: PacienteDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pacienteDao = AppDatabase.getDatabase(this).pacienteDao()

        binding.btnRegister.setOnClickListener(View.OnClickListener {
            if (!isEditTextNotNull()) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Por favor, preencha todos os campos",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val paciente = getPacienteInfo()

                GlobalScope.launch(Dispatchers.IO) {
                    val cpfExists = pacienteDao.isCpfExists(paciente.cpf)

                    withContext(Dispatchers.Main) {
                        if (!cpfExists) {
                            GlobalScope.launch(Dispatchers.IO) {
                                pacienteDao.insert(paciente)
                            }

                            Toast.makeText(
                                this@RegisterActivity,
                                "Registro bem-sucedido",
                                Toast.LENGTH_SHORT
                            ).show()
                            startLoginActivity()
                        } else {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Erro ao cadastrar: CPF já cadastrado",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        })
    }

    private fun getPacienteInfo(): Paciente {
        val cpf = binding.editTextCPF.text.toString()
        val senha = binding.editTextPassword.text.toString()
        val nome = binding.editTextName.text.toString()
        val idade = binding.editTextAge.text.toString().toIntOrNull() ?: 0
        val convenio = binding.editTextConvenio.text.toString()

        return Paciente(cpf, senha, nome, idade, convenio)
    }

    private fun isEditTextNotNull(): Boolean {
        return binding.editTextCPF.text.isNotEmpty() &&
                binding.editTextPassword.text.isNotEmpty() &&
                binding.editTextName.text.isNotEmpty() &&
                binding.editTextAge.text.isNotEmpty() &&
                binding.editTextConvenio.text.isNotEmpty()
    }

    private fun startLoginActivity(){
        startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
        finish()
    }
}
