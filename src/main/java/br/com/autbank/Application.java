package br.com.autbank;

import arch.runtime.microservice.Arch;
import arch.runtime.microservice.app.ArchApp;

@ArchApp
public class Application {
	public static void main(String[] args) {
		Arch.run(Application.class, args);
	}
}
