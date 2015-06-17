import spock.lang.Specification

class RssRetrieverNetTests  extends  Specification {

    XmlSlurper slurper
    RssRetriever retriever

    def setup() {
        slurper = Mock(XmlSlurper) {

            def rssText = this.getClass().getResource("rssContent.txt").text
            def result = new XmlSlurper().parseText(rssText)
            2 * parse(_) >> { throw new IOException("fake exception1") }
            1 *parse(_) >> result
        }
        retriever  = new RssRetriever(slurper)
    }

    def "when network not available then retry"() {
        given:
        retriever.process()

        expect:
        retriever.name() == "rss"
        retriever.title() == "Yahoo! Weather - Columbus, OH"
    }
}
