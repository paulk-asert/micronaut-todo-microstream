package groovy.blog

import io.micronaut.core.annotation.NonNull

import java.util.concurrent.ConcurrentHashMap

class TodoContainer {
    @NonNull
    final Map<TodoKey, Todo> todos = new ConcurrentHashMap<>()
}