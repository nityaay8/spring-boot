package com.emp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emp.model.EmpDTO;
import com.emp.service.EmpService;

@RestController
@RequestMapping("emp")
public class EmpController {

	@Autowired
	private EmpService empService;

	@PostMapping
	public ResponseEntity<EmpDTO> save(@RequestBody EmpDTO empDTO) {
		EmpDTO saved = empService.save(empDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@GetMapping("{id}")
	public ResponseEntity<EmpDTO> get(@PathVariable Integer id) {
		EmpDTO saved = empService.get(id);
		return ResponseEntity.ok(saved);
	}

	@PutMapping
	public ResponseEntity<EmpDTO> update(@RequestBody EmpDTO empDTO) {
		EmpDTO saved = empService.update(empDTO);
		return ResponseEntity.ok(saved);
	}

	@DeleteMapping("{id}")
	public ResponseEntity remove(@PathVariable Integer id) {
		empService.remove(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("all")
	public ResponseEntity<List<EmpDTO>> getAll() {
		List<EmpDTO> empList = empService.getEmployees();
		return ResponseEntity.ok(empList);
	}
}
