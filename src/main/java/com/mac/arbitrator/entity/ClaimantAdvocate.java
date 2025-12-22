package com.mac.arbitrator.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "claimant_advocate")
public class ClaimantAdvocate {
    @EmbeddedId
    private ClaimantAdvocateEmbeddable id;
}
