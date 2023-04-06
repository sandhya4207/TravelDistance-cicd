package com.distance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;

import com.distance.controller.DistanceController;
import com.distance.model.Data;
import com.distance.model.ResourceSets;
import com.distance.model.Resources;
@Slf4j
@Service
public class DistanceServiceImpl implements DistanceService{

	
@Autowired
 private RestTemplate restTemplate;



	public  double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		if ((lat1 == lat2) && (lon1 == lon2)) {
			return 0;
		}
		else {
			double theta = lon1 - lon2;
			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			if (unit.equals("K")) {
				dist = dist * 1.609344;
			} else if (unit.equals("N")) {
				dist = dist * 0.8684;
			}
			return (dist);
		}
	}
	
	@Value("${apiKey}")
	String apiKey;
	public Double getDistance(String city1,String city2) {
		StringBuilder b=new StringBuilder("http://dev.virtualearth.net/REST/V1/Routes/Driving?wp.0=");
		b.append(city1);
		b.append("wa&wp.1=");
		b.append(city2);
		b.append("wa&key=");
		b.append(apiKey);
		String s=new String(b);
		String url="http://dev.virtualearth.net/REST/V1/Routes/Driving?wp.0=Delhi wa&wp.1=Nagpur wa&key=AnthbzFgrqS4AcG5bJPDHfAoZwvLcDn_y2flkn8i14MwimW5K-yZvMqqy-x1X0z9";
		Data result = restTemplate.getForObject(s, Data.class);
		List<ResourceSets> l = result.getResourceSets();
		ResourceSets rr = l.get(0);
		Resources r = rr.getResources().get(0);
		System.out.println(r.getTravelDistance());
		return r.getTravelDistance();
	}
}
