import spock.lang.Specification

/**
 * Created by ovi on 11/8/14.
 */
class RssRetrieverTest extends  Specification {

    def "it gets content from Yahoo weather site when asked to get contents from URL with node rss"() {
        def rss = new RssRetriever().getContent()

        expect:
        rss.name() == "rss"
        rss.channel.title.text() == "Yahoo! Weather - Columbus, OH"
    }
}
