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

import com.qa.app.DTO.GuitaristDTO;
import com.qa.app.persistence.domain.Guitarist;
import com.qa.app.service.GuitaristService;

@SpringBootTest
public class GuitaristControllerUnitTest {
	
	@Autowired
	private GuitaristController controller;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@MockBean
	private GuitaristService service;
	
	private List<Guitarist> guitaristList;
	private Guitarist testGuitarist;
	private Guitarist testGuitaristWithId;
	private GuitaristDTO guitaristDTO;
	private final Long id = 1L;

    private GuitaristDTO mapToDTO(Guitarist guitarist) {
        return this.modelMapper.map(guitarist, GuitaristDTO.class);
    }
    
    @BeforeEach
    void init() {
    	this.guitaristList = new ArrayList<>();
    	this.testGuitarist = new Guitarist("Liam",10,"Electric");
    	this.testGuitaristWithId = new Guitarist (testGuitarist.getName(), testGuitarist.getNoOfStrings(), testGuitarist.getType());
    	this.testGuitarist.setId(id);
    	this.guitaristList.add(testGuitaristWithId);
    	this.guitaristDTO = this.mapToDTO(testGuitaristWithId);
    }
    
    @Test
    void createTest() {
        when(this.service.create(testGuitarist))
            .thenReturn(this.guitaristDTO);
        
        //assertThat(expected . isEqualTo(actual))
        assertThat(new ResponseEntity<GuitaristDTO>(this.guitaristDTO, HttpStatus.CREATED))
                .isEqualTo(this.controller.create(testGuitarist));
        
        verify(this.service, times(1))
            .create(this.testGuitarist);
    }
    
    @Test
    void readTest() {
        when(this.service.readOne(this.id))
            .thenReturn(this.guitaristDTO);
        
        assertThat(new ResponseEntity<GuitaristDTO>(this.guitaristDTO, HttpStatus.OK))
                .isEqualTo(this.controller.getGuitaristById(this.id));
        
        verify(this.service, times(1))
            .readOne(this.id);
    }
    
    @Test
    void readAllTest() {
        when(service.readAll())
            .thenReturn(this.guitaristList
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList()));
        
        assertThat(this.controller.getAllGuitarists().getBody()
                .isEmpty()).isFalse();
        
        verify(this.service, times(1))
            .readAll();
    }
    
    @Test
    void updateTest() {
    	GuitaristDTO newGuitarist = new GuitaristDTO(null,"Harry",6,"Bass");
    	GuitaristDTO updatedGuitarist = new GuitaristDTO(this.id,newGuitarist.getName(),newGuitarist.getNoOfStrings(),newGuitarist.getType());
    	
    	when(service.update(newGuitarist, id)).thenReturn(updatedGuitarist);
    	
    	assertThat(new ResponseEntity<GuitaristDTO>(updatedGuitarist,HttpStatus.ACCEPTED))
    		.isEqualTo(this.controller.updateGuitaristById(this.id, newGuitarist));
    	
    	verify(this.service,times(1)).update(newGuitarist, this.id);
 
    }
    
    @Test
    void deleteTest() {
        this.controller.deleteGuitarist(id);

        verify(this.service, times(1))
            .delete(id);
    }
}
