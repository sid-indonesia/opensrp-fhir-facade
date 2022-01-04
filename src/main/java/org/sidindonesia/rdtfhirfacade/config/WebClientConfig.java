/* (C)2022 */
package org.sidindonesia.rdtfhirfacade.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	@Bean
	public WebClient openSRPServerClient(@Value("${opensrp-server.base-url}") String baseURL,
			ClientRegistrationRepository clientRegistrations, OAuth2AuthorizedClientRepository authorizedClients) {

		ServletOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServletOAuth2AuthorizedClientExchangeFilterFunction(
				clientRegistrations, authorizedClients);
		oauth.setDefaultOAuth2AuthorizedClient(true);

		return WebClient.builder().baseUrl(baseURL).filter(oauth).build();
	}
}
