package org.personal.info_service.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "toilet_info", schema = "public")
public class ToiletInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long toiletInfoId;

    private boolean isDeleted;
    private String toiletInfoManagementAgency;
    private String toiletInfoPhoneNumber;
    private String toiletInfoOpeningHours;
    private String toiletInfoOpeningHoursDetails;
    private String toiletInfoInstallationYearMonth;
    private String toiletInfoOwnershipType;
    private String toiletInfoWasteDisposalMethod;
    private boolean toiletInfoSafetyFacilityInstallationIsRequired;
    private boolean toiletInfoEmergencyBellIsInstalled;
    private String toiletInfoEmergencyBellLocation;
    private boolean toiletInfoEntranceCctvIsInstalled;
    private boolean toiletInfoDiaperChangingTableIsAvailable;
    private String toiletInfoDiaperChangingTableLocation;
    private int toiletInfoMaleToiletsNumber;
    private int toiletInfoMaleUrinalsNumber;
    private int toiletInfoMaleDisabledToiletsNumber;
    private int toiletInfoMaleDisabledUrinalsNumber;
    private int toiletInfoMaleChildToiletsNumber;
    private int toiletInfoMaleChildUrinalsNumber;
    private int toiletInfoFemaleToiletsNumber;
    private int toiletInfoFemaleDisabledToiletsNumber;
    private int toiletInfoFemaleChildToiletsNumber;

    private Long toiletLocationId;
}
