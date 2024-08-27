package org.personal.registration_service.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QToiletRegist is a Querydsl query type for ToiletRegist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QToiletRegist extends EntityPathBase<ToiletRegist> {

    private static final long serialVersionUID = 1375051026L;

    public static final QToiletRegist toiletRegist = new QToiletRegist("toiletRegist");

    public final BooleanPath toiletInfoSafetyFacilityInstallationIsRequired = createBoolean("toiletInfoSafetyFacilityInstallationIsRequired");

    public final StringPath toiletRegistConfirmedDate = createString("toiletRegistConfirmedDate");

    public final StringPath toiletRegistDate = createString("toiletRegistDate");

    public final BooleanPath toiletRegistDiaperChangingTableIsAvailable = createBoolean("toiletRegistDiaperChangingTableIsAvailable");

    public final StringPath toiletRegistDiaperChangingTableLocation = createString("toiletRegistDiaperChangingTableLocation");

    public final BooleanPath toiletRegistEmergencyBellIsInstalled = createBoolean("toiletRegistEmergencyBellIsInstalled");

    public final StringPath toiletRegistEmergencyBellLocation = createString("toiletRegistEmergencyBellLocation");

    public final BooleanPath toiletRegistEntranceCctvIsInstalled = createBoolean("toiletRegistEntranceCctvIsInstalled");

    public final NumberPath<Integer> toiletRegistFemaleChildToiletsNumber = createNumber("toiletRegistFemaleChildToiletsNumber", Integer.class);

    public final NumberPath<Integer> toiletRegistFemaleDisabledToiletsNumber = createNumber("toiletRegistFemaleDisabledToiletsNumber", Integer.class);

    public final NumberPath<Integer> toiletRegistFemaleToiletsNumber = createNumber("toiletRegistFemaleToiletsNumber", Integer.class);

    public final NumberPath<Long> toiletRegistId = createNumber("toiletRegistId", Long.class);

    public final StringPath toiletRegistInstallationYearMonth = createString("toiletRegistInstallationYearMonth");

    public final BooleanPath toiletRegistIsApproved = createBoolean("toiletRegistIsApproved");

    public final NumberPath<Double> toiletRegistLatitude = createNumber("toiletRegistLatitude", Double.class);

    public final NumberPath<Double> toiletRegistLongitude = createNumber("toiletRegistLongitude", Double.class);

    public final NumberPath<Integer> toiletRegistMaleChildToiletsNumber = createNumber("toiletRegistMaleChildToiletsNumber", Integer.class);

    public final NumberPath<Integer> toiletRegistMaleChildUrinalsNumber = createNumber("toiletRegistMaleChildUrinalsNumber", Integer.class);

    public final NumberPath<Integer> toiletRegistMaleDisabledToiletsNumber = createNumber("toiletRegistMaleDisabledToiletsNumber", Integer.class);

    public final NumberPath<Integer> toiletRegistMaleDisabledUrinalsNumber = createNumber("toiletRegistMaleDisabledUrinalsNumber", Integer.class);

    public final NumberPath<Integer> toiletRegistMaleToiletsNumber = createNumber("toiletRegistMaleToiletsNumber", Integer.class);

    public final NumberPath<Integer> toiletRegistMaleUrinalsNumber = createNumber("toiletRegistMaleUrinalsNumber", Integer.class);

    public final StringPath toiletRegistManagementAgency = createString("toiletRegistManagementAgency");

    public final StringPath toiletRegistNumberAddress = createString("toiletRegistNumberAddress");

    public final StringPath toiletRegistOpeningHours = createString("toiletRegistOpeningHours");

    public final StringPath toiletRegistOpeningHoursDetails = createString("toiletRegistOpeningHoursDetails");

    public final StringPath toiletRegistOwnershipType = createString("toiletRegistOwnershipType");

    public final StringPath toiletRegistPhoneNumber = createString("toiletRegistPhoneNumber");

    public final StringPath toiletRegistRoadNameAddress = createString("toiletRegistRoadNameAddress");

    public final StringPath toiletRegistToiletName = createString("toiletRegistToiletName");

    public final StringPath toiletRegistWasteDisposalMethod = createString("toiletRegistWasteDisposalMethod");

    public final StringPath userEmail = createString("userEmail");

    public QToiletRegist(String variable) {
        super(ToiletRegist.class, forVariable(variable));
    }

    public QToiletRegist(Path<? extends ToiletRegist> path) {
        super(path.getType(), path.getMetadata());
    }

    public QToiletRegist(PathMetadata metadata) {
        super(ToiletRegist.class, metadata);
    }

}

