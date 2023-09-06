package io.dpopkov.bepomidor.pomidor

import org.springframework.data.repository.CrudRepository

interface PomidorRepository : CrudRepository<Pomidor, Long>
