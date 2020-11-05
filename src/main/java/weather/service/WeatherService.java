package weather.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import weather.model.Weather;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class is a service weather
 *
 * @author Денис Висков
 * @version 1.0
 * @since 05.11.2020
 */
@Service
public class WeatherService {
    /**
     * Weathers
     */
    private final Map<Integer, Weather> weathers = new ConcurrentHashMap<>();

    {
        weathers.put(1, new Weather(1, "Msc", 20));
        weathers.put(2, new Weather(2, "SPb", 15));
        weathers.put(3, new Weather(3, "Bryansk", 15));
        weathers.put(4, new Weather(4, "Smolensk", 15));
        weathers.put(5, new Weather(5, "Kiev", 15));
        weathers.put(6, new Weather(6, "Minsk", 15));
    }

    /**
     * Return Mono by given id
     *
     * @param id
     * @return Mono
     */
    public Mono<Weather> findById(Integer id) {
        return Mono.justOrEmpty(weathers.get(id));
    }

    /**
     * @return All weathers
     */
    public Flux<Weather> all() {
        return Flux.fromIterable(weathers.values());
    }

    /**
     * @return max temperature mono
     */
    public Mono<Weather> maxTemperature() {
        return Mono.justOrEmpty(weathers.values()
                .parallelStream()
                .max(weatherComparator()));
    }

    /**
     * @param temperature
     * @return Flux greater than given temperature
     */
    public Flux<Weather> greaterThan(int temperature) {
        return Flux.fromStream(weathers.values().parallelStream()
                .filter(weather -> weather.getTemperature() > temperature));
    }

    /**
     * Return a plain Comparator which execute compare by degree
     *
     * @return Comparator
     */
    private Comparator<Weather> weatherComparator() {
        return new Comparator<Weather>() {
            @Override
            public int compare(Weather o1, Weather o2) {
                if (o1.getTemperature() < o2.getTemperature()) {
                    return -1;
                }
                if (o1.getTemperature() > o2.getTemperature()) {
                    return 1;
                }
                return 0;
            }
        };
    }
}
