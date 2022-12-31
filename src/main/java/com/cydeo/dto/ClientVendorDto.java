package com.cydeo.dto;

import com.cydeo.enums.ClientVendorType;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientVendorDto {

    private Long id;

    @Valid
    @NotNull(message = "ClientVendor name can not be blank.")
    @Column(unique = true)
    @Size(max = 50, min = 2, message = "ClientVendor Name must be between 2 and 50 characters long.")
    private String clientVendorName;

    @NotNull(message = "phone number cannot be blank.")
    @Pattern(regexp = "^\\d{10}$")
    // ^ asserts position at start of a line-d matches a digit (equivalent to [0-9]){10} matches the previous token exactly 10 times$ asserts position at the end of a line
    private String phone;

    @Pattern(regexp = "^http(s{0,1})://[a-zA-Z0-9/\\-\\.]+.([A-Za-z/] {2,5})[a-zA-Z0-9/\\&\\?\\=\\-\\.\\~\\%]*")
    private String website;

    @Valid
    @NotNull(message = "Please select type.")
    private ClientVendorType clientVendorType;

    @Valid
    private AddressDto address;
    private CompanyDto company;

}
