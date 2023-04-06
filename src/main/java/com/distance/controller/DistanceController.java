package com.distance.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.distance.model.Distance;
import com.distance.service.DistanceService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/map")
@CrossOrigin("*")
@Slf4j
public class DistanceController {

	@Autowired(required = true)
	private DistanceService distanceServ;

	@GetMapping("/distance")
	public ResponseEntity<Map<String, Double>> getDistance(@RequestBody Distance distance) {
		double finalDistance = distanceServ.distance(distance.getLatitude1(), distance.getLongitude1(),
				distance.getLatitude2(), distance.getLongitude2(), "K");
		return new ResponseEntity<Map<String, Double>>(Map.of("Distance", finalDistance), HttpStatus.OK);
	}

	@GetMapping("/travelDistance")
	public ResponseEntity getTravelDistance(@RequestParam String city1, @RequestParam String city2) {
		try {
			if (city1.equalsIgnoreCase(city2))
				return new ResponseEntity<>(Map.of("response", "Please Select Different Cities"),
						HttpStatus.BAD_REQUEST);
			log.info("city1", "city2{}", city1, city2);
			log.info("Api Triggered finding the distance{}");
			Double s = distanceServ.getDistance(city1, city2);
			return new ResponseEntity<>(Map.of("Distance", s), HttpStatus.OK);
		} catch (Exception e) {
			log.info(e.getMessage());
			return new ResponseEntity<>(Map.of("Exception", "Something Went Wrong"), HttpStatus.BAD_REQUEST);
		}
	}

}
