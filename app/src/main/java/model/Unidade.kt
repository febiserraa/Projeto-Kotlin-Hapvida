package model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val UNIDADE_TABLE_NAME = "unidades_table"

@Entity(tableName = UNIDADE_TABLE_NAME)
data class Unidade(
    @PrimaryKey(autoGenerate = true)
    @NonNull @ColumnInfo(name = "id") val id: Long = 0,
    @NonNull @ColumnInfo(name = "nome") val nome: String,
    @NonNull @ColumnInfo(name = "localizacao") val localizacao: String,
    @ColumnInfo(name = "imagem") val imagem: ByteArray?
)
