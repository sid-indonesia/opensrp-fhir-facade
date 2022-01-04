/* (C)2022 */
package org.sidindonesia.rdtfhirfacade.provider;

import static java.util.stream.Collectors.toList;

import ca.uhn.fhir.rest.annotation.RequiredParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.server.IResourceProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Enumerations.AdministrativeGender;
import org.hl7.fhir.r4.model.Patient;
import org.opensrp.api.domain.Client;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class PatientResourceProvider implements IResourceProvider {

	private final WebClient webClient;

	@Override
	public Class<? extends IBaseResource> getResourceType() {
		return Patient.class;
	}

	@Search()
	public List<Patient> search(@RequiredParam(name = Patient.SP_NAME) String name) {
		Flux<Client> client = webClient.get()
				.uri(builder -> builder.path("/rest/client/search").queryParam("name", name).build()).retrieve()
				.bodyToFlux(Client.class);

		List<Client> clientOpenSRP = client.toStream().collect(toList());

		return clientOpenSRP.stream().map(c -> {
			Patient patient = new Patient();
			patient.setId(c.getBaseEntityId());
			patient.addName().addGiven(c.getFirstName());
			patient.setGender("Female".equals(c.getGender()) ? AdministrativeGender.FEMALE : AdministrativeGender.MALE);
			return patient;
		}).collect(toList());
	}
}
