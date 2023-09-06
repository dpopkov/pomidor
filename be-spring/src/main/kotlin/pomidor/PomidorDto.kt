package io.dpopkov.bepomidor.pomidor

data class PomidorDto(
    val description: String,
    val start: Long,
    val end: Long,
    val id: Long? = null,
)
