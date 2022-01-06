/* (C)2022 */
package org.sidindonesia.rdtfhirfacade.provider;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.DateType;
import org.hl7.fhir.r4.model.Observation;
import org.opensrp.api.domain.Client;
import org.springframework.web.reactive.function.client.WebClient;

import ca.uhn.fhir.rest.annotation.RequiredParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.server.IResourceProvider;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class ObservationResourceProvider implements IResourceProvider {

	private final WebClient webClient;

	@Override
	public Class<? extends IBaseResource> getResourceType() {
		return Observation.class;
	}

	@Search()
	public List<Observation> search(@RequiredParam(name = Observation.SP_DATE) DateType date) {
		Flux<Client> client = webClient.get()
				.uri(builder -> builder.path("/rest/client/search").queryParam("date", date).build()).retrieve()
				.bodyToFlux(Client.class);

		List<Client> clientOpenSRP = client.toStream().collect(toList());

		return clientOpenSRP.stream().map(c -> {
			Observation observation = new Observation();
			observation.setId(c.getBaseEntityId());
			return observation;
		}).collect(toList());
	}

	@Search
	public List<Observation> search() {
		List<Observation> retVal = new ArrayList<>(); // populate this
		// Communicatie with OpenSRP Server API
		Observation observation = new Observation()
				.setCode(new CodeableConcept(new Coding("testSystem", "testCode", "testDisplay")));
		observation.setId("testId");
		retVal.add(observation);
		return retVal;
	}
}
