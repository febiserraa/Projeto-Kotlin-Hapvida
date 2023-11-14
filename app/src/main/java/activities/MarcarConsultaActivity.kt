package activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.fiap.global_solution_2sem_hapvida.databinding.ActivityMarcarConsultaBinding
import database.AppDatabase
import database.ConsultaDao
import database.PacienteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Consulta

class MarcarConsultaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMarcarConsultaBinding
    private lateinit var consultaDao: ConsultaDao
    private lateinit var pacienteDao: PacienteDao
    private lateinit var pacienteCPF: String

    private val unidadesList = listOf("Selecione uma Unidade", "Unidade São Paulo", "Unidade Guarulhos", "Unidade Campinas", "Unidade Rio De Janeiro")
    private val medicosList = listOf("Selecione um(a) Médico(a)", "Dr. Felipe", "Dr. Gian", "Dr. Ravi", "Dr. Luan")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarcarConsultaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        consultaDao = AppDatabase.getDatabase(this).consultaDao()
        pacienteDao = AppDatabase.getDatabase(this).pacienteDao()
        pacienteCPF = intent.getStringExtra("paciente_cpf").toString()

        // Lógica para o adapter para o Spinner de Unidade
        val adapterUnidade = ArrayAdapter(this, android.R.layout.simple_spinner_item, unidadesList)
        adapterUnidade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerUnidade.adapter = adapterUnidade

        // Lógica para o adapter para o Spinner de Médico
        val adapterMedico = ArrayAdapter(this, android.R.layout.simple_spinner_item, medicosList)
        adapterMedico.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerMedico.adapter = adapterMedico

        binding.btnMarcarConsulta.setOnClickListener {
            val data = binding.editTextData.text.toString()
            val hora = binding.editTextHora.text.toString()

            // Obtem a unidade selecionada no Spinner
            val unidadeSpinner = binding.spinnerUnidade
            val unidade = if (unidadeSpinner.selectedItemPosition > 0) {
                unidadeSpinner.selectedItem.toString()
            } else {
                null // Retorna nulo pois nada foi inserido
            }

            // Obtem o médico selecionado no Spinner
            val medicoSpinner = binding.spinnerMedico
            val medico = if (medicoSpinner.selectedItemPosition > 0) {
                medicoSpinner.selectedItem.toString()
            } else {
                null // Retorna nulo pois nada foi inserido
            }

            // Verificar se algum campo está vazio
            if (data.isBlank() || hora.isBlank() || unidade.isNullOrBlank() || medico.isNullOrBlank()) {
                Toast.makeText(this@MarcarConsultaActivity, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Corrotina para obter o paciente pelo CPF
            GlobalScope.launch(Dispatchers.IO) {
                val paciente = pacienteDao.getPacienteByCpf(pacienteCPF)

                if (paciente != null) {
                    val novaConsulta = Consulta(
                        data = data,
                        hora = hora,
                        unidade = unidade,
                        medico = medico,
                        pacienteCpf = paciente.cpf
                    )

                    // Corrotina para inserir a consulta no banco de dados
                    consultaDao.insert(novaConsulta)

                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MarcarConsultaActivity, "Consulta marcada com sucesso", Toast.LENGTH_SHORT).show()
                    }

                    returnHomeActivity()
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MarcarConsultaActivity, "Paciente não encontrado", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun returnHomeActivity() {
        startActivity(Intent(this@MarcarConsultaActivity, HomeActivity::class.java))
        finish()
    }
}
