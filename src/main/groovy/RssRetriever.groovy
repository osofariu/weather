/**
 * Created by ovi on 11/8/14.
 */
class RssRetriever {

    def  getContent() {
       new XmlSlurper().parse("http://weather.yahooapis.com/forecastrss?w=12776196").declareNamespace(
               'yweather':'http://xml.weather.yahoo.com/ns/rss/1.0', 'geo':'http://www.w3.org/2003/01/geo/wgs84_pos#')
    }
}
