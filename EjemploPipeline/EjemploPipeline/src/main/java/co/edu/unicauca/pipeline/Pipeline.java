package co.edu.unicauca.pipeline;

import java.util.List;

/**
 * Esta clase representa la tubería.
 *
 * Su responsabilidad es conectar los filtros y transportar el resultado de un
 * filtro al siguiente.
 *
 * @author libardo
 */
public class Pipeline<T> {

    private final List<Filter<T>> filters;

    public Pipeline(List<Filter<T>> filters) {
        this.filters = filters;
    }

    public T execute(T input) {

        T result = input;

        for (Filter<T> filter : filters) {

            result = filter.process(result);

        }

        return result;
    }

}
