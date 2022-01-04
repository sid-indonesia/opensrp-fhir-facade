/* (C)2022 */
package org.sidindonesia.rdtfhirfacade;

import org.sidindonesia.rdtfhirfacade.servlet.FhirRestfulServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan(basePackageClasses = {FhirRestfulServer.class})
@SpringBootApplication
public class RdtFhirFacadeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RdtFhirFacadeApplication.class, args);
	}
}
