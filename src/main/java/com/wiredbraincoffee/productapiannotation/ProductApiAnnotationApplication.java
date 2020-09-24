package com.wiredbraincoffee.productapiannotation;

import com.wiredbraincoffee.productapiannotation.model.Product;
import com.wiredbraincoffee.productapiannotation.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class ProductApiAnnotationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApiAnnotationApplication.class, args);
	}

	@Bean
	CommandLineRunner init(ProductRepository repository){
		return args -> {
			Flux<Product> productFlux = Flux.just(
					new Product(null, "Big Latte", 2.99),
					new Product(null, "Big Latte2", 2.99),
					new Product(null, "Big Latte3", 2.99)
			)
					//.flatMap(p -> repository.save(p));
					.flatMap(repository::save);

			productFlux
					.thenMany(repository.findAll())
					.subscribe(System.out::println);
		};
	}

}
