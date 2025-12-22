package com.mac.arbitrator.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClaimantAdvocateEmbeddable {

    private Long caseId;
    private Long claimantIds;
    private Long advocateId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ClaimantAdvocateEmbeddable that = (ClaimantAdvocateEmbeddable) o;
        return Objects.equals(caseId, that.caseId) &&
                Objects.equals(claimantIds, that.claimantIds) &&
                Objects.equals(advocateId, that.advocateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(caseId, claimantIds, advocateId);
    }
}
