package application


import di.ApplicationContext
import spock.lang.Specification

class ApplicationTest extends Specification {

    def setup() {
        ApplicationContext.clear()
    }

    def "application.Application can be started"() {
        given: "an application.Application class"
        Application application = new Application()

        when: "the application.Application is started"
        application.main()

        then: "no exception is thrown"
        noExceptionThrown()
    }
}