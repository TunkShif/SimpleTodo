package one.tunkshif.simpletodo.api

import one.tunkshif.simpletodo.extension.Logging
import one.tunkshif.simpletodo.model.ResponseFormat
import one.tunkshif.simpletodo.model.Todo
import one.tunkshif.simpletodo.model.request.TodoRequest
import one.tunkshif.simpletodo.repository.TodoRepository
import one.tunkshif.simpletodo.repository.UserRepository
import one.tunkshif.simpletodo.service.TodoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/todolist", produces = ["application/json"])
class TodoApi(
    @Autowired val todoService: TodoService,
    @Autowired val userRepository: UserRepository,
    @Autowired val todoRepository: TodoRepository
) : Logging {
    @GetMapping
    fun getAllByUsername(@RequestParam username: String): ResponseFormat<Collection<Todo>> =
        ResponseFormat(data = todoService.read(username))

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun addOne(request: TodoRequest): ResponseFormat<String> {
        todoService.create(request.username, request.title)
        return ResponseFormat(data = "added")
    }

    @PutMapping("/{todoId}")
    fun markOneAsDone(@PathVariable todoId: Long): ResponseFormat<String> {
        todoService.update(todoId)
        return ResponseFormat(data = "done")
    }

    @DeleteMapping("/{todoId}")
    fun deleteOne(@PathVariable todoId: Long): ResponseFormat<String> {
        todoService.delete(todoId)
        return ResponseFormat(data = "deleted")
    }
}