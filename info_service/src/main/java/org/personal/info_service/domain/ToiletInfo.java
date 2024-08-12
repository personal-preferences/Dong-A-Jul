package org.personal.info_service.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
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

    public ToiletInfo() {
    }

    @Builder
    public ToiletInfo(Long toiletInfoId, boolean isDeleted, String toiletInfoManagementAgency, String toiletInfoPhoneNumber,
                      String toiletInfoOpeningHours, String toiletInfoOpeningHoursDetails,
                      String toiletInfoInstallationYearMonth, String toiletInfoOwnershipType,
                      String toiletInfoWasteDisposalMethod, boolean toiletInfoSafetyFacilityInstallationIsRequired,
                      boolean toiletInfoEmergencyBellIsInstalled, String toiletInfoEmergencyBellLocation,
                      boolean toiletInfoEntranceCctvIsInstalled, boolean toiletInfoDiaperChangingTableIsAvailable,
                      String toiletInfoDiaperChangingTableLocation, int toiletInfoMaleToiletsNumber,
                      int toiletInfoMaleUrinalsNumber, int toiletInfoMaleDisabledToiletsNumber,
                      int toiletInfoMaleDisabledUrinalsNumber, int toiletInfoMaleChildToiletsNumber,
                      int toiletInfoMaleChildUrinalsNumber, int toiletInfoFemaleToiletsNumber,
                      int toiletInfoFemaleDisabledToiletsNumber, int toiletInfoFemaleChildToiletsNumber,
                      Long toiletLocationId) {
        this.toiletInfoId = toiletInfoId;
        this.isDeleted = isDeleted;
        this.toiletInfoManagementAgency = toiletInfoManagementAgency;
        this.toiletInfoPhoneNumber = toiletInfoPhoneNumber;
        this.toiletInfoOpeningHours = toiletInfoOpeningHours;
        this.toiletInfoOpeningHoursDetails = toiletInfoOpeningHoursDetails;
        this.toiletInfoInstallationYearMonth = toiletInfoInstallationYearMonth;
        this.toiletInfoOwnershipType = toiletInfoOwnershipType;
        this.toiletInfoWasteDisposalMethod = toiletInfoWasteDisposalMethod;
        this.toiletInfoSafetyFacilityInstallationIsRequired = toiletInfoSafetyFacilityInstallationIsRequired;
        this.toiletInfoEmergencyBellIsInstalled = toiletInfoEmergencyBellIsInstalled;
        this.toiletInfoEmergencyBellLocation = toiletInfoEmergencyBellLocation;
        this.toiletInfoEntranceCctvIsInstalled = toiletInfoEntranceCctvIsInstalled;
        this.toiletInfoDiaperChangingTableIsAvailable = toiletInfoDiaperChangingTableIsAvailable;
        this.toiletInfoDiaperChangingTableLocation = toiletInfoDiaperChangingTableLocation;
        this.toiletInfoMaleToiletsNumber = toiletInfoMaleToiletsNumber;
        this.toiletInfoMaleUrinalsNumber = toiletInfoMaleUrinalsNumber;
        this.toiletInfoMaleDisabledToiletsNumber = toiletInfoMaleDisabledToiletsNumber;
        this.toiletInfoMaleDisabledUrinalsNumber = toiletInfoMaleDisabledUrinalsNumber;
        this.toiletInfoMaleChildToiletsNumber = toiletInfoMaleChildToiletsNumber;
        this.toiletInfoMaleChildUrinalsNumber = toiletInfoMaleChildUrinalsNumber;
        this.toiletInfoFemaleToiletsNumber = toiletInfoFemaleToiletsNumber;
        this.toiletInfoFemaleDisabledToiletsNumber = toiletInfoFemaleDisabledToiletsNumber;
        this.toiletInfoFemaleChildToiletsNumber = toiletInfoFemaleChildToiletsNumber;
        this.toiletLocationId = toiletLocationId;
    }

    public void updateInfoId(Long toiletInfoId){
        this.toiletInfoId = toiletInfoId;
    }

    public void updateDeleted(Boolean isDeleted){
        this.isDeleted = isDeleted;
    }
}
