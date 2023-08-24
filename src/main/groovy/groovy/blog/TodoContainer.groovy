package groovy.blog

import groovy.transform.CompileStatic
import io.micronaut.core.annotation.NonNull

import java.util.concurrent.ConcurrentHashMap

@CompileStatic
class TodoContainer {
    @NonNull
    final Map<TodoKey, Todo> todos = new ConcurrentHashMap<>()
}