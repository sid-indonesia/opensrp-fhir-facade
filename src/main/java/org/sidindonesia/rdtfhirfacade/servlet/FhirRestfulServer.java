/* (C)2022 */
package org.sidindonesia.rdtfhirfacade.servlet;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.server.RestfulServer;
import ca.uhn.fhir.rest.server.interceptor.ResponseHighlighterInterceptor;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import lombok.RequiredArgsConstructor;

import org.sidindonesia.rdtfhirfacade.provider.ObservationResourceProvider;
import org.sidindonesia.rdtfhirfacade.provider.PatientResourceProvider;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@WebServlet("/*")
public class FhirRestfulServer extends RestfulServer {
	private final WebClient webClient;

	@Override
	protected void initialize() throws ServletException {
		// Create a context for the appropriate version
		setFhirContext(FhirContext.forR4());

		// Register resource providers
		registerProviders(new PatientResourceProvider(webClient), new ObservationResourceProvider(webClient));

		// Format the responses in nice HTML
		registerInterceptor(new ResponseHighlighterInterceptor());
	}
}
