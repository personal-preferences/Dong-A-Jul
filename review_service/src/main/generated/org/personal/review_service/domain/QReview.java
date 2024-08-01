package org.personal.review_service.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = -458362842L;

    public static final QReview review = new QReview("review");

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    public final StringPath reviewContent = createString("reviewContent");

    public final NumberPath<Long> reviewId = createNumber("reviewId", Long.class);

    public final BooleanPath reviewIsDeleted = createBoolean("reviewIsDeleted");

    public final StringPath reviewRegisteredDate = createString("reviewRegisteredDate");

    public final NumberPath<Long> reviewScore = createNumber("reviewScore", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QReview(String variable) {
        super(Review.class, forVariable(variable));
    }

    public QReview(Path<? extends Review> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReview(PathMetadata metadata) {
        super(Review.class, metadata);
    }

}

