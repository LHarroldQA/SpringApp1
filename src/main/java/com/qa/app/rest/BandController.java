package com.qa.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.app.DTO.BandDTO;
import com.qa.app.persistence.domain.Band;
import com.qa.app.service.BandService;


@RestController
@CrossOrigin
@RequestMapping("/band")
public class BandController {
	
	private BandService service;

	@Autowired
	public BandController(BandService service) {
		super();
		this.service = service;
	}
	
	//create
	@PostMapping("/create")
	public ResponseEntity<BandDTO> create(@RequestBody Band band){
//		BandDTO created = this.service.create(band);
		return new ResponseEntity<>(this.service.create(band),HttpStatus.CREATED);
	}
	
	//read all
	@GetMapping("/read")
	public ResponseEntity<List<BandDTO>> getAllBands(){
		return ResponseEntity.ok(this.service.readAll());
	}
	
	//read one
	@GetMapping("/read/{id}")
	public ResponseEntity<BandDTO> getBandById(@PathVariable Long id){
		return ResponseEntity.ok(this.service.readOne(id));
	}
	
	//update
	@PutMapping("/update/{id}")
	ResponseEntity<BandDTO> updateBandById(@PathVariable Long id,@RequestBody BandDTO bandDTO){
//		BandDTO updated = this.service.update(bandDTO, id);
		return new ResponseEntity<>(this.service.update(bandDTO, id),HttpStatus.ACCEPTED);
	}
	
	//delete
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<BandDTO> deleteGuitarist(@PathVariable Long id){
		return this.service.delete(id)? new ResponseEntity<>(HttpStatus.NO_CONTENT): new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
