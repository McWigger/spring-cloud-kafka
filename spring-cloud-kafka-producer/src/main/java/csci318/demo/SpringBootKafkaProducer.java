package csci318.demo;

import csci318.demo.binding.ProducerBinding;
import csci318.demo.model.Appliance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableBinding(ProducerBinding.class)
public class SpringBootKafkaProducer {

	private static final Logger log = LoggerFactory.getLogger(SpringBootKafkaProducer.class);
	private static final String url = "https://random-data-api.com/api/appliance/random_appliance";

	public static void main(String[] args) {
		SpringApplication.run(SpringBootKafkaProducer.class, args);
	}

	// With @Bean annotation of the following method, a RestTemplate object is injected.
	// But there is no need to define a separate method to return a RestTemplateBuilder object.
	// This is due to the following reason:
	// "In a typical auto-configured Spring Boot application
	// this builder is available as a bean and can be injected
	// whenever a RestTemplate is needed." -- from RestTemplateBuilder API Doc
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate, ProducerBinding binding) throws Exception {
		return args -> {
			try{
				while(!Thread.currentThread().isInterrupted()){
					Appliance appliance = restTemplate.getForObject(url, Appliance.class);
					assert  appliance != null;
					log.info(appliance.toString());
					binding.outbound().send(MessageBuilder.withPayload(appliance).build());
					Thread.sleep(1200);
				}
			}
			catch(InterruptedException ignored){
			}
		};
	}
}
