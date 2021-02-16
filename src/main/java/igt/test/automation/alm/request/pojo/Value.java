
package igt.test.automation.alm.request.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Class have getter and setters of ALM Field value object.
 * 
 * @author 
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "value" })
public class Value {

    /** ALM field value. */
    @JsonProperty("value") private String value;

    @JsonProperty("value") public String getValue() {
        return value;
    }

    @JsonProperty("value") public void setValue(final String value) {
        this.value = value;
    }
}
