package activities

import adapter.UnidadeAdapter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.fiap.global_solution_2sem_hapvida.databinding.ActivityVerUnidadesBinding
import database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Unidade

class VerUnidadesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerUnidadesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerUnidadesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GlobalScope.launch(Dispatchers.IO) {

            inserirUnidades()

            val unidades = AppDatabase.getDatabase(this@VerUnidadesActivity).unidadeDao().getAllUnidades()

            withContext(Dispatchers.Main) {
                binding.recyclerViewUnidades.layoutManager =
                    LinearLayoutManager(this@VerUnidadesActivity)

                val unidadeAdapter = UnidadeAdapter(unidades)
                binding.recyclerViewUnidades.adapter = unidadeAdapter
            }
        }
    }

    private suspend fun inserirUnidades() {
        try {
            UnidadesUtils(this@VerUnidadesActivity).inserirUnidades()
            showToast("Unidades inseridas com sucesso!")
            Log.d("InsertUnidades", "Unidades inseridas com sucesso!")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("InsertUnidades", "Erro ao inserir unidades: ${e.message}")
        }
    }

    private suspend fun showToast(message: String) {
        withContext(Dispatchers.Main) {
            Toast.makeText(this@VerUnidadesActivity, message, Toast.LENGTH_SHORT).show()
        }
    }
}
