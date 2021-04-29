package one.tunkshif.simpletodo.api

import one.tunkshif.simpletodo.model.Todo
import one.tunkshif.simpletodo.repository.TodoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping("/api/todo", produces = ["application/json"])
class TodoApi(
    @Autowired
    val todoRepository: TodoRepository
) {
    @GetMapping
    @RolesAllowed("ADMIN")
    fun getAll(): Collection<Todo> =
        todoRepository.findAll()
}