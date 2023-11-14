package database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import model.Paciente

@Dao
interface PacienteDao {

    @Insert
    fun insert(paciente: Paciente)

    @Query("SELECT EXISTS (SELECT 1 FROM pacientes_table WHERE cpf = :cpf)")
    fun isCpfExists(cpf: String): Boolean

    @Query("SELECT EXISTS (SELECT 1 FROM pacientes_table WHERE cpf = :cpf AND senha = :senha)")
    fun login(cpf: String, senha: String): Boolean

    @Query("SELECT * FROM pacientes_table WHERE cpf = :cpf")
    fun getPacienteByCpf(cpf: String): Paciente?
}
