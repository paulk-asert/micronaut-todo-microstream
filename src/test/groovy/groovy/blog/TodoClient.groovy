package groovy.blog

import io.micronaut.http.annotation.*
import io.micronaut.http.client.annotation.Client
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull

@Client('/todo')
interface TodoClient {

    @Get('/')
    List<Todo> list()

    @Post('/')
    Todo create(@NotNull @Valid @Body Todo todo)
}
