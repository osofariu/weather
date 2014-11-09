/**
 * Created by ovi on 11/8/14.
 */
class Weather {

    def rssRetriever

    def Weather(RssRetriever rssRetriever) {
       this.rssRetriever = rssRetriever
    }

    def retrieveContents() {
        rssRetriever.getContent()
    }
}
