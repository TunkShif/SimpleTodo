package one.tunkshif.simpletodo.api

import one.tunkshif.simpletodo.extension.Logging
import one.tunkshif.simpletodo.model.Info
import one.tunkshif.simpletodo.model.ResponseFormat
import one.tunkshif.simpletodo.model.request.InfoRequest
import one.tunkshif.simpletodo.repository.InfoRepository
import one.tunkshif.simpletodo.service.InfoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping("/api/infos", produces = ["application/json"])
class InfoApi(
    @Autowired val infoService: InfoService,
    @Autowired val infoRepository: InfoRepository
) : Logging {
    @GetMapping
    fun getAll(): ResponseFormat<Collection<Info>> =
        ResponseFormat(data = infoRepository.findAll())

    @GetMapping("/{infoId}")
    fun getOne(@PathVariable infoId: Long): ResponseFormat<Info> =
        ResponseFormat(data = infoRepository.findById(infoId).get())

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @RolesAllowed("ADMIN")
    fun addOne(request: InfoRequest): ResponseFormat<String> {
        infoService.create(request.username, request.title, request.content)
        return ResponseFormat(data = "added")
    }

    @PutMapping("/{infoId}")
    @RolesAllowed("ADMIN")
    fun editOne(
        @PathVariable infoId: Long,
        @RequestParam username: String,
        @RequestParam title: String,
        @RequestParam content: String
    ): ResponseFormat<String> {
        infoService.update(infoId, title, content, username)
        return ResponseFormat(data = "edited")
    }

    @DeleteMapping("/{infoId}")
    @RolesAllowed("ADMIN")
    fun deleteOne(@PathVariable infoId: Long): ResponseFormat<String> {
        infoService.delete(infoId)
        return ResponseFormat(data = "deleted")
    }

    @PostMapping("/{infoId}/move", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun moveOne(@PathVariable infoId: String, username: String): ResponseFormat<String> {
        infoService.move(infoId.removeSurrounding("{", "}").toLong(), username)
        return ResponseFormat(data = "moved")
    }
}