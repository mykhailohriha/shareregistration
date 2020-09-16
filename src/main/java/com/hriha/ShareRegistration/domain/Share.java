package com.hriha.ShareRegistration.domain;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table
public class Share {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String comment;

    @Column
    private Long EDRPOU;

    @Column
    private Long quantity;

    @Column
    private String status;

    @Column
    private LocalDateTime creationDate;

    @Column
    private BigDecimal value;

    @Column
    private BigDecimal totalValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue() {
        if (this.getValue() != null && this.getQuantity() != null) {
            this.totalValue = this.getValue().multiply(BigDecimal.valueOf(this.getQuantity()));
        }
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getEDRPOU() {
        return EDRPOU;
    }

    public void setEDRPOU(Long EDRPOU) {
        this.EDRPOU = EDRPOU;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Share share = (Share) o;
        return id.equals(share.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Share{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
