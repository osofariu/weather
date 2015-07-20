class Weather {

    def rssRetriever
    def conditionsFile
    def forecastFile

    def Weather(RssRetriever rssRetriever) {
        this.rssRetriever = rssRetriever
    }

    def Weather(rssRetriever, conditionsFile,  forecastFile) {
        this.rssRetriever = rssRetriever
        this.conditionsFile = conditionsFile
        this.forecastFile = forecastFile
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
        cond  + ", " + temp  +  "°F"
    }

    def forecast() {
        def result = ""
        rssRetriever.forecast().each {
           result +=  "${it["day"]}\t${it["low"]}°F - ${it["high"]}°F\t\t${it["forecast"]}\n"
        }
        result
    }

    def writeAggregateFiles() {
        conditionsFile.withWriter {it << conditions()}
        //forecastFile.withWriter   {it << forecast()}
    }

    static RssRetriever getRetriever() {
        def rssRetriever = new RssRetriever()
        rssRetriever.xmlSlurper = new XmlSlurper()
        rssRetriever.process()
        rssRetriever
    }

    static main(args) {
        Weather weather = new Weather(getRetriever(), new File("/tmp/conditions.txt"), new File("/tmp/forecast.txt"))
        if (args.size() <= 0) {
            println "You should provide an argument [conditions | icon | forecast]"
             System.exit(1)
        }

        switch (args[0]) {
            case "conditions" : println weather.conditions(); break
            case "icon" : println weather.currentIcon(); break
            case "forecast" : println weather.forecast(); break
            case "aggregate" : weather.writeAggregateFiles(); break
            default:
                println "The argument most be one of: [conditions | icon | forecast]"
        }
    }
}
