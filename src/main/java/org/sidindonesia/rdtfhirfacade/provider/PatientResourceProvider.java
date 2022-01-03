package org.sidindonesia.rdtfhirfacade.provider;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Enumerations.AdministrativeGender;
import org.hl7.fhir.r4.model.Patient;
import org.opensrp.api.domain.Client;
import org.springframework.web.reactive.function.client.WebClient;

import ca.uhn.fhir.rest.annotation.RequiredParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.server.IResourceProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
				.uri(builder -> builder.path("/rest/client/search").queryParam("name", name).build())
				.headers(headers -> headers.setBearerAuth(
						"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJpWGV6TGRrQ0xhNVIyamladVZaN2N4Z3pxcDFQUF9UUGtVSzM2TXB0VTUwIn0.eyJleHAiOjE2NDAzOTM4ODUsImlhdCI6MTY0MDMwNzQ4NSwianRpIjoiYjQ2MjA4OTEtMjA2NC00NDcyLWE5N2QtNGM5ZmE3YzQxMGIxIiwiaXNzIjoiaHR0cHM6Ly9rZXljbG9hay5yZHQuc3RhZ2Uuc2lkLWluZG9uZXNpYS5vcmcvYXV0aC9yZWFsbXMvcmR0LXN0YWdlIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjkyOGE4OGM5LTAzZjUtNGFjNy04ZTFlLWNiN2E0M2RlYmI0NyIsInR5cCI6IkJlYXJlciIsImF6cCI6ImRldi1jbGllbnQiLCJzZXNzaW9uX3N0YXRlIjoiMWI0OWQ4MTgtOTYxOS00ZDBhLWE3ZGItMDFkMjYwNTRkMTVlIiwiYWNyIjoiMSIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJPUEVOTVJTIiwiUExBTlNfRk9SX1VTRVIiLCJBTExfRVZFTlRTIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJvcGVuaWQgcHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYW1lIjoiU0lEIFJEVCBTdGFnaW5nIiwicHJlZmVycmVkX3VzZXJuYW1lIjoic2lkIiwiZ2l2ZW5fbmFtZSI6IlNJRCIsImZhbWlseV9uYW1lIjoiUkRUIFN0YWdpbmciLCJlbWFpbCI6InNpZC5kZXZlbG9wZXJAc2lkLWluZG9uZXNpYS5vcmcifQ.eXzLrE03T1UUrSHc-zr6stDNgDnRHZDJfhtk44ge6qVSO4_QDZh6kfBC6gEeIySJQQs3dM8bYeKe7-jj9YWVFRHZs-QRxvxy6-HZpmlkEO1vfSjz6LwuumYNZ1Z0YFc3OB17lyNmn_Z7X6JM5Klu0M5coTKndulYruPwy6OSnm7lfKkMFPAgV5Zqev9ev3XurcxhDxLqy5o19dG8ketGQ1k5JgqTllL7AB47Zg5Z6JJUjN1uIhZz8PP-TzJ2hkxgQjyLPjT4nek3EfAWlIgUw753ty028LkxZX1B_5ATaO4HSku7jbhZNs79adWsQmESIuAiNlxLHPTjPVGabsfcfQ"))
				.retrieve().bodyToFlux(Client.class);

		client.doOnNext((c) -> {
			System.out.println(c);
		});
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
