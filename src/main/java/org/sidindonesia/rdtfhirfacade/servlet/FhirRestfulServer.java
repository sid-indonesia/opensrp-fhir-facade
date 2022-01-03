package org.sidindonesia.rdtfhirfacade.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.sidindonesia.rdtfhirfacade.provider.PatientResourceProvider;
import org.springframework.web.reactive.function.client.WebClient;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.server.RestfulServer;
import ca.uhn.fhir.rest.server.interceptor.ResponseHighlighterInterceptor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@WebServlet("/*")
public class FhirRestfulServer extends RestfulServer {
	private final WebClient webClient;

	@Override
	protected void initialize() throws ServletException {
		// Create a context for the appropriate version
		setFhirContext(FhirContext.forR4());
		
		// Register resource providers
		registerProvider(new PatientResourceProvider(webClient));
		
		// Format the responses in nice HTML
		registerInterceptor(new ResponseHighlighterInterceptor());
	}
}
