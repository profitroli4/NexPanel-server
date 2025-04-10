package com.profitroli4.nexpanel;

import com.profitroli4.nexpanel.detector.AndroidDeviceDetector;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NexpanelApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(NexpanelApplication.class, args);
	}

	@Override
	public void run(String... args) {
		AndroidDeviceDetector detector = new AndroidDeviceDetector();
		detector.findAndroidDevices();
	}

}
