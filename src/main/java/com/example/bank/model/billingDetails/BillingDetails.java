package com.example.bank.model.billingDetails;


import com.example.bank.enums.BillingType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
//Поддерживает наследование в builder-паттерне.
//Работает с абстрактными классами
@SuperBuilder
@Entity
@Table(name = "billing_details")
// для отображения с одной таблицей для целой иерархии
// классов используем стратегию наследования SINGLE_TABLE
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// Нужен столбец например BD_TYPE для пометки,
// creditcard или bankAccount
@DiscriminatorColumn(name = "bd_type")
@AllArgsConstructor
@NoArgsConstructor
public abstract class BillingDetails {

    @Id
    // для SINGLE_TABLE другая стратегия генерации ключа
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    /// Держатель счета
    @Column(name = "holder_billing")
    private String holderBilling;


    @Column(name = "bd_type")
    private BillingType billingType;
}
