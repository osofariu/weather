import groovy.util.slurpersupport.GPathResult

class RssRetriever {

    def xml
    def xmlSlurper
    def tryCount = 5
    def sleepDuration = 10000

    def process() {
        while (tryCount-- > 0) {
            sleepDuration *= 2
            try {
                xml = getContent()
                return
            } catch (IOException e) {
                System.sleep(sleepDuration)
            }
        }
        throw new IOException("Giving up re-trying..")
    }

    def GPathResult  getContent() {
        //GPathResult result = xmlSlurper.parse("http://weather.yahooapis.com/forecastrss?w=12776196")
        GPathResult result = xmlSlurper.parse(" https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%3D2383660")

        if (result != null) {
            result.declareNamespace('yweather': 'http://xml.weather.yahoo.com/ns/rss/1.0', 'geo': 'http://www.w3.org/2003/01/geo/wgs84_pos#')
        }
        return result
    }

    def name() {
        xml.name()
    }

    def title() {
        xml.results.channel.title.text()
    }

    def currentConditions() {
        xml.results.channel.item.'yweather:condition'.@text.text()
    }

    def currentTemperature() {
        xml.results.channel.item.'yweather:condition'.@temp.text()
    }

    def currentIcon() {
        xml.results.channel.item.'yweather:condition'.@code.text()
    }

    def forecast() {
        def ywforecast =  xml.results.channel.item.'yweather:forecast'
        def forecastList = [] as List
        ywforecast.list().each{
            def dayForecast = [day: it.@day, date: it.@date, low: it.@low, high: it.@high, forecast: it.@text, code: it.@code]
            forecastList << dayForecast
        }
        forecastList
    }
}
