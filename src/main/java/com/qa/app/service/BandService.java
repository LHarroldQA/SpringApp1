package com.qa.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.app.DTO.BandDTO;
import com.qa.app.exception.BandNotFoundException;
import com.qa.app.persistence.domain.Band;
import com.qa.app.persistence.repo.BandRepo;
import com.qa.app.utils.AppBeanUtils;

@Service
public class BandService {
	
	private BandRepo repo;
	
	private ModelMapper mapper;

	@Autowired
	public BandService(BandRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}
	
	private BandDTO mapToDTO(Band band) {
		return this.mapper.map(band, BandDTO.class);
	}

//	private Band mapFromDTO(BandDTO bandDTO) {
//		return this.mapper.map(bandDTO, Band.class);
//	}
	
	public BandDTO create(Band band) {
//		Band toSave =  this.mapFromDTO(bandDTO);
		Band saved = this.repo.save(band);
		return this.mapToDTO(saved);
	}
	
	public List<BandDTO> readAll(){
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	//read by id
	public BandDTO readOne(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow(BandNotFoundException::new));
	}
	
	//update
	public BandDTO update(BandDTO bandDTO,Long id) {
		Band toUpdate = this.repo.findById(id).orElseThrow(BandNotFoundException::new);
		AppBeanUtils.mergeObject(bandDTO, toUpdate);
		return this.mapToDTO(this.repo.save(toUpdate));
	}
	
	//delete
	public boolean delete(Long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}

}
