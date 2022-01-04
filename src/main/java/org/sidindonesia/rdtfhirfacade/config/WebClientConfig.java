/* (C)2022 */
package org.sidindonesia.rdtfhirfacade.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	@Bean
	public WebClient openSRPServerClient(@Value("${opensrp-server.base-url}") String baseURL) {
		return WebClient.create(baseURL);
	}
}
