package com.pit.talks.measures;

import java.time.Instant;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@SpringBootApplication
public class MeasuresApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeasuresApplication.class, args);
	}

	public record Measure(String deviceId, String value, String unit, Instant measureAt) { }
	public record Alert(String value, boolean critical){}

	@RestController
	@RequiredArgsConstructor
	@RequestMapping("/measures")
	public static class MeasureController{

		private final StreamBridge streamBridge;

		@PostMapping
		@ResponseStatus(HttpStatus.CREATED)
		public void createMeasure( @RequestBody Measure measure){
			log.info("Controller value: [{}]", measure);
			streamBridge.send("measureCreated",measure );
		}
	}

	@Bean
	Consumer<Measure> measureNewConsumer(){
		return measure -> log.info("Consumer [{}]",measure);
	}

	@Bean
	Function<Measure,Alert> measureNewFunction(){
		return measure -> new Alert(measure.value ,  Integer.parseInt(measure.value)>35 );
	}
}
