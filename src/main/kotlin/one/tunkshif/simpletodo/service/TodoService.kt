package one.tunkshif.simpletodo.service

import one.tunkshif.simpletodo.model.Todo
import one.tunkshif.simpletodo.repository.TodoRepository
import one.tunkshif.simpletodo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Service
@Transactional
class TodoService(
    @PersistenceContext
    val entityManager: EntityManager,
    @Autowired
    val userRepository: UserRepository,
    @Autowired
    val todoRepository: TodoRepository
) {

    fun create(username: String, title: String): Todo {
        val user = userRepository.findByUsername(username)
        return todoRepository.save(
            Todo(
                title = title,
                user = user!!
            )
        )
    }

    fun read(username: String): Collection<Todo> {
        val user = userRepository.findByUsername(username)
        return todoRepository.findByUser(user!!)
    }

    fun update(todoId: Long): Todo {
        val todo = entityManager.getReference(Todo::class.java, todoId)
        todo.isDone = true
        return entityManager.merge(todo)
    }

    fun delete(todoId: Long) {
        todoRepository.deleteById(todoId)
    }
}