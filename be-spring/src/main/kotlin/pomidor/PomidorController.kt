package io.dpopkov.bepomidor.pomidor

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pomidor")
class PomidorController(
    private val pomidorRepository: PomidorRepository
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @PostMapping
    fun createPomidor(@RequestBody pomidorDto: PomidorDto): ResponseEntity<PomidorDto?> {
        val saved = pomidorRepository.save<Pomidor>(pomidorDto.toEntity())
        log.info("saved {}", saved);
        return ResponseEntity.ok(saved.toDto())
    }

    @GetMapping
    fun getAll(): ResponseEntity<List<PomidorDto>?> {
        val all = pomidorRepository.findAll().map({p -> p.toDto()}).toList()
        log.info("get all found {} pomidors", all.size)
        return ResponseEntity.ok(all)
    }
}
