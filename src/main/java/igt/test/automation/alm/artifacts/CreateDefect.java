package igt.test.automation.alm.artifacts;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import igt.test.automation.alm.functionlibrary.APIManager;
import igt.test.automation.alm.request.pojo.Field;
import igt.test.automation.alm.request.pojo.Fields;
import igt.test.automation.alm.request.pojo.Value;

/**
 * Class use to create the ALM defect request.
 * 
 * @author 
 *
 */
public class CreateDefect {

    /** The Log4j logger. */
    private static final Logger LOG = LogManager.getLogger(CreateDefect.class);

    /**
     * Function use to create the ALM defect json body as string.
     * 
     * @param detectedBy
     *            - ALM User Name who detected the bug
     * @param testSetUpInformation
     *            - ALM mandatory Test Set up information
     * @param defectNameSummary
     *            - ALM defect summary.
     * @param stepsToReproduce
     *            - ALM defect step to reproduce
     * @param status
     *            - ALM defect current status like Open/New/etc
     * @param severity
     *            - igt defined severity like "4-Significant: minor function is
     *            inoperable, no workaround available."
     * @param owner
     *            - Who will the defect owner
     * @param funcComponent
     *            - Project Function component
     * @param developedBy
     *            - ALM user who created this defect.
     * @param testingPhase
     *            - In which testing phase this defect appear like
     *            Regression/Sanity/Smoke/Function Testing/etc
     * @return - ALM defect Json request.
     * 
     * @author 
     */
    public String createDefectRequest(final String detectedBy,
            final String testSetUpInformation, final String defectNameSummary,
            final String stepsToReproduce, final String status, final String severity,
            final String owner, final String funcComponent, final String developedBy,
            final String testingPhase) {
        String jsonString = null;
        Fields fieldsObj = new Fields();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("kk:mm:ss");
        String dateTimeString = format.format(date);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateformat.format(date);
        try {
            fieldsObj.setType("defect");
            List<Field> fieldList = new ArrayList<>();

            // **************** Priority Field **************************
            Field priorityFieldObj = new Field();
            Value priorityValueObj = new Value();
            priorityFieldObj.setName("priority");
            priorityValueObj.setValue("Conditional");
            List<Value> priorityValueObjList = new ArrayList<>();
            priorityValueObjList.add(priorityValueObj);
            priorityFieldObj.setValues(priorityValueObjList);
            fieldList.add(priorityFieldObj);

            // **************** Test Set Up Information Field
            // **************************
            Field tSetUpInfoFieldObj = new Field();
            Value tSetUpInfoValueObj = new Value();
            tSetUpInfoFieldObj.setName("user-28");
            tSetUpInfoValueObj.setValue(testSetUpInformation);
            List<Value> tSetUpInfoidValueObjList = new ArrayList<>();
            tSetUpInfoidValueObjList.add(tSetUpInfoValueObj);
            tSetUpInfoFieldObj.setValues(tSetUpInfoidValueObjList);
            fieldList.add(tSetUpInfoFieldObj);

            // **************** Creation Date Field **************************
            Field execDateFieldObj = new Field();
            Value execDateValueObj = new Value();
            execDateFieldObj.setName("creation-time");
            execDateValueObj.setValue(dateString);
            List<Value> execDateValueObjList = new ArrayList<>();
            execDateValueObjList.add(execDateValueObj);
            execDateFieldObj.setValues(execDateValueObjList);
            fieldList.add(execDateFieldObj);

            // **************** Name Field **************************
            Field nameSummaryFieldObj = new Field();
            Value nameSummaryValueObj = new Value();
            nameSummaryFieldObj.setName("name");
            nameSummaryValueObj.setValue(defectNameSummary);
            List<Value> nameSummaryValueObjList = new ArrayList<>();
            nameSummaryValueObjList.add(nameSummaryValueObj);
            nameSummaryFieldObj.setValues(nameSummaryValueObjList);
            fieldList.add(nameSummaryFieldObj);

            // **************** User-27 (Steps To Reproduce) Field
            // **************************
            Field stepToReproduceFieldObj = new Field();
            Value stepToReproduceValueObj = new Value();
            stepToReproduceFieldObj.setName("user-27");
            stepToReproduceValueObj.setValue(stepsToReproduce);
            List<Value> stepToReproduceValueObjList = new ArrayList<>();
            stepToReproduceValueObjList.add(stepToReproduceValueObj);
            stepToReproduceFieldObj.setValues(stepToReproduceValueObjList);
            fieldList.add(stepToReproduceFieldObj);

            // **************** Status Field **************************
            Field statusFieldObj = new Field();
            Value statusValueObj = new Value();
            statusFieldObj.setName("status");
            statusValueObj.setValue(status);
            List<Value> statusValueObjList = new ArrayList<>();
            statusValueObjList.add(statusValueObj);
            statusFieldObj.setValues(statusValueObjList);
            fieldList.add(statusFieldObj);

            // **************** description Field **************************
            Field descriptionFieldObj = new Field();
            Value descriptionValueObj = new Value();
            descriptionFieldObj.setName("description");
            descriptionValueObj
                    .setValue(defectNameSummary + "\n" + stepsToReproduce);
            List<Value> descriptionValueObjList = new ArrayList<>();
            descriptionValueObjList.add(descriptionValueObj);
            descriptionFieldObj.setValues(descriptionValueObjList);
            fieldList.add(descriptionFieldObj);

            // **************** Last Modified Field **************************
            Field lastModifiedFieldObj = new Field();
            Value lastModifiedValueObj = new Value();
            lastModifiedFieldObj.setName("last-modified");
            lastModifiedValueObj.setValue(dateString + " " + dateTimeString);
            List<Value> lastModifiedValueObjList = new ArrayList<>();
            lastModifiedValueObjList.add(lastModifiedValueObj);
            lastModifiedFieldObj.setValues(lastModifiedValueObjList);
            fieldList.add(lastModifiedFieldObj);

            // **************** severity Field **************************
            Field severityFieldObj = new Field();
            Value severityValueObj = new Value();
            severityFieldObj.setName("severity");
            severityValueObj.setValue(severity);
            List<Value> severityValueObjList = new ArrayList<>();
            severityValueObjList.add(severityValueObj);
            severityFieldObj.setValues(severityValueObjList);
            fieldList.add(severityFieldObj);

            // **************** Detected By Field **************************
            Field actualTesterFieldObj = new Field();
            Value actualTesterValueObj = new Value();
            actualTesterFieldObj.setName("detected-by");
            actualTesterValueObj.setValue(detectedBy);
            List<Value> actualTesterValueObjList = new ArrayList<>();
            actualTesterValueObjList.add(actualTesterValueObj);
            actualTesterFieldObj.setValues(actualTesterValueObjList);
            fieldList.add(actualTesterFieldObj);

            // **************** owner Field **************************
            Field ownerFieldObj = new Field();
            Value ownerValueObj = new Value();
            ownerFieldObj.setName("owner");
            ownerValueObj.setValue(owner);
            List<Value> ownerValueObjList = new ArrayList<>();
            ownerValueObjList.add(ownerValueObj);
            ownerFieldObj.setValues(ownerValueObjList);
            fieldList.add(ownerFieldObj);

            // **************** Functional Component Field ************
            Field funcComponentFieldObj = new Field();
            Value funcComponentValueObj = new Value();
            funcComponentFieldObj.setName("user-08");
            funcComponentValueObj.setValue(funcComponent);
            List<Value> funcComponentValueObjList = new ArrayList<>();
            funcComponentValueObjList.add(funcComponentValueObj);
            funcComponentFieldObj.setValues(funcComponentValueObjList);
            fieldList.add(funcComponentFieldObj);

            // ********** Developed By (Dev Team Name) Field **********
            Field developedByFieldObj = new Field();
            Value developedByValueObj = new Value();
            developedByFieldObj.setName("user-09");
            developedByValueObj.setValue(developedBy);
            List<Value> developedByValueObjList = new ArrayList<>();
            developedByValueObjList.add(developedByValueObj);
            developedByFieldObj.setValues(developedByValueObjList);
            fieldList.add(developedByFieldObj);

            // **************** Type Field **************************
            Field typeFieldObj = new Field();
            Value typeValueObj = new Value();
            typeFieldObj.setName("user-05");
            typeValueObj.setValue("Defect");
            List<Value> typeValueObjList = new ArrayList<>();
            typeValueObjList.add(typeValueObj);
            typeFieldObj.setValues(typeValueObjList);
            fieldList.add(typeFieldObj);

            // **************** Testing Phase Field **************************
            Field testingPhaseFieldObj = new Field();
            Value testingPhaseValueObj = new Value();
            testingPhaseFieldObj.setName("user-07");
            testingPhaseValueObj.setValue(testingPhase);
            List<Value> testingPhaseValueObjList = new ArrayList<>();
            testingPhaseValueObjList.add(testingPhaseValueObj);
            testingPhaseFieldObj.setValues(testingPhaseValueObjList);
            fieldList.add(testingPhaseFieldObj);

            // **************** Testing Phase Field **************************
            Field detectionVersionFieldObj = new Field();
            Value detectionVersionValueObj = new Value();
            detectionVersionFieldObj.setName("detection-version");
            detectionVersionValueObj.setValue("N/A");
            List<Value> detectionVersionValueObjList = new ArrayList<>();
            detectionVersionValueObjList.add(detectionVersionValueObj);
            detectionVersionFieldObj.setValues(detectionVersionValueObjList);
            fieldList.add(detectionVersionFieldObj);

            // *********** User 17 Additional Field ***********
            Field user17FieldObj = new Field();
            Value user17ValueObj = new Value();
            user17FieldObj.setName("user-17");
            user17ValueObj.setValue("Dev");
            List<Value> user17ValueObjList = new ArrayList<>();
            user17ValueObjList.add(user17ValueObj);
            user17FieldObj.setValues(user17ValueObjList);
            fieldList.add(user17FieldObj);

            // **************** User 24 Additional Field **********
            Field user24FieldObj = new Field();
            Value user24ValueObj = new Value();
            user24FieldObj.setName("user-24");
            user24ValueObj.setValue("4. Business Inconvenience");
            List<Value> user24ValueObjList = new ArrayList<>();
            user24ValueObjList.add(user24ValueObj);
            user24FieldObj.setValues(user24ValueObjList);
            fieldList.add(user24FieldObj);

            // **************** User 06 Additional Field ************
            Field user06FieldObj = new Field();
            Value user06ValueObj = new Value();
            user06FieldObj.setName("user-06");
            user06ValueObj.setValue("4-Low");
            List<Value> user06ValueObjList = new ArrayList<>();
            user06ValueObjList.add(user06ValueObj);
            user06FieldObj.setValues(user06ValueObjList);
            fieldList.add(user06FieldObj);

            fieldsObj.setFields(fieldList);

            jsonString = APIManager.convertToJson(fieldsObj);
            LOG.info(jsonString);

        } catch (Exception e) {
            LOG.error("Exception while creating defect json request:- "
                    + e);
        }
        return jsonString;
    }
}
