
package igt.test.automation.alm.functionlibrary;

/**
 * Class to have information related to ALM test Set.
 * 
 * @author 
 *
 */
public class TestSet {

    /** ALM Test Set Name. */
    private String name;

    /** ALM Test Set Id. */
    private String id;

    /** ALM Test Set Parent id i.e. test set parent director id. */
    private String parentId;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(final String parentId) {
        this.parentId = parentId;
    }

}
