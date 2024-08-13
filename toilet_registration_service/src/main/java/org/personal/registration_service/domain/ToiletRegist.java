package org.personal.registration_service.domain;

import java.time.LocalDateTime;

import org.personal.registration_service.common.DateParsing;
import org.personal.registration_service.request.ToiletRegistRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "toilet_regist", schema = "public")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToiletRegist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "toilet_regist_id")
	private Long toiletRegistId;

	@Column(name = "toilet_regist_date", nullable = false, updatable = false)
	private String toiletRegistDate;

	@Column(name = "toilet_regist_is_approved", nullable = false)
	private Boolean toiletRegistIsApproved;

	@Column(name = "toilet_regist_confirmed_date")
	private String toiletRegistConfirmedDate;

	@Column(name = "toilet_regist_toilet_name")
	private String toiletRegistToiletName;

	@Column(name = "toilet_regist_road_name_address")
	private String toiletRegistRoadNameAddress;

	@Column(name = "toilet_regist_number_address")
	private String toiletRegistNumberAddress;

	@Column(name = "toilet_regist_latitude", nullable = false)
	private Double toiletRegistLatitude;

	@Column(name = "toilet_regist_longitude", nullable = false)
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

	// 정적 팩토리 메서드로 객체를 생성
	public static ToiletRegist of(ToiletRegistRequest request) {
		return ToiletRegist.builder()
			.toiletRegistLatitude(request.toiletRegistLatitude())
			.toiletRegistLongitude(request.toiletRegistLongitude())
			.toiletRegistToiletName(request.toiletRegistToiletName())
			.toiletRegistRoadNameAddress(request.toiletRegistRoadNameAddress())
			.toiletRegistNumberAddress(request.toiletRegistNumberAddress())
			.toiletRegistManagementAgency(request.toiletRegistManagementAgency())
			.toiletRegistPhoneNumber(request.toiletRegistPhoneNumber())
			.toiletRegistOpeningHours(request.toiletRegistOpeningHours())
			.toiletRegistOpeningHoursDetails(request.toiletRegistOpeningHoursDetails())
			.toiletRegistInstallationYearMonth(request.toiletRegistInstallationYearMonth())
			.toiletRegistOwnershipType(request.toiletRegistOwnershipType())
			.toiletRegistWasteDisposalMethod(request.toiletRegistWasteDisposalMethod())
			.toiletInfoSafetyFacilityInstallationIsRequired(request.toiletInfoSafetyFacilityInstallationIsRequired())
			.toiletRegistEmergencyBellIsInstalled(request.toiletRegistEmergencyBellIsInstalled())
			.toiletRegistEmergencyBellLocation(request.toiletRegistEmergencyBellLocation())
			.toiletRegistEntranceCctvIsInstalled(request.toiletRegistEntranceCctvIsInstalled())
			.toiletRegistDiaperChangingTableIsAvailable(request.toiletRegistDiaperChangingTableIsAvailable())
			.toiletRegistDiaperChangingTableLocation(request.toiletRegistDiaperChangingTableLocation())
			.toiletRegistMaleToiletsNumber(request.toiletRegistMaleToiletsNumber())
			.toiletRegistMaleUrinalsNumber(request.toiletRegistMaleUrinalsNumber())
			.toiletRegistMaleDisabledToiletsNumber(request.toiletRegistMaleDisabledToiletsNumber())
			.toiletRegistMaleDisabledUrinalsNumber(request.toiletRegistMaleDisabledUrinalsNumber())
			.toiletRegistMaleChildToiletsNumber(request.toiletRegistMaleChildToiletsNumber())
			.toiletRegistMaleChildUrinalsNumber(request.toiletRegistMaleChildUrinalsNumber())
			.toiletRegistFemaleToiletsNumber(request.toiletRegistFemaleToiletsNumber())
			.toiletRegistFemaleDisabledToiletsNumber(request.toiletRegistFemaleDisabledToiletsNumber())
			.toiletRegistFemaleChildToiletsNumber(request.toiletRegistFemaleChildToiletsNumber())
			.userEmail(request.userEmail())
			.toiletRegistIsApproved(false) // 기본값
			.build();
	}

	@PrePersist
	protected void onCreate() {
		if (toiletRegistDate == null) {
			toiletRegistDate = DateParsing.LdtToStr(LocalDateTime.now());
		}
		if (toiletRegistIsApproved == null) {
			toiletRegistIsApproved = false;
		}
	}

	// 승인 상태와 승인 날짜를 업데이트
	public void update(Boolean isApproved, LocalDateTime confirmedTime) {
		this.toiletRegistIsApproved = isApproved;
		this.toiletRegistConfirmedDate = DateParsing.LdtToStr(confirmedTime);
	}
}
