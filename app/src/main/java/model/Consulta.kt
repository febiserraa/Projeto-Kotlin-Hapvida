package model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val CONSULTA_TABLE_NAME = "consultas_table"

@Entity(tableName = CONSULTA_TABLE_NAME)
data class Consulta(
    @PrimaryKey(autoGenerate = true)
    @NonNull @ColumnInfo val id: Long = 0,
    @NonNull @ColumnInfo val data: String,
    @NonNull @ColumnInfo val hora: String,
    @NonNull @ColumnInfo val unidade: String,
    @NonNull @ColumnInfo val medico: String,
    @NonNull @ColumnInfo val pacienteCpf: String
)