/* (C)2022 */
package org.sidindonesia.opensrpfhirfacade.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	@Bean
	ReactiveClientRegistrationRepository clientRegistrations(
			@Value("${spring.security.oauth2.client.provider.keycloak.token-uri}") String tokenUri,
			@Value("${spring.security.oauth2.client.registration.keycloak.client-id}") String clientId,
			@Value("${spring.security.oauth2.client.registration.keycloak.client-secret}") String clientSecret,
			@Value("${spring.security.oauth2.client.registration.keycloak.authorization-grant-type}") String authorizationGrantType) {
		ClientRegistration registration = ClientRegistration.withRegistrationId("keycloak").tokenUri(tokenUri)
				.clientId(clientId).clientSecret(clientSecret)
				.authorizationGrantType(new AuthorizationGrantType(authorizationGrantType)).build();
		return new InMemoryReactiveClientRegistrationRepository(registration);
	}

	@Bean
	public WebClient openSRPServerClient(@Value("${opensrp-server.base-url}") String baseURL,
			ReactiveClientRegistrationRepository clientRegistrations) {
		InMemoryReactiveOAuth2AuthorizedClientService clientService = new InMemoryReactiveOAuth2AuthorizedClientService(
				clientRegistrations);
		AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager = new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(
				clientRegistrations, clientService);
		ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(
				authorizedClientManager);
		oauth.setDefaultClientRegistrationId("keycloak");
		return WebClient.builder().baseUrl(baseURL).filter(oauth).build();
	}
}
