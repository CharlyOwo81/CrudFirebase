package mx.edu.itson.crudfirebase

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import mx.edu.itson.crudfirebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TareaAdapter
    private lateinit var viewModel: TareaViewModel

    var tareaEdit = Tarea()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[TareaViewModel::class.java]

        viewModel.listaTareas.observe(this) { tareas ->
            setupRecylerView(tareas)
        }

        binding.btnAgregarTarea.setOnClickListener {
            val tarea = Tarea(
                titulo = binding.etTitulo.text.toString(),
                descripcion = binding.tvDescripcion.text.toString()
            )

            viewModel.agregarTareas(tarea)

            binding.etTitulo.setText("")
            binding.tvDescripcion.setText("")
        }

        binding.btnActualizarTarea.setOnClickListener {
            tareaEdit.titulo = ""
            tareaEdit.descripcion = ""

            tareaEdit.titulo = binding.etTitulo.text.toString()
            tareaEdit.descripcion = binding.tvDescripcion.text.toString()

            viewModel.actualizarTareas(tareaEdit)
        }


        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

    }

    fun setupRecylerView(listaTareas: List<Tarea>){
        adapter = TareaAdapter(listaTareas, ::borrarTarea, ::actualizarTarea)
        binding.rvTareas.adapter = adapter
    }

    fun borrarTarea(id: String){
        viewModel.borrarTareas(id)
    }

    fun actualizarTarea(tarea: Tarea){
        tareaEdit = tarea

        binding.etTitulo.setText(tareaEdit.titulo)
        binding.tvDescripcion.setText(tareaEdit.descripcion)
    }
}