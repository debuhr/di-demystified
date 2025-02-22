package application.adapter.datastore

import application.domain.Part
import spock.lang.Specification

class CsvPartsRepositoryTest extends Specification {
    CsvPartsRepository repository = new CsvPartsRepository()

    def "Repository loads parts from properties file"() {
        when: "all parts are requested"
        List<Part> parts = repository.findAll()

        then: "the parts are loaded"

        List<String> partNames = parts.collect({ part -> part.name })
        partNames.contains("side window")
        partNames.contains("break light")
    }
}
