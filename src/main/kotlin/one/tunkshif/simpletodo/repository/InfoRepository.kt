package one.tunkshif.simpletodo.repository

import one.tunkshif.simpletodo.model.Info
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InfoRepository : JpaRepository<Info, Long>