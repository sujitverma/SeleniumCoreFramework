
package igt.test.automation.alm.response.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Class have getter and setter for ALM Response Entity.
 * 
 * @author 
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "Fields", "Type", "children-count" })
public class Entity {

    /**
     * It contains the list of fields which comes in ALM response for different
     * artifacts.
     */
    @JsonProperty("Fields")
    private List<Field> fields = null;

    /**
     * It contains the type of ALM response artifact like Defects, TestSets,
     * TestCase, etc.
     */
    @JsonProperty("Type")
    private String type;

    /** It contains the childer count of the entity return in response. */
    @JsonProperty("children-count")
    private Integer childrenCount;

    @JsonProperty("Fields")
    public List<Field> getFields() {
        return fields;
    }

    @JsonProperty("Fields")
    public void setFields(final List<Field> fields) {
        this.fields = fields;
    }

    @JsonProperty("Type")
    public String getType() {
        return type;
    }

    @JsonProperty("Type")
    public void setType(final String type) {
        this.type = type;
    }

    @JsonProperty("children-count")
    public Integer getChildrenCount() {
        return childrenCount;
    }

    @JsonProperty("children-count")
    public void setChildrenCount(final Integer childrenCount) {
        this.childrenCount = childrenCount;
    }

}
