package one.tunkshif.simpletodo.service

import one.tunkshif.simpletodo.model.Info
import one.tunkshif.simpletodo.model.Todo
import one.tunkshif.simpletodo.repository.InfoRepository
import one.tunkshif.simpletodo.repository.TodoRepository
import one.tunkshif.simpletodo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Service
@Transactional
class InfoService(
    @PersistenceContext
    private val entityManager: EntityManager,
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val infoRepository: InfoRepository,
    @Autowired
    private val todoRepository: TodoRepository
) {

    fun create(username: String, title: String, content: String): Info {
        val user = userRepository.findByUsername(username)
        return infoRepository.save(
            Info(
                title = title,
                content = content,
                user = user!!
            )
        )
    }

    fun update(infoId: Long, title: String, content: String, username: String): Info {
        val info = entityManager.getReference(Info::class.java, infoId)
        info.title = title
        info.content = content
        info.user = userRepository.findByUsername(username)!!
        return entityManager.merge(info)
    }

    fun delete(infoId: Long) {
        infoRepository.deleteById(infoId)
    }

    fun move(infoId: Long, username: String) {
        val user = userRepository.findByUsername(username)
        val info = infoRepository.findById(infoId).get()
        todoRepository.save(
            Todo(
                title = info.title,
                user = user!!
            )
        )
    }
}