package com.cydeo.entity;

import com.cydeo.entity.common.BaseEntity;
import com.cydeo.enums.ClientVendorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "clients_vendors")
public class ClientVendor extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private ClientVendorType clientVendorType;

    private String clientVendorName;
    private String phone;
    private String website;

    @OneToOne
    private Address address;

    @ManyToOne
    private Company company;

    public ClientVendor(Long id, LocalDateTime insertDateTime, Long insertUserId, LocalDateTime lastUpdateDateTime, Long lastUpdateUserId, Boolean isDeleted, ClientVendorType clientVendorType, String clientVendorName, String phone, String website, Address address, Company company) {
        super(id, insertDateTime, insertUserId, lastUpdateDateTime, lastUpdateUserId, isDeleted);
        this.clientVendorType = clientVendorType;
        this.clientVendorName = clientVendorName;
        this.phone = phone;
        this.website = website;
        this.address = address;
        this.company = company;
    }
}
