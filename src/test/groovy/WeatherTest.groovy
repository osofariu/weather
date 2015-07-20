import groovy.mock.interceptor.MockFor
import spock.lang.Specification

class WeatherTest extends Specification {

    Weather weather
    def rssRetriever
    def conditionsFile
    def forecastFile

    def setup() {
        rssRetriever = Mock(RssRetriever)
        conditionsFile = Mock(File)
        forecastFile = Mock(File)
        weather = new Weather(rssRetriever, conditionsFile, forecastFile)
    }

    def "Current condition is what we retrieved"() {
        weather.rssRetriever.currentConditions() >> "Mostly Sunny"

        expect:
        weather.currentConditions() == "Mostly Sunny"
    }

    def "Current temperature is what we retrieved"() {
        weather.rssRetriever.currentTemperature() >> "100"

        expect:
        weather.currentTemperature() == "100"
    }

    def "Current weather icon is made available"() {
        weather.rssRetriever.currentIcon() >> "x"

        expect:
        weather.currentIcon() == "x"
    }

    def "Current conditions includes conditions and temperature"() {
        weather.rssRetriever.currentConditions() >> "Mostly Sunny"
        weather.rssRetriever.currentTemperature() >> "100"

        expect:
        weather.conditions() == "Mostly Sunny, 100°F"
    }

    def "Current forecast for next five days is properly formatted"() {
        weather.rssRetriever.forecast() >> [[day: "Sat.", low: "21", high: "61", forecast: "Little Sprinkles"],
                                     [day: "Sun.", low: "22", high: "62", forecast: "More Sprinkles"],
                                     [day: "Mon.", low: "23", high: "63", forecast: "Sprinkles Galore"],
                                     [day: "Tue.", low: "24", high: "64", forecast: "Few Sprinkles"],
                                     [day: "Wed.", low: "25", high: "65", forecast: "Sunny!"]]

        expect:
        weather.forecast() ==   "Sat.\t21°F - 61°F\t\tLittle Sprinkles\n" +
                                "Sun.\t22°F - 62°F\t\tMore Sprinkles\n"   +
                                "Mon.\t23°F - 63°F\t\tSprinkles Galore\n" +
                                "Tue.\t24°F - 64°F\t\tFew Sprinkles\n"    +
                                "Wed.\t25°F - 65°F\t\tSunny!\n"
    }

    def "In aggregate mode, weather should produce files containing the current conditions and forecasts"() {
        when:
        weather.conditions() >> "Conditions 123"
        weather.forecast() >>  "Forecast 123"

        weather.writeAggregateFiles()

        then:
        1 * conditionsFile.withWriter {it << "Conditions 123"}
        1 * forecastFile.withWriter {it << "Forecast 123"}
    }
}
