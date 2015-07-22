import spock.lang.Specification


class WeatherIntTest  extends  Specification {

    def "weather gives you reasonable conditions"() {
        def weather = new Weather(getRetriever())
        String conditions = weather.conditions()

        expect:
        assert conditions =~ /.*°F/
    }


    def "weather aggregate saves conditions to file"() {
        def conditionsFileName = "saves_conditions.txt"
        def forecastFileName = "saves_forecast.txt"
        def weather = new Weather(getRetriever(), new File(conditionsFileName), new File(forecastFileName))

        weather.writeAggregateFiles()

        expect:
        def conditionsText = getTextFromFile(new File(conditionsFileName))
        def forecastText = getTextFromFile(new File(forecastFileName))
        conditionsText.get(0).size() > 0
        forecastText.size() == 5
    }

    def "weather aggregate option saves weather info to files as requested"() {
        def conditionsFileName = "/tmp/conditions.txt"

        String[] args = new String[1]
        args[0] = "aggregate"
        Weather.main(args)

        expect:
        def conditionsText = getTextFromFile(new File(conditionsFileName))
        conditionsText.get(0).size() > 0
    }

    private List<String> getTextFromFile(File file) {
        def lines = file.readLines()
        file.delete()
        lines
    }

    private RssRetriever getRetriever() {
        def rssRetriever = new RssRetriever()
        rssRetriever.xmlSlurper = new XmlSlurper()
        rssRetriever.process()
        rssRetriever
    }
}
