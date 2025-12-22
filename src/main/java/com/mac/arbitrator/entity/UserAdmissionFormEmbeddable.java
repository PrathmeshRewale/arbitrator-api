package com.mac.arbitrator.entity;

import com.mac.arbitrator.entity.enums.UserCaseType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class UserAdmissionFormEmbeddable {
    private Long userid;
    private Long admissionFormId;
    @Enumerated(EnumType.STRING)
    private UserCaseType userCaseType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserAdmissionFormEmbeddable entity = (UserAdmissionFormEmbeddable) o;
        return Objects.equals(this.userid, entity.userid) &&
                Objects.equals(this.admissionFormId, entity.admissionFormId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, admissionFormId);
    }

}
