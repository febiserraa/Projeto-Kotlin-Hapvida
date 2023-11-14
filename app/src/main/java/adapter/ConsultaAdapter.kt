package adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.global_solution_2sem_hapvida.databinding.ItemViewConsultaBinding
import model.Consulta

class ConsultaAdapter(private val consultas: List<Consulta>) : RecyclerView.Adapter<ConsultaAdapter.ConsultaViewHolder>() {

    class ConsultaViewHolder(private val binding: ItemViewConsultaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(consulta: Consulta) {
            binding.textViewData.text = "Data: ${consulta.data}"
            binding.textViewHora.text = "Hora: ${consulta.hora}"
            binding.textViewUnidade.text = "Unidade: ${consulta.unidade}"
            binding.textViewMedico.text = "Médico: ${consulta.medico}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsultaViewHolder {
        val binding = ItemViewConsultaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConsultaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConsultaViewHolder, position: Int) {
        val consulta = consultas[position]
        holder.bind(consulta)
    }

    override fun getItemCount(): Int {
        return consultas.size
    }
}
