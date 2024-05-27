package model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Film(
    var name: String,
    var color: String,
    val description: String,
    val rating: Double,
    val imageResId: Int,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}


