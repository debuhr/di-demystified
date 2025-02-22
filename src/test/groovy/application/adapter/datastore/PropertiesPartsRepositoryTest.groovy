package application.adapter.datastore

import application.domain.Part
import spock.lang.Specification

class PropertiesPartsRepositoryTest extends Specification {
    PropertiesPartsRepository repository = new PropertiesPartsRepository()

    def "Repository loads parts from properties file"() {
        when: "all parts are requested"
        List<Part> parts = repository.findAll()

        then: "the parts are loaded"

        List<String> partNames = parts.collect({ part -> part.name })
        partNames.contains("steering wheel")
        partNames.contains("connecting rod")
        partNames.contains("suspension spring")
        partNames.contains("spark plug")
    }
}
