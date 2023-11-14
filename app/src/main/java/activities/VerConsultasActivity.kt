package activities

import adapter.ConsultaAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.fiap.global_solution_2sem_hapvida.databinding.ActivityVerConsultasBinding
import database.AppDatabase
import database.ConsultaDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VerConsultasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerConsultasBinding
    private lateinit var consultaDao: ConsultaDao
    private lateinit var pacienteCPF: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerConsultasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        consultaDao = AppDatabase.getDatabase(this).consultaDao()
        pacienteCPF = intent.getStringExtra("paciente_cpf").toString()

        // Use corrotinas para executar a consulta no contexto IO
        GlobalScope.launch(Dispatchers.IO) {
            val consultas = consultaDao.getConsultasByPacienteCpf(pacienteCPF)

            // Atualize o RecyclerView na thread principal usando withContext
            withContext(Dispatchers.Main) {
                val consultaAdapter = ConsultaAdapter(consultas)
                binding.recyclerViewConsultas.adapter = consultaAdapter
            }
        }
    }
}
