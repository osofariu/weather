import spock.lang.Specification

/**
 * Created by ovi on 11/8/14.
 */
class RssRetrieverTest extends  Specification {

    def retriever

    def setup() {

        retriever = new RssRetriever(new XmlSlurper())
        def rssText = this.getClass().getResource("rssContent.txt").text
        retriever.xml =  new XmlSlurper().parseText(rssText).declareNamespace(
                'yweather': 'http://xml.weather.yahoo.com/ns/rss/1.0', 'geo': 'http://www.w3.org/2003/01/geo/wgs84_pos#')
    }


    def "it gets content from Yahoo weather site when asked to get contents from URL with node rss"() {
        expect:
        retriever.name() == "rss"
        retriever.title() == "Yahoo! Weather - Columbus, OH"
    }

    def "current condition is Cloudy"() {
        expect:
        retriever.currentConditions() == "Cloudy"
    }

    def "current temperature is 45"() {
        expect:
        retriever.currentTemperature() == "45"
    }

    def "current icon is 26"() {

        expect:
        retriever.currentIcon() == "26"
    }

    def "current forecast has five entries in it "() {
        def forecast = retriever.forecast();

        expect:
        forecast.size == 5
    }

    def "today is Sat, 8 Nov 2014, low=33, high=51, forecast=Partly Cloudy, code=29"() {
        def forecast = retriever.forecast();
        def today = forecast[0]

        expect:
        today["day"]        == "Sat"
        today["date"]       == "8 Nov 2014"
        today["low"]        == "33"
        today["high"]       == "51"
        today["forecast"]   == "Partly Cloudy"
        today["code"]       == "29"
    }

    def "Sunday day=Sun, date=9 Nov 2014, low=37, high=52 forecast=Sunny, code=32 /> "() {
        def forecast = retriever.forecast();
        def today = forecast[1]

        expect:
        today["day"]        == "Sun"
        today["date"]       == "9 Nov 2014"
        today["low"]        == "37"
        today["high"]       == "52"
        today["forecast"]   == "Sunny"
        today["code"]       == "32"
    }

    def "Monday day=Mon, date=10 Nov 2014, low=44, high=62 forecast=Sunny, code=32 /> "() {
        def forecast = retriever.forecast();
        def today = forecast[2]

        expect:
        today["day"]        == "Mon"
        today["date"]       == "10 Nov 2014"
        today["low"]        == "44"
        today["high"]       == "62"
        today["forecast"]   == "Mostly Sunny"
        today["code"]       == "34"
    }

    def "Tuesday day=Tue, date=11 Nov 2014, low=36, high=63 forecast=PM Showers, code=39 /> "() {
        def forecast = retriever.forecast();
        def today = forecast[3]

        expect:
        today["day"]        == "Tue"
        today["date"]       == "11 Nov 2014"
        today["low"]        == "36"
        today["high"]       == "63"
        today["forecast"]   == "PM Showers"
        today["code"]       == "39"
    }

    def "Wednesday day=Wed, date=12 Nov 2014, low=28, high=42 forecast=Partly Cloudy, code=30 /> "() {
        def forecast = retriever.forecast();
        def today = forecast[4]

        expect:
        today["day"]        == "Wed"
        today["date"]       == "12 Nov 2014"
        today["low"]        == "28"
        today["high"]       == "42"
        today["forecast"]   == "Partly Cloudy"
        today["code"]       == "30"
    }


}
