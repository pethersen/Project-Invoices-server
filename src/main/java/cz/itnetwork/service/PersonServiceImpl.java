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
package cz.itnetwork.service;

import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonStatisticsDTO;
import cz.itnetwork.dto.mapper.PersonMapper;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private PersonRepository personRepository;


    /**
     * Adds a new person to the system.
     *
     * @param personDTO The DTO (Data Transfer Object) representing the person to be added.
     * @return The DTO representing the added person.
     */
    public PersonDTO addPerson(PersonDTO personDTO) {
        // Map PersonDTO to PersonEntity
        PersonEntity entity = personMapper.toEntity(personDTO);

        // Save the entity to the repository
        entity = personRepository.save(entity);

        // Map the saved entity back to DTO and return
        return personMapper.toDTO(entity);
    }

    /**
     * Marks a person as hidden in the system without deleting the record.
     *
     * @param personId The ID of the person to be marked as hidden.
     */
    @Override
    public void removePerson(long personId) {
        try {
            // Fetch the person entity by ID
            PersonEntity person = fetchPersonById(personId);

            // Set the 'hidden' flag to true
            person.setHidden(true);

            // Save the updated entity
            personRepository.save(person);
        } catch (NotFoundException ignored) {
            // No action needed if the person is not found
        }
    }

    /**
     * Retrieves all visible persons from the system.
     *
     * @return A list of DTOs representing all visible persons.
     */
    @Override
    public List<PersonDTO> getAll() {
        return personRepository.findByHidden(false)
                .stream()
                .map(i -> personMapper.toDTO(i))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a person by their ID.
     *
     * @param personId The ID of the person to retrieve.
     * @return The DTO representing the retrieved person.
     */
    @Override
    public PersonDTO getById(long personId) {
        // Fetch the person entity by ID
        PersonEntity personEntity = fetchPersonById(personId);

        // Map the entity to DTO and return
        return personMapper.toDTO(personEntity);
    }

    /**
     * Updates the information of a person.
     *
     * @param personId  The ID of the person to be edited.
     * @param personDTO The DTO containing the updated information.
     * @return The DTO representing the edited person.
     */
    @Override
    public PersonDTO editPerson(long personId, PersonDTO personDTO) {
        try {
            // Fetch the person entity by ID
            PersonEntity deletedPerson = fetchPersonById(personId);

            // Mark the person as hidden
            deletedPerson.setHidden(true);

            // Save the updated 'hidden' status
            personRepository.save(deletedPerson);

            personDTO.setId(null);

            // Map the updated DTO to entity
            PersonEntity editedPerson = personMapper.toEntity(personDTO);

            // Save the edited person
            editedPerson = personRepository.save(editedPerson);

            // Map the edited entity back to DTO and return
            return personMapper.toDTO(editedPerson);

        } catch (NotFoundException ignored) {
            // No action needed if the person is not found
        }
        return null;
    }

    // region: Private methods
    /**
     * <p>Attempts to fetch a person.</p>
     * <p>In case a person with the passed [id] doesn't exist a [{@link org.webjars.NotFoundException}] is thrown.</p>
     *
     * @param id Person to fetch
     * @return Fetched entity
     * @throws org.webjars.NotFoundException In case a person with the passed [id] isn't found
     */
    private PersonEntity fetchPersonById(long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person with id " + id + " wasn't found in the database."));
    }
    // endregion

    /**
     * Retrieves statistics for each visible person in the system.
     * This method fetches information about each person such as their ID, name,
     * and revenue generated (if available).
     *
     * @return A list of {@link PersonStatisticsDTO} objects containing statistics
     *         for each visible person.
     */
    public List<PersonStatisticsDTO> getStatistics() {
        List<PersonEntity> personEntities = personRepository.findByHidden(false);
        List<PersonStatisticsDTO> personStatisticsDTOs = new ArrayList<>();

        for (PersonEntity entity : personEntities) {
            PersonStatisticsDTO person = new PersonStatisticsDTO();
            person.setPersonId(entity.getId());
            person.setPersonName(entity.getName());
            person.setRevenue(personRepository.getRevenue(entity.getId()));
            if (person.getRevenue() == null)
                person.setRevenue(0L);
            personStatisticsDTOs.add(person);
        }

        return personStatisticsDTOs;
    }
}
