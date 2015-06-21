import spock.lang.Specification

class WeatherTest extends Specification {

    def weather
    def mockRetriever

    def setup() {
        mockRetriever = Mock(RssRetriever)
        weather = new Weather(mockRetriever)
    }

    def "Current condition is what we retrieved"() {
        mockRetriever.currentConditions() >> "Mostly Sunny"

        expect:
        weather.currentConditions() == "Mostly Sunny"
    }

    def "Current temperature is what we retrieved"() {
        mockRetriever.currentTemperature() >> "100"

        expect:
        weather.currentTemperature() == "100"
    }

    def "Current weather icon is made available"() {
        mockRetriever.currentIcon() >> "x"

        expect:
        weather.currentIcon() == "x"
    }

    def "Current conditions includes conditions and temperature"() {
        mockRetriever.currentConditions() >> "Mostly Sunny"
        mockRetriever.currentTemperature() >> "100"

        expect:
        weather.conditions() == "Mostly Sunny, 100°F"
    }

    def "Current forecast for next five days is properly formatted"() {
        mockRetriever.forecast() >> [[day: "Sat.", low: "21", high: "61", forecast: "Little Sprinkles"],
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
}
