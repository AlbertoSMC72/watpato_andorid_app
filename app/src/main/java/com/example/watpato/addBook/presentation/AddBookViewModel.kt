package com.example.watpato.addBook.presentation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watpato.addBook.data.model.BookRequest
import com.example.watpato.addBook.data.model.GenreDTO
import com.example.watpato.addBook.domain.CreateBookUseCase
import com.example.watpato.addBook.domain.CreateGenreUseCase
import com.example.watpato.addBook.domain.GetAllGenresUseCase
import com.example.watpato.core.data.UserInfoProvider
import kotlinx.coroutines.launch

class CreateBookViewModel : ViewModel() {

    private val getAllGenresUseCase = GetAllGenresUseCase()
    private val createGenreUseCase = CreateGenreUseCase()
    private val createBookUseCase = CreateBookUseCase()

    // Title and description
    private val _title = MutableLiveData("")
    val title: LiveData<String> get() = _title

    private val _description = MutableLiveData("")
    val description: LiveData<String> get() = _description

    // Listado de géneros
    private val _genres = MutableLiveData<List<GenreDTO>>(emptyList())
    val genres: LiveData<List<GenreDTO>> get() = _genres

    // Géneros seleccionados (por ID)
    private val _selectedGenreIds = MutableLiveData<List<Int>>(emptyList())
    val selectedGenreIds: LiveData<List<Int>> get() = _selectedGenreIds

    // Control de error y éxito
    private val _error = MutableLiveData<String>("")
    val error: LiveData<String> get() = _error

    private val _success = MutableLiveData<Boolean>(false)
    val success: LiveData<Boolean> get() = _success

    // Llamado inicial para traer géneros
    fun loadGenres() {
        viewModelScope.launch {
            val result = getAllGenresUseCase()
            result.onSuccess { list ->
                _genres.value = list
            }.onFailure { exception ->
                _error.value = "Error cargando géneros: ${exception.message}"
            }
        }
    }

    // Cambios en título/descripción
    fun onTitleChange(newTitle: String) {
        _title.value = newTitle
    }

    fun onDescriptionChange(newDescription: String) {
        _description.value = newDescription
    }

    // Actualizar la lista de géneros seleccionados
    fun onGenreSelected(genreId: Int) {
        val current = _selectedGenreIds.value.orEmpty().toMutableList()
        if (current.contains(genreId)) {
            // si ya está seleccionado, lo quitamos
            current.remove(genreId)
        } else {
            // si no está, lo agregamos
            current.add(genreId)
        }
        _selectedGenreIds.value = current
    }

    fun createNewGenre(name: String) {
        viewModelScope.launch {
            val result = createGenreUseCase(name)
            result.onSuccess { newGenre ->
                val updated = _genres.value.orEmpty().toMutableList()
                updated.add(newGenre)
                _genres.value = updated
                onGenreSelected(newGenre.id)
            }.onFailure { exception ->
                _error.value = "Error creando género: ${exception.message}"
            }
        }
    }

    fun createBook() {
        val titleValue = _title.value?.trim().orEmpty()
        val descriptionValue = _description.value?.trim().orEmpty()
        val selectedGenres = _selectedGenreIds.value.orEmpty()

        if (titleValue.isBlank() || descriptionValue.isBlank()) {
            _error.value = "Por favor, completa todos los campos"
            return
        }
        if (selectedGenres.isEmpty()) {
            _error.value = "Selecciona al menos un género"
            return
        }

        // Autor predeterminado: 1
        val request = BookRequest(
            title = titleValue,
            description = descriptionValue,
            authorId = UserInfoProvider.userID,
            genreIds = selectedGenres
        )

        viewModelScope.launch {
            val result = createBookUseCase(request)
            result.onSuccess {
                _success.value = true
                _error.value = ""
            }.onFailure { exception ->
                _success.value = false
                _error.value = "Error creando libro: ${exception.message}"
            }
        }
    }
}