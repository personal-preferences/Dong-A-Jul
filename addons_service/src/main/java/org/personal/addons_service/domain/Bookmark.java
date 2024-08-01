package org.personal.addons_service.domain;

public record Bookmark (int memoId,
						String memoContent,
						String userEmail,
						int toiletLocationId) {}
