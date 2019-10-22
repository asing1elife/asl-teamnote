package com.asing1elife.teamnote.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name="al_reimburse_invoice")
public class ReimburseInvoiceModel extends BaseModel {

    @ManyToOne
    private ReimburseModel reimburse;

    @Column
    private String number;

    @Column
    private Double amount = 0D;

}
