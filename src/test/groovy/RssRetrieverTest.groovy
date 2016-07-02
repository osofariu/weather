import spock.lang.Specification

class RssRetrieverTest extends  Specification {

    def retriever

    def setup() {
        retriever = new RssRetriever()
        retriever.xmlSlurper = new XmlSlurper()
        def rssText = this.getClass().getResource("rssContent.txt").text
        retriever.xml =  new XmlSlurper().parseText(rssText).declareNamespace(
                'yweather': 'http://xml.weather.yahoo.com/ns/rss/1.0', 'geo': 'http://www.w3.org/2003/01/geo/wgs84_pos#')
    }

    def "it gets content from Yahoo weather site when asked to get contents from URL"() {
        expect:
        retriever.name() == "query"
        retriever.title() == "Yahoo! Weather - Columbus, OH, US"
    }

    def "current condition is"() {
        expect:
        retriever.currentConditions() == "Partly Cloudy"
    }

    def "current temperature is"() {
        expect:
        retriever.currentTemperature() == "63"
    }

    def "current icon is"() {

        expect:
        retriever.currentIcon() == "30"
    }

    def "current forecast has ten entries in it "() {
        def forecast = retriever.forecast();

        expect:
        forecast.size == 10
    }

    def "today is"() {
        def forecast = retriever.forecast();
        def today = forecast[0]

        expect:
        today["day"]        == "Sat"
        today["date"]       == "02 Jul 2016"
        today["low"]        == "57"
        today["high"]       == "78"
        today["forecast"]   == "Mostly Cloudy"
        today["code"]       == "28"
    }

    def "tomorrow is"() {
        def forecast = retriever.forecast();
        def today = forecast[1]

        expect:
        today["day"]        == "Sun"
        today["date"]       == "03 Jul 2016"
        today["low"]        == "60"
        today["high"]       == "77"
        today["forecast"]   == "Cloudy"
        today["code"]       == "26"
    }

    def "day after tomorrow is"() {
        def forecast = retriever.forecast();
        def today = forecast[2]

        expect:
        today["day"]        == "Mon"
        today["date"]       == "04 Jul 2016"
        today["low"]        == "62"
        today["high"]       == "75"
        today["forecast"]   == "Showers"
        today["code"]       == "11"
    }

    def "day after after tomorrow is"() {
        def forecast = retriever.forecast();
        def today = forecast[3]

        expect:
        today["day"]        == "Tue"
        today["date"]       == "05 Jul 2016"
        today["low"]        == "66"
        today["high"]       == "84"
        today["forecast"]   == "Partly Cloudy"
        today["code"]       == "30"
    }

    def "day after after after tomorrow is"() {
        def forecast = retriever.forecast();
        def today = forecast[4]

        expect:
        today["day"]        == "Wed"
        today["date"]       == "06 Jul 2016"
        today["low"]        == "68"
        today["high"]       == "86"
        today["forecast"]   == "Thunderstorms"
        today["code"]       == "4"
    }
}
