package com.qa.app.rest;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.app.DTO.BandDTO;
import com.qa.app.persistence.domain.Band;
import com.qa.app.persistence.repo.BandRepo;


@SpringBootTest
@AutoConfigureMockMvc
public class BandControllerIntegrationTest {
	
	@Autowired
	private MockMvc mock;
	
	@Autowired
	private BandRepo repo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Band testBand;
	private Band testBandWithId;
	private BandDTO bandDTO;
	
	private Long id;
	
    private BandDTO mapToDTO(Band band) {
        return this.modelMapper.map(band, BandDTO.class);
    }
    
    @BeforeEach
    void init() {
    	this.repo.deleteAll();
    	
    	this.testBand = new Band("Big band");
    	this.testBandWithId = this.repo.save(testBand);
    	this.bandDTO = this.mapToDTO(testBandWithId);
    	
    	this.id = this.testBandWithId.getId();
    }
    
    @Test
    void testCreate() throws Exception{
        this.mock
        .perform(request(HttpMethod.POST, "/band/create").contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(testBand))
                .accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isCreated())
    			.andExpect(content().json(this.objectMapper.writeValueAsString(bandDTO)));
    }
	
    @Test
    void testRead() throws Exception{
    	this.mock
    			.perform(request(HttpMethod.GET,"/band/read/"+this.id).accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk())
    			.andExpect(content().json(this.objectMapper.writeValueAsString(bandDTO)));
    }
    
    @Test
    void testReadAll() throws Exception {
    	List<BandDTO> bandList = new ArrayList<>();
    	bandList.add(this.bandDTO);
    	
    	String content = this.mock
    			.perform(request(HttpMethod.GET,"/band/read").accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    	
    	assertEquals(this.objectMapper.writeValueAsString(bandList),content);
    }
    
    @Test
    void testUpdate() throws Exception {
        BandDTO newBand= new BandDTO(null, "Not so big band", null);
        Band updatedBand = new Band(newBand.getName());
        updatedBand.setId(this.id);

        String result = this.mock
                .perform(request(HttpMethod.PUT, "/band/update/" + this.id).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(newBand)))
                .andExpect(status().isAccepted()).andReturn().getResponse().getContentAsString();

        assertEquals(this.objectMapper.writeValueAsString(this.mapToDTO(updatedBand)), result);
    }
    
    
    @Test
    void testDelete() throws Exception {
        this.mock.perform(request(HttpMethod.DELETE, "/band/delete/" + this.id)).andExpect(status().isNoContent());
    }

}
