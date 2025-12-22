package com.mac.arbitrator.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class RespondantAdvocateEmbeddable {

    private Long caseId;
    private Long respondentIds;
    private Long advocateId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RespondantAdvocateEmbeddable that = (RespondantAdvocateEmbeddable) o;
        return Objects.equals(caseId, that.caseId) &&
                Objects.equals(respondentIds, that.respondentIds) &&
                Objects.equals(advocateId, that.advocateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(caseId, respondentIds, advocateId);
    }
}
