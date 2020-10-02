package com.qa.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.app.DTO.GuitaristDTO;
import com.qa.app.exception.GuitaristNotFoundException;
import com.qa.app.persistence.domain.Guitarist;
import com.qa.app.persistence.repo.GuitaristRepo;
import com.qa.app.utils.AppBeanUtils;

@Service
public class GuitaristService {
	
	private GuitaristRepo repo;
	
	private ModelMapper mapper;

    @Autowired
    public GuitaristService(GuitaristRepo repo, ModelMapper mapper) {
        super();
        this.repo = repo;
        this.mapper = mapper;
    }
	
	private GuitaristDTO mapToDTO(Guitarist guitarist) {
		return this.mapper.map(guitarist, GuitaristDTO.class);
	}

//	private Guitarist mapFromDTO(GuitaristDTO guitaristDTO) {
//		return this.mapper.map(guitaristDTO, Guitarist.class);
//	}
//	
	//create
	public GuitaristDTO create(Guitarist guitarist) {
//		Guitarist toSave =  this.mapFromDTO(guitaristDTO);
		Guitarist saved = this.repo.save(guitarist);
		return this.mapToDTO(saved);
	}
	
	//read
	public List<GuitaristDTO> readAll(){
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	//read by id
	public GuitaristDTO readOne(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow(GuitaristNotFoundException::new));
	}
	
	//update
	public GuitaristDTO update(GuitaristDTO guitaristDTO,Long id) {
		Guitarist toUpdate = this.repo.findById(id).orElseThrow(GuitaristNotFoundException::new);
		AppBeanUtils.mergeObject(guitaristDTO, toUpdate);
		return this.mapToDTO(this.repo.save(toUpdate));
	}
	
	//delete
	public boolean delete(Long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}

}
