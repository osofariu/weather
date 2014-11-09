import spock.lang.Specification

/**
 * Created by ovi on 11/8/14.
 */
class WeatherTest extends  Specification {

    def rssRetrieverMock = Mock(RssRetriever)
    def rss, rss_live;

    def setup() {
        Weather weather_live = new Weather(new RssRetriever())   // stop using this.. use mocked rssRetriever
        rss_live = weather_live.retrieveContents()

        Weather weather = new Weather(rssRetrieverMock)
        def rssText = this.getClass().getResource("rssContent.txt").text
        def xmlNode =  new XmlSlurper().parseText(rssText).declareNamespace(
                'yweather':'http://xml.weather.yahoo.com/ns/rss/1.0', 'geo':'http://www.w3.org/2003/01/geo/wgs84_pos#')
        rssRetrieverMock.getContent() >> xmlNode
        rss = weather.retrieveContents()
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
        rss_live.name() == "rss"
        rss.name() == "rss"
    }

    def "given yahoo weather URL, it it will get one node called item"() {
        expect:
        rss_live.channel.item.name() == "item"
        rss.channel.item.name() == "item"
    }

    def "Item has a title that starts with Conditions for"() {
        def title = rss.channel.item.title
        def title_live = rss_live.channel.item.title
        expect:
        title_live.text().startsWith("Conditions for")
        title.text().startsWith("Conditions for")
    }
    //rss.channel.childNodes()[12].children()[5].attributes["date"]
    //rss.channel.item.'yweather:condition'.@text
    def "Item has a node called condition that has current conditions"() {
        def cond = rss.channel.item.'yweather:condition'.@text
        def cond_live = rss_live.channel.item.'yweather:condition'.@text
        expect:
        cond_live.text().length() > 0
        cond.text().length() > 0
    }
}
