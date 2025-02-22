import spock.lang.Specification

class ApplicationTest extends Specification {
    def "Application can be started"() {
        given: "an Application class"
        Application application = new Application()

        when: "the Application is started"
        application.main()

        then: "no exception is thrown"
        noExceptionThrown()
    }
}