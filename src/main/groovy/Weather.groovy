/**
 * Created by ovi on 11/8/14.
 */
class Weather {

    def rssRetriever

    def Weather(RssRetriever rssRetriever) {
       this.rssRetriever = rssRetriever
    }

    def currentConditions() {
        rssRetriever.currentConditions()
    }

    def currentTemperature() {
        rssRetriever.currentTemperature()
    }

    def currentIcon() {
        rssRetriever.currentIcon()
    }

    def conditions() {
        def cond = rssRetriever.currentConditions()
        def temp = rssRetriever.currentTemperature()
        return cond  + ", " + temp  +  "°F"
    }

    def forecast() {
        def result = ""
        rssRetriever.forecast().each {
           result +=  "${it["day"]}\t${it["low"]}°F - ${it["high"]}°F\t\t${it["forecast"]}\n"
        }
        result
    }

    static main(args) {
        RssRetriever retriever = new RssRetriever(new XmlSlurper())
        Weather weather = new Weather(retriever)
        if (args.size() <= 0) {
            println "You should provide an argument [conditions | icon | forecast]"
             System.exit(1)
        }

        switch (args[0]) {
            case "conditions" : println weather.conditions(); break
            case "icon" : println weather.currentIcon(); break
            case "forecast" : println weather.forecast(); break
            default:
                println "The argument most be one of: [conditions | icon | forecast]"
        }
    }
}
