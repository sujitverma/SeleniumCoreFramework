
package igt.test.automation.alm.response.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Class have getter and setters of ALM Field objects.
 * 
 * @author 
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "Name", "values" })
public class Field {

    /** It have the name of ALM field. */
    @JsonProperty("Name")
    private String name;

    /** It have the list of value object for the given ALM field. */
    @JsonProperty("values")
    @JsonIgnore
    private List<Value> values = null;

    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    @JsonProperty("Name")
    public void setName(final String name) {
        this.name = name;
    }

    @JsonProperty("values")
    public List<Value> getValues() {
        return values;
    }

    @JsonProperty("values")
    public void setValues(final List<Value> values) {
        this.values = values;
    }

}
