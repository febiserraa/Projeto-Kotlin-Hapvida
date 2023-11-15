package database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import model.UNIDADE_TABLE_NAME
import model.Unidade

@Dao
interface UnidadeDao {
    @Insert
    fun insertMultipleUnidades(unidades: List<Unidade>)

    @Query("SELECT * FROM unidades_table")
    fun getAllUnidades(): List<Unidade>
}
