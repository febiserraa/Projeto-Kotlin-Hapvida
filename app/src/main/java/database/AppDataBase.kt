package database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import model.Consulta
import model.Paciente
import model.Unidade

const val PACIENTE_DATABASE_NAME = "PACIENTE_DATABASE_NAME"

@Database(entities = [Paciente::class, Consulta::class, Unidade::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pacienteDao(): PacienteDao
    abstract fun consultaDao(): ConsultaDao
    abstract fun unidadeDao(): UnidadeDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE != null) {
                return INSTANCE!!
            } else {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    PACIENTE_DATABASE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
