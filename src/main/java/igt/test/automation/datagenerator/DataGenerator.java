
package igt.test.automation.datagenerator;

import java.util.Locale;

import org.joda.time.DateTime;

import igt.test.automation.constants.LangLocale;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;

/** 
 * Data generator utility with production quality of data for Person, Company, Banking related data. 
 * 
 * @author 
 * 
 * */
public class DataGenerator {

    /** fairy object. */
    private Fairy fairy;
    /** person object. */
    private Person person;
    
    /** default constructor. 
     * 
     * @param locale - The lang locale you want the data to be.
     * 
     * */
    public DataGenerator(final LangLocale locale) {
        fairy = Fairy.create(Locale.forLanguageTag(locale.getValue()));
        person = fairy.person();
    }
    
    public String getFirstName() {
        return person.getFirstName();
    }

    public String getLastName() {
        return person.getLastName();
    }


    public String getMiddleName() {
        return person.getMiddleName();
    }


    public String getEmail() {
        return person.getEmail();
    }

    public String getUsername() {
        return person.getUsername();
    }

    public String getPassword() {
        return person.getPassword();
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public boolean isMale() {
        return person.isMale();
    }

    public boolean isFemale() {
        return person.isFemale();
    }

    public String getSex() {
        return person.getSex().toString();
    }

    public String getTelephoneNumber() {
        return person.getTelephoneNumber();
    }

    public DateTime getDateOfBirth() {
        return person.getDateOfBirth();
    }

    public int getAge() {
        return person.getAge();
    }

    public String getNationalIdentityCardNumber() {
        return person.getNationalIdentityCardNumber();
    }

    public String getAddressLine1() {
        return person.getAddress().getAddressLine1();
    }

    public String getAddressLine2() {
        return person.getAddress().getAddressLine2();
    }

    public String getApartmentNumber() {
        return person.getAddress().getApartmentNumber();
    }

    public String getCity() {
        return person.getAddress().getCity();
    }

    public String getPostalCode() {
        return person.getAddress().getPostalCode();
    }

    public String getStreet() {
        return person.getAddress().getStreet();
    }

    public String getStreetNumber() {
        return person.getAddress().getStreetNumber();
    }

    public String getCompanyName() {
        return person.getCompany().getName();
    }

    public String getCompanyUrl() {
        return person.getCompany().getUrl();
    }

    public String getCompanyEmail() {
        return person.getCompany().getEmail();
    }

    public String getCompanyDomain() {
        return person.getCompany().getDomain();
    }

    public String getCompanyVatIdentificationNumber() {
        return person.getCompany().getVatIdentificationNumber();
    }

    public String getPassportNumber() {
        return person.getPassportNumber();
    }
    
}

