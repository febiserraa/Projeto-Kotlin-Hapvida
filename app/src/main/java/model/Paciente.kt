package model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

const val PACIENTE_TABLE_NAME = "pacientes_table"

@Entity(tableName = PACIENTE_TABLE_NAME)
@Parcelize
data class Paciente(
    @PrimaryKey val cpf: String,
    @NonNull @ColumnInfo val senha: String,
    @NonNull @ColumnInfo val nome: String,
    @NonNull @ColumnInfo val idade: Int,
    @NonNull @ColumnInfo val convenio: String
) : Parcelable
