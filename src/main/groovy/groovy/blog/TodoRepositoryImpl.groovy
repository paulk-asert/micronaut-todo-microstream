package groovy.blog

import io.micronaut.core.annotation.NonNull
import io.micronaut.microstream.RootProvider
import io.micronaut.microstream.annotations.StoreParams
import io.micronaut.microstream.annotations.StoreReturn
import jakarta.annotation.Nullable
import jakarta.inject.Singleton
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull

import java.time.LocalDate

@Singleton
class TodoRepositoryImpl implements TodoRepository {
    private final RootProvider<TodoContainer> rootProvider

    TodoRepositoryImpl(RootProvider<TodoContainer> rootProvider) {
        this.rootProvider = rootProvider
    }

    @Override
    Collection<Todo> findAll() {
        rootProvider.root().todos.values()
    }

    @Override
    Collection<Todo> findCompletedOnSchedule() {
        rootProvider.root().todos.values().findAll(t -> t.completed < t.due && t.done)
    }

    @Override
    Todo findByKey(@NotNull @NonNull TodoKey key) {
        confirmExists(key)
        rootProvider.root().todos[key]
    }

    @Override
    Todo save(@NotNull @NonNull @Valid Todo todo) {
        var todos = rootProvider.root().todos
        performCreate(todos, todo)
    }

    @StoreParams('todos')
    protected Todo performCreate(Map<TodoKey, Todo> todos, Todo todo) {
        var key = new TodoKey(todo.title, todo.due)
        confirmAbsent(key)
        todos[key] = todo
        todo
    }

    @Override
    Todo schedule(@NotNull @NonNull TodoKey key, @Nullable LocalDate newDate) {
        confirmExists(key)
        performSchedule(key, newDate)[key]
    }

    @StoreReturn
    protected Map<TodoKey, Todo> performSchedule(TodoKey key, LocalDate newDate) {
        var todos = rootProvider.root().todos
        var todo = todos[key]
        todo.due = newDate
        todos.remove(key)
        key.due = newDate
        todos.put(key, todo)
        todos
    }

    @Override
    Todo complete(@NotNull @NonNull TodoKey key) {
        confirmExists(key)
        performComplete(key)
    }

    @StoreReturn
    protected Todo performComplete(TodoKey key) {
        rootProvider.root().todos[key].tap { completed = LocalDate.now() }
    }

    @Override
    void delete(@NotNull @NonNull TodoKey key) {
        confirmExists(key)
        performDelete(key)
    }

    @StoreReturn
    protected Map<TodoKey, Todo> performDelete(TodoKey key) {
        var todos = rootProvider.root().todos
        todos.remove(key)
        todos
    }

    private void confirmAbsent(TodoKey key) {
        if (rootProvider.root().todos.containsKey(key))
            throw new IllegalArgumentException('Todo matching request already exists!')
    }

    private void confirmExists(TodoKey key) {
        if (!rootProvider.root().todos.containsKey(key))
            throw new IllegalArgumentException('No Todo matching request exists!')
    }

}
