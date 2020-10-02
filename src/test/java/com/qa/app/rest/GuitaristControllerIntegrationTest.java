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
import com.qa.app.DTO.GuitaristDTO;
import com.qa.app.persistence.domain.Guitarist;
import com.qa.app.persistence.repo.GuitaristRepo;

@SpringBootTest
@AutoConfigureMockMvc
public class GuitaristControllerIntegrationTest {
	
	@Autowired
	private MockMvc mock;
	
	@Autowired
	private GuitaristRepo repo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Guitarist testGuistarist;
	private Guitarist testGuitaristWithId;
	private GuitaristDTO guitaristDTO;
	
	private Long id;
	
    private GuitaristDTO mapToDTO(Guitarist guitarist) {
        return this.modelMapper.map(guitarist, GuitaristDTO.class);
    }
    
    @BeforeEach
    void init() {
    	this.repo.deleteAll();
    	
    	this.testGuistarist = new Guitarist("Liam",10,"Electric");
    	this.testGuitaristWithId = this.repo.save(testGuistarist);
    	this.guitaristDTO = this.mapToDTO(testGuitaristWithId);
    	
    	this.id = this.testGuitaristWithId.getId();
    }
    
    @Test
    void testCreate() throws Exception{
        this.mock
        .perform(request(HttpMethod.POST, "/guitarist/create").contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(testGuistarist))
                .accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isCreated())
    			.andExpect(content().json(this.objectMapper.writeValueAsString(guitaristDTO)));
    }
    
    @Test
    void testRead() throws Exception{
    	this.mock
    			.perform(request(HttpMethod.GET,"/guitarist/read/"+this.id).accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk())
    			.andExpect(content().json(this.objectMapper.writeValueAsString(guitaristDTO)));
    }
    
    @Test
    void testReadAll() throws Exception {
    	List<GuitaristDTO> guitaristList = new ArrayList<>();
    	guitaristList.add(this.guitaristDTO);
    	
    	String content = this.mock
    			.perform(request(HttpMethod.GET,"/guitarist/read").accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    	
    	assertEquals(this.objectMapper.writeValueAsString(guitaristList),content);
    }
    
    @Test
    void testUpdate() throws Exception {
        GuitaristDTO newGuitarist = new GuitaristDTO(null, "Harry", 4, "Bass");
        Guitarist updatedGuitarist = new Guitarist(newGuitarist.getName(), newGuitarist.getNoOfStrings(),newGuitarist.getType());
        updatedGuitarist.setId(this.id);

        String result = this.mock
                .perform(request(HttpMethod.PUT, "/guitarist/update/" + this.id).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(newGuitarist)))
                .andExpect(status().isAccepted()).andReturn().getResponse().getContentAsString();

        assertEquals(this.objectMapper.writeValueAsString(this.mapToDTO(updatedGuitarist)), result);
    }
    
    @Test
    void testDelete() throws Exception {
        this.mock.perform(request(HttpMethod.DELETE, "/guitarist/delete/" + this.id)).andExpect(status().isNoContent());
    }
	

}
