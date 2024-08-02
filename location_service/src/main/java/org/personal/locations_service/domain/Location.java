package org.personal.locations_service.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name="location", schema = "public")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "road_address", nullable = false)
    private String roadAddress;

    @Column(name = "jibun_address", nullable = false)
    private String jibunAddress;

    private Float latitude;          // 위도

    private Float longitude;        // 경도

    private boolean isDeleted = false;

    @Builder
    public Location(String name, String roadAddress, String jibunAddress, Float latitude, Float longitude, boolean isDeleted) {
        this.name = name;
        this.roadAddress = roadAddress;
        this.jibunAddress = jibunAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isDeleted = isDeleted;
    }
}
