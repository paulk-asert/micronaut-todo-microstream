package groovy.blog

import jakarta.annotation.Nullable
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull

import java.time.LocalDate

interface TodoRepository {
    Collection<Todo> findAll()

    Collection<Todo> findCompletedOnSchedule()

    Todo save(@Valid Todo todo)

    Todo findByKey(@NotNull @Valid TodoKey key)

    Todo schedule(@NotNull @Valid TodoKey key, @Nullable LocalDate newDate)

    Todo complete(@NotNull @Valid TodoKey key)

    void delete(@Valid @NotNull TodoKey key)
}
