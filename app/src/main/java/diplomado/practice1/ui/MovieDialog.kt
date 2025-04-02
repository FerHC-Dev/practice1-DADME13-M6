package diplomado.practice1.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import diplomado.practice1.R
import diplomado.practice1.application.MoviesDBApp
import diplomado.practice1.data.MovieRepository
import diplomado.practice1.data.db.model.MovieEntity
import diplomado.practice1.databinding.MovieDialogBinding
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MovieDialog(
    private val newMovie: Boolean = true,
    private val movie: MovieEntity = MovieEntity(
        title = "",
        genre = "",
        director = "",
        year = 0
    ),
    private val updateUI: () -> Unit,
    private val message: (String) -> Unit
): DialogFragment() {

    private var _binding: MovieDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var dialog: Dialog
    private var saveButton: Button? = null
    private lateinit var repository: MovieRepository

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        _binding = MovieDialogBinding.inflate(layoutInflater)

        val spinnerItems = resources.getStringArray(R.array.spinner_items)
        //Spinner
        val spinner: Spinner = binding.tietGenre

        // Lista de elementos para el Spinner
        val items = resources.getStringArray(R.array.spinner_items)

        // Configurando el adaptador personalizado
        val adapter = SpinnerAdapter(requireContext(), items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter


        dialog = if(newMovie) {
            buildDialog(getString(R.string.btn_add), getString(R.string.btn_cancel), {
                //Guardar
                binding.apply {
                    movie.title = tietTitle.text.toString()
                    movie.year = tietYear.text.toString().toInt()
                    movie.director = tietDirector.text.toString()
                    movie.genre = spinnerItems[tietGenre.selectedItemPosition]
                }

                try {
                    lifecycleScope.launch {
                        val result = async {
                            repository.insertMovie(movie)
                        }
                        result.await()
                        message(getString(R.string.movie_added))
                        updateUI()

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    message(getString(R.string.movie_error))
                }

            }, {
                //Cancelar
                message(getString(R.string.movie_not_added))
            })
        }else{

            binding.apply {
                tietTitle.setText(movie.title)
                tietGenre.setSelection(spinnerItems.indexOf(movie.genre))
                tietDirector.setText(movie.director)
                tietYear.setText(String.format(movie.year.toString()))
            }

            buildDialog(getString(R.string.btn_update), getString(R.string.btn_delete), {
                //Actualizar
                binding.apply {
                    movie.title = tietTitle.text.toString()
                    movie.year = tietYear.text.toString().toInt()
                    movie.director = tietDirector.text.toString()
                    movie.genre = spinnerItems[tietGenre.selectedItemPosition]
                }
                try {
                    lifecycleScope.launch {
                        val result = async {
                            repository.updateMovie(movie)
                        }
                        result.await()
                        message(getString(R.string.movie_updated))
                        updateUI()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    message(getString(R.string.movie_error))
                }
            },{
                //Eliminar

                //Guardamos el contexto
                val context = requireContext()

                AlertDialog.Builder(context)
                    .setTitle(getString(R.string.dialog_delete))
                    .setMessage(getString(R.string.question_delete, movie.title))
                    .setPositiveButton(getString(R.string.btn_delete)) { _, _ ->
                        try {
                            lifecycleScope.launch {
                                val result = async {
                                    repository.deleteMovie(movie)
                                }
                                result.await()
                                message(context.getString(R.string.movie_deleted))
                                updateUI()
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            message(getString(R.string.movie_error))
                        }
                    }.setNegativeButton(getString(R.string.btn_cancel)) { _, _ ->
                        message(context.getString(R.string.movie_not_added))
                    }.create().show()
            })

        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(spinnerItems.indexOf(movie.genre) != spinner.selectedItemPosition){
                    saveButton?.isEnabled = validateFields()
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) { }
        }

        return dialog
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onStart() {
        super.onStart()

        repository = (requireActivity().applicationContext as MoviesDBApp).repository

        //Referebcia al boton de guardar
        saveButton = (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
        saveButton?.isEnabled = false

        binding.apply {
            setupTextWatcher(tietTitle, tietYear, tietDirector)
        }
    }

    private fun setupTextWatcher (vararg textFields: TextInputEditText){
        val textWatcher = object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                saveButton?.isEnabled = validateFields()
            }
        }

        textFields.forEach { it.addTextChangedListener(textWatcher) }
    }

    private fun validateFields(): Boolean =
        binding.tietTitle.text.toString().isNotEmpty() && binding.tietYear.text.toString().isNotEmpty() &&
                binding.tietDirector.text.toString().isNotEmpty() && binding.tietGenre.selectedItemPosition != 0

    private fun buildDialog(btnText1: String, btnText2: String
                            , positiveButton: () -> Unit, negativeButton: () -> Unit): Dialog =
        AlertDialog.Builder(requireContext()).setView(binding.root)
            .setTitle(if(newMovie) getString(R.string.dialog_title) else getString(R.string.dialog_update))
            .setPositiveButton(btnText1) { _, _ ->
                positiveButton()
            }
            .setNegativeButton(btnText2) { _, _ ->
                negativeButton()
            }
            .create()

}