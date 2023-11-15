package activities

import android.content.Context
import android.widget.Toast
import database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Unidade
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

class UnidadesUtils(private val context: Context) {

    private val db = AppDatabase.getDatabase(context)
    private val unidadeDao = db.unidadeDao()

    fun inserirUnidades() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                unidadeDao.insertMultipleUnidades(getUnidades())
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Erro ao inserir unidades: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadImageAsByteArray(imageFileName: String): ByteArray? {
        return try {
            val inputStream: InputStream = context.assets.open(imageFileName)
            val outputStream = ByteArrayOutputStream()

            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } != -1) {
                outputStream.write(buffer, 0, length)
            }

            outputStream.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun getUnidades(): List<Unidade> {
        val unidades = mutableListOf<Unidade>()

        unidades.add(Unidade(
            nome = "Hospital e Maternidade Araraquara",
            localizacao = "Av José Bonifácio, 569 - Centro\nAraraquara - SP",
            imagem = loadImageAsByteArray("hapvida-unidade.png")
        ))

        unidades.add(Unidade(
            nome = "Hospital e Maternidade Bauru",
            localizacao = "Rua Gustavo Maciel, 15 - Centro\nBauru - SP",
            imagem = loadImageAsByteArray("hapvida-unidade.png")
        ))

        unidades.add(Unidade(
            nome = "Hospital e Maternidade Renascença de Campinas",
            localizacao = "Avenida Barão de Itapura, 1444 - Vila Itapura\nCampinas - SP",
            imagem = loadImageAsByteArray("hapvida-unidade.png")
        ))

        unidades.add(Unidade(
            nome = "Hospital e Maternidade de Franca",
            localizacao = "Rua Doutor Fernando Falleiros de Lima, 2333 ,lote 01 ao 26 - Centro\nFranca - SP",
            imagem = loadImageAsByteArray("hapvida-unidade.png")
        ))

        unidades.add(Unidade(
            nome = "Hospital e Maternidade Guarulhos",
            localizacao = "Avenida Tiradentes, 1015 - Jardim Santa Edwirges\nGuarulhos - SP",
            imagem = loadImageAsByteArray("hapvida-unidade.png")
        ))

        unidades.add(Unidade(
            nome = "Hospital e Maternidade Paulo Sacramento",
            localizacao = "Rua Quinze de Novembro, 865 - Centro\nJundiaí - SP",
            imagem = loadImageAsByteArray("hapvida-unidade.png")
        ))

        unidades.add(Unidade(
            nome = "Hospital e Maternidade Medical",
            localizacao = "Av. Ana Carolina de Barros Levy, 124 - Vila Paraíso\nLimeira - SP",
            imagem = loadImageAsByteArray("hapvida-unidade.png")
        ))

        unidades.add(Unidade(
            nome = "Hospital e Maternidade Lins",
            localizacao = "Av. Nicolau Zarvos, 1650 - Jardim Leoni\nLins - SP",
            imagem = loadImageAsByteArray("hapvida-unidade.png")
        ))

        unidades.add(Unidade(
            nome = "Hospital e Maternidade Cruzeiro do Sul",
            localizacao = "Avenida Autonomistas, 2502 - Vila Yara\nOsasco - SP",
            imagem = loadImageAsByteArray("hapvida-unidade.png")
        ))

        unidades.add(Unidade(
            nome = "Hospital São Francisco",
            localizacao = "Rua Bernardino de Campos, 912 - Centro\nRibeirão Preto - SP",
            imagem = loadImageAsByteArray("hapvida-unidade.png")
        ))
        return unidades
    }
}
