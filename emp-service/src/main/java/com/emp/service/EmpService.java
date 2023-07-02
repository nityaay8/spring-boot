package com.emp.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.entity.EmpEntity;
import com.emp.exception.EmpException;
import com.emp.model.EmpDTO;
import com.emp.repository.EmpRepository;

@Service
public class EmpService {

	@Autowired
	private EmpRepository empRepository;

	@Autowired
	private ModelMapper modelMapper;

	public EmpDTO save(EmpDTO empDTO) {
		EmpEntity empEntity = modelMapper.map(empDTO, EmpEntity.class);
		EmpEntity savedEmpEntity = empRepository.save(empEntity);
		return modelMapper.map(savedEmpEntity, EmpDTO.class);
	}

	public EmpDTO update(EmpDTO empDTO) {
		Optional<EmpEntity> empOptional = empRepository.findById(empDTO.getId());
		if (empOptional.isEmpty()) {
			throw new EmpException("NOT_FOUND");
		}
		modelMapper.map(empDTO, empOptional.get());
		EmpEntity savedEmpEntity = empRepository.save(empOptional.get());
		return modelMapper.map(savedEmpEntity, EmpDTO.class);
	}

	public EmpDTO get(Integer id) {
		Optional<EmpEntity> empOptional = empRepository.findById(id);
		if (empOptional.isEmpty()) {
			throw new EmpException("NOT_FOUND");
		}

		return modelMapper.map(empOptional.get(), EmpDTO.class);
	}

	public void remove(Integer id) {
		Optional<EmpEntity> empOptional = empRepository.findById(id);
		if (empOptional.isEmpty()) {
			throw new EmpException("NOT_FOUND");
		}

		empRepository.delete(empOptional.get());
	}

	public List<EmpDTO> getEmployees() {
		List<EmpEntity> empEntities = empRepository.findAll();
		List<EmpDTO> empDTOList = empEntities.stream().map(empEntity -> modelMapper.map(empEntity, EmpDTO.class))
				.toList();
		return empDTOList;
	}
}
