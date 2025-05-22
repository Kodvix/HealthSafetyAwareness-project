package com.kodvix.healthsafetyawareness.service;

import com.kodvix.healthsafetyawareness.entity.Solution;
import com.kodvix.healthsafetyawareness.repository.SolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SolutionService {
	
	@Autowired
	private SolutionRepository solutionRepository;

	
	public Solution createSolution(Solution sol) {                            //FOR CREATEING(INSERTING) DATA
		return solutionRepository.save(sol);
	}

	
	public List<Solution> getAllSolution() {                              //FOR READING DATA
		return solutionRepository.findAll();
	}
	
	
	public Optional<Solution> getSingleSolution(Long id) {                  //FOR READING SINGLE DATA
		return solutionRepository.findById(id);
	}


	public Solution updateSolution(Long id, Solution newSolution) {                 //FOR UPDATING DATA
		Solution SolutionData = solutionRepository.findById(id).orElse(null);
		
		if (SolutionData != null) {
			return solutionRepository.save(newSolution);
		}
		
		else {
			throw new RuntimeException("Solution not found with id: " + id);
		}
	}


	public void deleteSolution(Long id) {                               //FOR DELETING DATA
		solutionRepository.deleteById(id);
	}
	
}