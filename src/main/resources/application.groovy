micronaut.application.name = 'micronautTodo'

microstream {
    storage {
        main.'root-class' = 'groovy.blog.TodoContainer'
        main.'storage-directory' = 'build/todo-storage'
    }
    rest {
        enabled = true
    }
}
