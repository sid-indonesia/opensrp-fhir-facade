/* (C)2022 */
package org.sidindonesia.opensrpfhirfacade;

import org.sidindonesia.opensrpfhirfacade.servlet.FhirRestfulServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan(basePackageClasses = {FhirRestfulServer.class})
@SpringBootApplication
public class OpenSRPFhirFacadeApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenSRPFhirFacadeApplication.class, args);
	}
}
