package org.personal.locations_service.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String roadAddress;

    private String jibunAddress;

    private Float latitude;          // 위도

    private Float longitude;        // 경도

    @Builder
    public Location(String name, String roadAddress, String jibunAddress, Float latitude, Float longitude) {
        this.name = name;
        this.roadAddress = roadAddress;
        this.jibunAddress = jibunAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
