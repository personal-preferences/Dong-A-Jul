package org.personal.addons_service.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addon", schema = "public")
@Builder
public class Addon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long addonId;

	@Column(name = "memo_content", nullable = true)
	private String memoContent;

	@Column(name = "is_bookmarked")
	private boolean isBookmarked;

	@Column(name = "user_email", nullable = false)
	private	String userEmail;

	@Column(name = "toilet_location_id", nullable = false)
	private Long toiletLocationId;
}
