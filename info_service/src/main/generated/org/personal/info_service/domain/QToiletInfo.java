package org.personal.info_service.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QToiletInfo is a Querydsl query type for ToiletInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QToiletInfo extends EntityPathBase<ToiletInfo> {

    private static final long serialVersionUID = -1386546945L;

    public static final QToiletInfo toiletInfo = new QToiletInfo("toiletInfo");

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final BooleanPath toiletInfoDiaperChangingTableIsAvailable = createBoolean("toiletInfoDiaperChangingTableIsAvailable");

    public final StringPath toiletInfoDiaperChangingTableLocation = createString("toiletInfoDiaperChangingTableLocation");

    public final BooleanPath toiletInfoEmergencyBellIsInstalled = createBoolean("toiletInfoEmergencyBellIsInstalled");

    public final StringPath toiletInfoEmergencyBellLocation = createString("toiletInfoEmergencyBellLocation");

    public final BooleanPath toiletInfoEntranceCctvIsInstalled = createBoolean("toiletInfoEntranceCctvIsInstalled");

    public final NumberPath<Integer> toiletInfoFemaleChildToiletsNumber = createNumber("toiletInfoFemaleChildToiletsNumber", Integer.class);

    public final NumberPath<Integer> toiletInfoFemaleDisabledToiletsNumber = createNumber("toiletInfoFemaleDisabledToiletsNumber", Integer.class);

    public final NumberPath<Integer> toiletInfoFemaleToiletsNumber = createNumber("toiletInfoFemaleToiletsNumber", Integer.class);

    public final NumberPath<Long> toiletInfoId = createNumber("toiletInfoId", Long.class);

    public final StringPath toiletInfoInstallationYearMonth = createString("toiletInfoInstallationYearMonth");

    public final NumberPath<Integer> toiletInfoMaleChildToiletsNumber = createNumber("toiletInfoMaleChildToiletsNumber", Integer.class);

    public final NumberPath<Integer> toiletInfoMaleChildUrinalsNumber = createNumber("toiletInfoMaleChildUrinalsNumber", Integer.class);

    public final NumberPath<Integer> toiletInfoMaleDisabledToiletsNumber = createNumber("toiletInfoMaleDisabledToiletsNumber", Integer.class);

    public final NumberPath<Integer> toiletInfoMaleDisabledUrinalsNumber = createNumber("toiletInfoMaleDisabledUrinalsNumber", Integer.class);

    public final NumberPath<Integer> toiletInfoMaleToiletsNumber = createNumber("toiletInfoMaleToiletsNumber", Integer.class);

    public final NumberPath<Integer> toiletInfoMaleUrinalsNumber = createNumber("toiletInfoMaleUrinalsNumber", Integer.class);

    public final StringPath toiletInfoManagementAgency = createString("toiletInfoManagementAgency");

    public final StringPath toiletInfoOpeningHours = createString("toiletInfoOpeningHours");

    public final StringPath toiletInfoOpeningHoursDetails = createString("toiletInfoOpeningHoursDetails");

    public final StringPath toiletInfoOwnershipType = createString("toiletInfoOwnershipType");

    public final StringPath toiletInfoPhoneNumber = createString("toiletInfoPhoneNumber");

    public final BooleanPath toiletInfoSafetyFacilityInstallationIsRequired = createBoolean("toiletInfoSafetyFacilityInstallationIsRequired");

    public final StringPath toiletInfoWasteDisposalMethod = createString("toiletInfoWasteDisposalMethod");

    public final NumberPath<Long> toiletLocationId = createNumber("toiletLocationId", Long.class);

    public QToiletInfo(String variable) {
        super(ToiletInfo.class, forVariable(variable));
    }

    public QToiletInfo(Path<? extends ToiletInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QToiletInfo(PathMetadata metadata) {
        super(ToiletInfo.class, metadata);
    }

}

