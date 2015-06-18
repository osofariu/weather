import spock.lang.Specification

class RssRetrieverNetTests  extends  Specification {

    def "when network at first not available and then available then it should retrieve the weather data"() {
        given:
        def retriever = makeRetriever(5, 1)

        when:
        retriever.process()

        then:
        retriever.name() == "rss"
        retriever.title() == "Yahoo! Weather - Columbus, OH"
    }

    def "when network is unavailable max try count minus 1 it should still work"() {
        given:
        def retriever = makeRetriever(3, 2)

        when:
        retriever.process()

        then:
        retriever.name() == "rss"
        retriever.title() == "Yahoo! Weather - Columbus, OH"
    }

    def "when network is unavailable max try count we should get an exception"() {
        given:
        def retriever = makeRetriever(1, 1)

        when:
        retriever.process()

        then:
        thrown(IOException)
    }

    def makeRetriever(tryCount, failCount) {
        XmlSlurper slurper = Mock(XmlSlurper)

        def rssText = this.getClass().getResource("rssContent.txt").text
        def result = new XmlSlurper().parseText(rssText)

        failCount * slurper.parse(_) >> { throw new IOException("fake IO exception") }
        if (tryCount > failCount) {
            1 * slurper.parse(_) >> result
        }

        RssRetriever retriever = new RssRetriever(slurper)
        retriever.tryCount = tryCount
        retriever.sleepDuration = 1
        return retriever
    }
}
