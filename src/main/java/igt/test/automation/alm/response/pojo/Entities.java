package igt.test.automation.alm.response.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Class to have getter and setters for the list of entities return in ALM
 * response.
 * 
 * @author 
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "entities", "TotalResults" })
public class Entities {

    /** List of entities return in ALM response. */
    @JsonProperty("entities")
    private List<Entity> entities = null;

    /** Total Result count in ALM response. */
    @JsonProperty("TotalResults")
    private Integer totalResults;

    @JsonProperty("entities")
    public List<Entity> getEntities() {
        return entities;
    }

    @JsonProperty("entities")
    public void setEntities(final List<Entity> entities) {
        this.entities = entities;
    }

    @JsonProperty("TotalResults")
    public Integer getTotalResults() {
        return totalResults;
    }

    @JsonProperty("TotalResults")
    public void setTotalResults(final Integer totalResults) {
        this.totalResults = totalResults;
    }

}
