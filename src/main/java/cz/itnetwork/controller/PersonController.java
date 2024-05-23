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
package cz.itnetwork.controller;

import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonStatisticsDTO;
import cz.itnetwork.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private PersonService personService;

    /**
     * Adds a new person to the system.
     *
     * @param personDTO The DTO (Data Transfer Object) representing the person to add.
     * @return The DTO representing the added person.
     */
    @PostMapping("/persons")
    public PersonDTO addPerson(@RequestBody PersonDTO personDTO) {
        return personService.addPerson(personDTO);
    }

    /**
     * Retrieves a list of all persons in the system.
     *
     * @return A list of DTOs representing all persons.
     */
    @GetMapping("/persons")
    public List<PersonDTO> getPersons() {
        return personService.getAll();
    }

    /**
     * Deletes a person from the system by their ID.
     *
     * @param personId The ID of the person to delete.
     */
    @DeleteMapping("/persons/{personId}")
    public void deletePerson(@PathVariable Long personId) {
        personService.removePerson(personId);
    }

    /**
     * Retrieves details of a person by their ID.
     *
     * @param personId The ID of the person to retrieve.
     * @return The DTO representing the details of the person.
     */
    @GetMapping("/persons/{personId}")
    public PersonDTO getPersonDetail(@PathVariable Long personId) {
        return personService.getById(personId);
    }

    /**
     * Edits details of a person by their ID.
     *
     * @param personId  The ID of the person to edit.
     * @param personDTO The DTO containing the updated information.
     * @return The DTO representing the updated details of the person.
     */
    @PutMapping("/persons/{personId}")
    public PersonDTO editPerson(@PathVariable Long personId, @RequestBody PersonDTO personDTO) {
        return personService.editPerson(personId, personDTO);
    }

    /**
     * Retrieves statistics about persons in the system.
     *
     * @return A list of DTOs representing various statistics about persons.
     */
    @GetMapping("/persons/statistics")
    public List<PersonStatisticsDTO> getPersonStatistics() {
        return personService.getStatistics();
    }
}
