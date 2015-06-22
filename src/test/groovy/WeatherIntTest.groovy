import spock.lang.Specification

class WeatherIntTest extends  Specification {

    Weather weather

    def setup() {
        weather = new Weather()
        RssRetriever retriever = new RssRetriever(new XmlSlurper())
        retriever.process()
        weather.rssRetriever = retriever
    }

    def "when conditions() called it returns current conditions"() {

        when:
        def now = weather.conditions()

        then:
        assert now ==~ /\w+.+F/
    }

    def "when icon() called it returns the number of the icon to use"() {

        when:
        def icon = weather.currentIcon()

        then:
        assert icon ==~ /[0-9]+/
    }
}
