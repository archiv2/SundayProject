// util/DistanceUtil.java
package com.sunday.server.util;

import org.springframework.stereotype.Component;

@Component
public class DistanceUtil {

    private static final double EARTH_RADIUS_KM = 6371.0;

    /**
     * Haversine 공식으로 두 좌표 간 거리(km) 계산
     */
    public double calculateKm(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }

    /**
     * 도착 판별 - 반경 300m 이내이면 도착으로 처리
     */
    public boolean isArrived(double userLat, double userLon,
                             double placeLat, double placeLon) {
        double distanceKm = calculateKm(userLat, userLon, placeLat, placeLon);
        return distanceKm <= 0.3; // 300m
    }
}