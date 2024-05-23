package cz.itnetwork.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.itnetwork.constant.Countries;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    /**
     * The unique identifier of the person.
     */
    @JsonProperty("_id")
    private Long id;

    /**
     * The name of the person.
     */
    private String name;

    /**
     * The identification number of the person.
     */
    private String identificationNumber;

    /**
     * The tax number of the person.
     */
    private String taxNumber;

    /**
     * The bank account number of the person.
     */
    private String accountNumber;

    /**
     * The bank code associated with the person's account.
     */
    private String bankCode;

    /**
     * The International Bank Account Number (IBAN) of the person.
     */
    private String iban;

    /**
     * The telephone number of the person.
     */
    private String telephone;

    /**
     * The email address of the person.
     */
    private String mail;

    /**
     * The street address of the person.
     */
    private String street;

    /**
     * The ZIP (postal) code of the person's address.
     */
    private String zip;

    /**
     * The city of the person's address.
     */
    private String city;

    /**
     * The country of the person's address, represented as an enum of type `Countries`.
     */
    private Countries country;

    /**
     * Any additional notes or comments about the person.
     */
    private String note;
}
