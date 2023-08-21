package groovy.blog

import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.time.LocalDate

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
        int total = all.size()
        int completed = all.count(t -> t.done)
        int totalScheduled = all.count(t -> t.scheduled)
        int completedOnSchedule = repo.findCompletedOnSchedule().size()
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
