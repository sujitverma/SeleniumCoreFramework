
package igt.test.automation.constants; 
/**
 * ENUM for language locale.
 * 
 * @author 
 */
public enum LangLocale {
    
    /** ENGLISH. */
    EN("en", "English"),
    /** German. */
    DE("de", "German"),
    /** Spanish. */
    ES("es", "Spanish"),
    /** Georgian. */
    KA("ka", "Georgian"),
    /** Polish. */
    PL("pl", "Polish"),
    /** Swedish. */
    SV("sv", "Swedish"),
    /** Chinese. */
    Zh("zh", "Chinese");
    
    /** String for the value. */
    private String value;
    
    /** String for the description. */
    private String description;
    
    LangLocale(final String value, final String description){
        this.value = value;
        this.description = description;
    }

    /**
     * Gets the value field. 
     * @return the value
     */
    public String getValue() {
        return value;
    }

    
    /**
     * Gets the description field. 
     * 
     * @return the description 
     */
    public String getDescription() {
        return description;
    }

    

}
