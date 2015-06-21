import spock.lang.Specification

import static java.util.regex.Pattern.matches
import static org.spockframework.util.Assert.that

class WeatherIntTest  extends  Specification {

    def "weather gives you reasonable conditions"() {
        def weather = new Weather()
        def rssRetriever = new RssRetriever()
        rssRetriever.xmlSlurper = new XmlSlurper()
        rssRetriever.process()
        weather.rssRetriever = rssRetriever
        def conditions = weather.conditions()

        expect:
        that conditions, matches(".*F")
    }
}
