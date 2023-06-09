package ru.nexign.jpa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="clients")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Long id;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "balance", nullable = false, scale = 2)
    private BigDecimal balance;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tariff_id", nullable = false)
    private TariffEntity tariff;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
    private List<ReportEntity> reports;

    public ClientEntity(String phoneNumber, BigDecimal balance, TariffEntity tariff, List<ReportEntity> reports) {
        this.phoneNumber = phoneNumber;
        this.balance = balance;
        this.tariff = tariff;
        this.reports = reports;
    }
}
