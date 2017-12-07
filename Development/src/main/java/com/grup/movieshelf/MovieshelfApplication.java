///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/////////////////////////////////////////////////////////////
// MovieShelfApplication
// The main entry point for the backend application.
/////////////////////////////////////////////////////////////

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class MovieshelfApplication {
	//------------------------------------------------
	// Main Method
	// Runs the project.
	//------------------------------------------------

	public static void main(String[] args) {
		SpringApplication.run(MovieshelfApplication.class, args);
	}
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////
