package database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import model.Consulta

@Dao
interface ConsultaDao {

    @Insert
    fun insert(consulta: Consulta)

    @Query("SELECT * FROM consultas_table WHERE pacienteCpf = :pacienteCpf")
    fun getConsultasByPacienteCpf(pacienteCpf: String): List<Consulta>
}