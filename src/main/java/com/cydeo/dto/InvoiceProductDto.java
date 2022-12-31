package com.cydeo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceProductDto {

    private Long id;
    @NotNull(message ="Quantity is a required field." )
    @Range(min = 1, max = 100, message = "Maximum order count is 100")
    private Integer quantity;
    @NotNull(message ="Price is a required field.")
    @Min(value=1, message ="Price should be at least $1")
    private BigDecimal price;
    @NotNull(message ="Tax is a required field.")
    @Range(min = 5, max = 20, message ="Tax should be between 5% and 20%")
    private Integer tax;
    private BigDecimal total;
    private BigDecimal profitLoss;
    private Integer remainingQty;
    private InvoiceDto invoice;
    @NotNull(message ="Product is a required field." )
    private ProductDto product;

}
