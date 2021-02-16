
package igt.test.automation.alm.request.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Class have List of ALM Field objects.
 * 
 * @author 
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Fields",
    "Type"
})
public class Fields {

    /** List of ALM field object. */
    @JsonProperty("Fields") private List<Field> fields = null;
    
    /** Type of ALM artifacts like TestCase, TestSet, TestRun, Defect etc. */
    @JsonProperty("Type") private String type;
   
    @JsonProperty("Fields") public List<Field> getFields() {
        return fields;
    }

    @JsonProperty("Fields") public void setFields(final List<Field> fields) {
        this.fields = fields;
    }

    @JsonProperty("Type") public String getType() {
        return type;
    }

    @JsonProperty("Type") public void setType(final String type) {
        this.type = type;
    }
}
