package io.dpopkov.bepomidor.pomidor

fun Pomidor.toDto(): PomidorDto {
    return PomidorDto(
        description = this.description,
        start = this.start,
        end = this.finish,
        id = this.id
    )
}

fun PomidorDto.toEntity(): Pomidor {
    return Pomidor(
        description = this.description,
        start = this.start,
        finish = this.end,
        id = this.id,
    )
}
