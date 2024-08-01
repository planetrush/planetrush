package com.planetrush.planetrush.planet.repository.custom;

import static com.planetrush.planetrush.verification.domain.QVerificationRecord.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.planetrush.planetrush.verification.domain.VerificationRecord;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class VerificationRecordRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	/**
	 * 인증 성공한 데이터를 불러옵니다.
	 * @param memberId 유저의 고유 id
	 * @param planetId 행성의 고유 id
	 * @return 인증 성공한 기록
	 */
	public List<VerificationRecord> findVerificationRecordsByMemberIdAndPlanetId(Long memberId, Long planetId) {
		return queryFactory.selectFrom(verificationRecord)
			.where(
				memberIdeq(memberId),
				planetIdeq(planetId),
				isVerified()
			)
			.fetch();
	}

	private BooleanExpression isVerified() {
		return verificationRecord.verified.isTrue();
	}

	private BooleanExpression planetIdeq(Long planetId) {
		return verificationRecord.planet.id.eq(planetId);
	}

	private BooleanExpression memberIdeq(Long memberId) {
		return verificationRecord.member.id.eq(memberId);
	}

}
