import spock.lang.Specification

/**
 * Created by ovi on 11/8/14.
 */
class WeatherTest extends  Specification {

    def rss

    def setup() {
        def rssRetrieverMock = Mock(RssRetriever)
        setupRssRetrieverMock(rssRetrieverMock)
        Weather weather = new Weather(rssRetrieverMock)
        rss = weather.retrieveContents()
    }

    private void setupRssRetrieverMock(rssRetrieverMock) {
        def rssText = this.getClass().getResource("rssContent.txt").text
        def xmlNode = new XmlSlurper().parseText(rssText).declareNamespace(
                'yweather': 'http://xml.weather.yahoo.com/ns/rss/1.0', 'geo': 'http://www.w3.org/2003/01/geo/wgs84_pos#')
        rssRetrieverMock.getContent() >> xmlNode
    }

    def "when Weather is asked to retrieve contents it uses RSsRetriever"() {
        def rssRetrieverMock = Mock(RssRetriever)
        def weather = new Weather(rssRetrieverMock)

        when:
        weather.retrieveContents()

        then:
        1 * rssRetrieverMock.getContent()
    }


    def "root of the rss tree is rss"() {
        expect:
        rss.name() == "rss"
    }

    def "given yahoo weather URL, it it will get one node called item"() {
        expect:
        rss.channel.item.name() == "item"
    }

    def "Item has a title that starts with Conditions for"() {
        def title = rss.channel.item.title

        expect:
        title.text().startsWith("Conditions for")
    }

    def "Item has a node called condition that has current conditions"() {
        def cond = rss.channel.item.'yweather:condition'.@text

        expect:
        cond.text().length() > 0
    }
}
