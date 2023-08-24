package groovy.blog

import groovy.transform.CompileStatic
import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.time.LocalDate

@CompileStatic
@Singleton
class TodoService {
    @Inject TodoRepository repo

    Collection<Todo> findAll() {
        repo.findAll()
    }

    Todo create(Todo todo) {
        repo.save(todo)
    }

    Todo find(TodoKey key) {
        repo.findByKey(key)
    }

    TodoStats stats() {
        Collection<Todo> all = repo.findAll()
        long total = all.size()
        long completed = all.count(0L, t -> t.done)
        long totalScheduled = all.count(0L, t -> t.scheduled)
        long completedOnSchedule = repo.findCompletedOnSchedule().size()
        new TodoStats(total, completed, totalScheduled, completedOnSchedule)
    }

    Todo delete(TodoKey key) {
        var todo = repo.findByKey(key)
        repo.delete(key)
        todo
    }

    Todo reschedule(TodoKey key, LocalDate newDate) {
        repo.schedule(key, newDate)
    }

    Todo unschedule(TodoKey key) {
        repo.schedule(key, null)
    }

    Todo complete(TodoKey key) {
        repo.complete(key)
    }
}
