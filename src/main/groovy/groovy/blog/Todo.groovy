package groovy.blog

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.TupleConstructor
import io.micronaut.core.annotation.*
import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank

import java.time.LocalDate

@Serdeable
@TupleConstructor
@EqualsAndHashCode
@Introspected
@CompileStatic
@JsonIgnoreProperties(['done', 'scheduled'])
class Todo {
    @NotBlank String title

    @Nullable String description

    @Nullable LocalDate due

    @Nullable LocalDate completed

    boolean isDone() {
        completed != null
    }

    boolean isScheduled() {
        due != null
    }
}
