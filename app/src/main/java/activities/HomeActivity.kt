package activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.com.fiap.global_solution_2sem_hapvida.databinding.ActivityHomeBinding
import database.AppDatabase
import database.PacienteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Paciente

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var paciente: Paciente
    private lateinit var pacienteDao: PacienteDao
    private lateinit var pacienteCPF: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pacienteDao = AppDatabase.getDatabase(this).pacienteDao()
        pacienteCPF = intent.getStringExtra("paciente_cpf").toString()

        setWelcomeMessage(pacienteCPF)

        binding.btnListUnidades.setOnClickListener(View.OnClickListener {
            startListarUnidadesActivity()
        })

        binding.btnMarcarConsulta.setOnClickListener(View.OnClickListener {
            startMarcarConsultaActivity()
        })

        binding.btnVerConsultas.setOnClickListener(View.OnClickListener {
            startVerConsultasActivity()
        })
    }

    private fun setWelcomeMessage(cpf: String) {
        GlobalScope.launch(Dispatchers.IO) {
            paciente = pacienteDao.getPacienteByCpf(cpf)!!

            withContext(Dispatchers.Main) {
                if (paciente != null) {
                    val mensagem = "Bem-vindo, ${paciente.nome}!"
                    binding.welcomeMessageTextView.text = mensagem
                } else {
                    binding.welcomeMessageTextView.text = "Paciente não encontrado."
                }
            }
        }
    }

    private fun startListarUnidadesActivity() {
        val intent = Intent(this@HomeActivity, VerUnidadesActivity::class.java)
        startActivity(intent)
    }

    private fun startMarcarConsultaActivity() {
        val intent = Intent(this@HomeActivity, MarcarConsultaActivity::class.java)
        intent.putExtra("paciente_cpf" , pacienteCPF)
        startActivity(intent)
    }

    private fun startVerConsultasActivity() {
        val intent = Intent(this@HomeActivity, VerConsultasActivity::class.java)
        intent.putExtra("paciente_cpf" , pacienteCPF)
        startActivity(intent)
    }
}
