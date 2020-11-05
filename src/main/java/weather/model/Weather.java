package weather.model;

/**
 * Simple POJO Class Weather
 *
 * @author Денис Висков
 * @version 1.0
 * @since 05.11.2020
 */
public class Weather {
    /**
     * Id
     */
    private int id;
    /**
     * City
     */
    private String city;
    /**
     * Temperature
     */
    private int temperature;

    public Weather(int id, String city, int temperature) {
        this.id = id;
        this.city = city;
        this.temperature = temperature;
    }

    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public int getTemperature() {
        return temperature;
    }
}
