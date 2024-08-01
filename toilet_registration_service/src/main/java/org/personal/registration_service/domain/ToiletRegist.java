package org.personal.registration_service.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "toilet_regist", schema = "public")
public class ToiletRegist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "toilet_regist_id")
	private Long toiletRegistId;

	@Column(name = "toilet_regist_date")
	private String toiletRegistDate;

	@Column(name = "toilet_regist_img")
	private String toiletRegistImg;

	@Column(name = "toilet_regist_is_approved")
	private Boolean toiletRegistIsApproved;

	@Column(name = "toilet_regist_confirmed_date")
	private String toiletRegistConfirmedDate;

	@Column(name = "toilet_regist_toilet_name")
	private String toiletRegistToiletName;

	@Column(name = "toilet_regist_road_name_address")
	private String toiletRegistRoadNameAddress;

	@Column(name = "toilet_regist_number_address")
	private String toiletRegistNumberAddress;

	@Column(name = "toilet_regist_latitude")
	private Double toiletRegistLatitude;

	@Column(name = "toilet_regist_longitude")
	private Double toiletRegistLongitude;

	@Column(name = "toilet_regist_management_agency")
	private String toiletRegistManagementAgency;

	@Column(name = "toilet_regist_phone_number")
	private String toiletRegistPhoneNumber;

	@Column(name = "toilet_regist_opening_hours")
	private String toiletRegistOpeningHours;

	@Column(name = "toilet_regist_opening_hours_details")
	private String toiletRegistOpeningHoursDetails;

	@Column(name = "toilet_regist_installation_year_month")
	private String toiletRegistInstallationYearMonth;

	@Column(name = "toilet_regist_ownership_type")
	private String toiletRegistOwnershipType;

	@Column(name = "toilet_regist_waste_disposal_method")
	private String toiletRegistWasteDisposalMethod;

	@Column(name = "toilet_info_safety_facility_installation_is_required")
	private Boolean toiletInfoSafetyFacilityInstallationIsRequired;

	@Column(name = "toilet_regist_emergency_bell_is_installed")
	private Boolean toiletRegistEmergencyBellIsInstalled;

	@Column(name = "toilet_regist_emergency_bell_location")
	private String toiletRegistEmergencyBellLocation;

	@Column(name = "toilet_regist_entrance_cctv_is_installed")
	private Boolean toiletRegistEntranceCctvIsInstalled;

	@Column(name = "toilet_regist_diaper_changing_table_is_available")
	private Boolean toiletRegistDiaperChangingTableIsAvailable;

	@Column(name = "toilet_regist_diaper_changing_table_location")
	private String toiletRegistDiaperChangingTableLocation;

	@Column(name = "toilet_regist_male_toilets_number")
	private Integer toiletRegistMaleToiletsNumber;

	@Column(name = "toilet_regist_male_urinals_number")
	private Integer toiletRegistMaleUrinalsNumber;

	@Column(name = "toilet_regist_male_disabled_toilets_number")
	private Integer toiletRegistMaleDisabledToiletsNumber;

	@Column(name = "toilet_regist_male_disabled_urinals_number")
	private Integer toiletRegistMaleDisabledUrinalsNumber;

	@Column(name = "toilet_regist_male_child_toilets_number")
	private Integer toiletRegistMaleChildToiletsNumber;

	@Column(name = "toilet_regist_male_child_urinals_number")
	private Integer toiletRegistMaleChildUrinalsNumber;

	@Column(name = "toilet_regist_female_toilets_number")
	private Integer toiletRegistFemaleToiletsNumber;

	@Column(name = "toilet_regist_female_disabled_toilets_number")
	private Integer toiletRegistFemaleDisabledToiletsNumber;

	@Column(name = "toilet_regist_female_child_toilets_number")
	private Integer toiletRegistFemaleChildToiletsNumber;

	@Column(name = "user_email")
	private String userEmail;
}