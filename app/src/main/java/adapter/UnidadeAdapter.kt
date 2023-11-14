package adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.global_solution_2sem_hapvida.databinding.ItemViewUnidadeBinding
import model.Unidade

class UnidadeAdapter(private val unidades: List<Unidade>) : RecyclerView.Adapter<UnidadeAdapter.UnidadeViewHolder>() {

    class UnidadeViewHolder(private val binding: ItemViewUnidadeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(unidade: Unidade) {
            Log.i("ID", "ID: ${unidade.id}")
            binding.textViewId.text = "ID: ${unidade.id}"
            binding.textViewNome.text = "Nome: ${unidade.nome}"
            binding.textViewLocalizacao.text = "Localização: ${unidade.localizacao}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnidadeViewHolder {
        val binding = ItemViewUnidadeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UnidadeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return unidades.size
    }

    override fun onBindViewHolder(holder: UnidadeViewHolder, position: Int) {
        val unidade = unidades[position]
        holder.bind(unidade)
    }
}
