/*  _____ _______         _                      _
 * |_   _|__   __|       | |                    | |
 *   | |    | |_ __   ___| |___      _____  _ __| | __  ___ ____
 *   | |    | | '_ \ / _ \ __\ \ /\ / / _ \| '__| |/ / / __|_  /
 *  _| |_   | | | | |  __/ |_ \ V  V / (_) | |  |   < | (__ / /
 * |_____|  |_|_| |_|\___|\__| \_/\_/ \___/|_|  |_|\_(_)___/___|
 *                                _
 *              ___ ___ ___ _____|_|_ _ _____
 *             | . |  _| -_|     | | | |     |  LICENCE
 *             |  _|_| |___|_|_|_|_|___|_|_|_|
 *             |_|
 *
 *   PROGRAMOVÁNÍ  <>  DESIGN  <>  PRÁCE/PODNIKÁNÍ  <>  HW A SW
 *
 * Tento zdrojový kód je součástí výukových seriálů na
 * IT sociální síti WWW.ITNETWORK.CZ
 *
 * Kód spadá pod licenci prémiového obsahu a vznikl díky podpoře
 * našich členů. Je určen pouze pro osobní užití a nesmí být šířen.
 * Více informací na http://www.itnetwork.cz/licence
 */
package cz.itnetwork.entity;

import cz.itnetwork.constant.Countries;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "person")
@Getter
@Setter
public class PersonEntity {

    /**
     * Unique identifier for the person.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the person.
     */
    @Column(nullable = false)
    private String name;

    /**
     * The identification number of the person.
     */
    @Column(nullable = false)
    private String identificationNumber;

    /**
     * The tax number of the person.
     */
    private String taxNumber;

    /**
     * The account number of the person.
     */
    @Column(nullable = false)
    private String accountNumber;

    /**
     * The bank code associated with the person's account.
     */
    @Column(nullable = false)
    private String bankCode;

    /**
     * The IBAN (International Bank Account Number) of the person's account.
     */
    private String iban;

    /**
     * The telephone number of the person.
     */
    @Column(nullable = false)
    private String telephone;

    /**
     * The email address of the person.
     */
    @Column(nullable = false)
    private String mail;

    /**
     * The street address of the person.
     */
    @Column(nullable = false)
    private String street;

    /**
     * The ZIP (postal) code of the person's address.
     */
    @Column(nullable = false)
    private String zip;

    /**
     * The city of the person's address.
     */
    @Column(nullable = false)
    private String city;

    /**
     * The country of the person's address.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Countries country;

    /**
     * Any additional notes or comments related to the person.
     */
    private String note;

    /**
     * Indicates whether the person is hidden in the system or not.
     */
    private boolean hidden = false;

    /**
     * A list of invoices where the person is the buyer.
     * Lazily loaded to improve performance.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "buyer")
    List<InvoiceEntity> purchases = new ArrayList<>();

    /**
     * A list of invoices where the person is the seller.
     * Lazily loaded to improve performance.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "seller")
    List<InvoiceEntity> sales = new ArrayList<>();
}
