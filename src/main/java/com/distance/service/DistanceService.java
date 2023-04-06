package com.distance.service;

import org.springframework.stereotype.Service;


public interface DistanceService {
	public  double distance(double lat1, double lon1, double lat2, double lon2, String unit); 
	public Double getDistance(String city1,String city2);

}
