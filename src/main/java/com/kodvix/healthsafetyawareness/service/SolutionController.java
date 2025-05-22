package com.kodvix.healthsafetyawareness.service;

import com.kodvix.healthsafetyawareness.entity.Solution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
public class SolutionController {
	
	@Autowired
	private SolutionService solutionService;
	
	
	@PostMapping("/solution")                           //USED TO CREATE(INSERT) DATA
	public Solution addSolutionDetails(@RequestBody Solution sol) {
		return solutionService.createSolution(sol);
	}
	
	
	@GetMapping("solution")                             //USED TO READ DATA
	public List<Solution> getAllSolutionDetails() {
		return solutionService.getAllSolution();
	}
	
	
	@GetMapping("/solution/{id}")                       //USED TO READ SINGLE DATA
	public ResponseEntity<Solution> getSingleSolution(@PathVariable Long id) {
		Solution sol = solutionService.getSingleSolution(id).orElse(null);
		
		if (sol != null) {
			return ResponseEntity.ok().body(sol);
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
	
	
	@PutMapping("/solution/{id}")                       //USED TO UPDATE DATA
	public ResponseEntity<Solution> updateSolution(@PathVariable Long id, @RequestBody Solution prob) {
		Solution updatedSolution = solutionService.updateSolution(id, prob);
		
		if (updatedSolution != null) {
			return ResponseEntity.ok(updatedSolution);
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
	
	
	@DeleteMapping("/solution/{id}")                    //USED TO DELETE DATA
	public ResponseEntity<Void> deleteSolution(@PathVariable Long id) {
		solutionService.deleteSolution(id);
		return ResponseEntity.noContent().build();
	}
}