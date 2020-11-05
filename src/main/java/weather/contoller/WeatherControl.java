package weather.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import weather.model.Weather;
import weather.service.WeatherService;

import java.time.Duration;

/**
 * Class is a weather controller
 *
 * @author Денис Висков
 * @version 1.0
 * @since 05.11.2020
 */
@RestController
public class WeatherControl {
    /**
     * Service
     */
    private final WeatherService weathers;

    @Autowired
    public WeatherControl(WeatherService weathers) {
        this.weathers = weathers;
    }

    /**
     * @return Flux<Weather>
     */
    @GetMapping(value = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Weather> all() {
        Flux<Weather> data = weathers.all();
        Flux<Long> delay = Flux.interval(Duration.ofSeconds(3));
        return Flux.zip(data, delay).map(Tuple2::getT1);
    }

    /**
     * @param id
     * @return Mono by id
     */
    @GetMapping(value = "/get/{id}")
    public Mono<Weather> get(@PathVariable Integer id) {
        return weathers.findById(id);
    }

    /**
     * @return mono with max temperature
     */
    @GetMapping(value = "/hottest")
    public Mono<Weather> hottest() {
        return weathers.maxTemperature();
    }

    /**
     * @param degree
     * @return flux with greater degree by given parameter
     */
    @GetMapping(value = "/cityGreatThan/{degree}")
    public Flux<Weather> greaterThan(@PathVariable("degree") int degree) {
        return weathers.greaterThan(degree);
    }
}
