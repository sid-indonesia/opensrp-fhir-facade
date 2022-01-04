/* (C)2022 */
package org.sidindonesia.rdtfhirfacade.config;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

//	@Bean
//	public WebClient openSRPServerClient(@Value("${opensrp-server.base-url}") String baseURL,
//		OAuth2AuthorizedClientManager authorizedClientManager) {
//		ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2Client =
//            new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
//
//
//		return WebClient.builder().baseUrl(baseURL).apply(oauth2Client.oauth2Configuration()).build();
//	}
	@Bean
	public WebClient openSRPServerClient(ListableBeanFactory beanFactory,
		@Value("${opensrp-server.base-url}") String baseURL) {

		for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
			System.out.println(beanDefinitionName);
		}

		return WebClient.builder().baseUrl(baseURL).build();
	}
}
