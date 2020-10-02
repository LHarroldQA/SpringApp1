package com.qa.app.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qa.app.DTO.BandDTO;
import com.qa.app.persistence.domain.Band;
import com.qa.app.service.BandService;

@SpringBootTest
public class BandControllerUnitTest {
	
	@Autowired
	private BandController controller;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@MockBean
	private BandService service;
	
	private List<Band> bandList;
	private Band testBand;
	private Band testBandWithId;
	private BandDTO bandDTO;
	private final Long id = 1L;
	
    private BandDTO mapToDTO(Band band) {
        return this.modelMapper.map(band, BandDTO.class);
    }
    
    @BeforeEach
    void init() {
    	this.bandList = new ArrayList<>();
    	this.testBand = new Band("Big band");
    	this.testBandWithId = new Band(testBand.getName());
    	this.testBandWithId.setId(id);
    	this.bandList.add(testBandWithId);
    	this.bandDTO = this.mapToDTO(testBandWithId);
    }
    
    @Test
    void createTest() {
        when(this.service.create(testBand))
            .thenReturn(this.bandDTO);
        
        //assertThat(expected . isEqualTo(actual))
        assertThat(new ResponseEntity<BandDTO>(this.bandDTO, HttpStatus.CREATED))
                .isEqualTo(this.controller.create(testBand));
        
        verify(this.service, times(1))
            .create(this.testBand);
    }
	
    @Test
    void readTest() {
    	when(this.service.readOne(this.id))
    		.thenReturn(this.bandDTO);
    	
    	assertThat(new ResponseEntity<BandDTO>(this.bandDTO,HttpStatus.OK))
    		.isEqualTo(this.controller.getBandById(this.id));
    	
    	verify(this.service,times(1))
    		.readOne(this.id);
    }
    
    @Test
    void readAllTest() {
    	when(this.service.readAll())
    		.thenReturn(this.bandList.stream().map(this::mapToDTO).collect(Collectors.toList()));
    	
    	assertThat(this.controller.getAllBands().getBody().isEmpty()).isFalse();
    	
    	verify(this.service, times(1)).readAll();
    }
    
    @Test
    void updateTest() {
    	BandDTO newBand = new BandDTO(null,"Not so big band", null);
    	BandDTO updatedBand = new BandDTO(this.id,newBand.getName(),newBand.getGuitarists());
    	
    	when(service.update(newBand, id)).thenReturn(updatedBand);
    	
    	assertThat(new ResponseEntity<BandDTO>(updatedBand,HttpStatus.ACCEPTED))
    		.isEqualTo(this.controller.updateBandById(this.id, newBand));
    	
    	verify(this.service,times(1))
    		.update(newBand, this.id);
    }
    
    @Test
    void deleteTest() {
    	this.controller.deleteGuitarist(id);
    	
    	verify(this.service, times(1)).delete(id);
    }

}
