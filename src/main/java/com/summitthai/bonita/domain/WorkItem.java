/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.summitthai.bonita.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import lombok.ToString;

/**
 *
 * @author Wittakarn
 */
@ToString
public abstract class WorkItem implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userId;
    private String password;
    private String taskId;
    private String processId;
    private String processName;
    private Map<String, Serializable> updateField;
    private List<String> filterField;
    private List<String> returnField;
    private List<String> orderClause;

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the taskId
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * @param taskId the taskId to set
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * @return the processName
     */
    public String getProcessName() {
        return processName;
    }

    /**
     * @param processName the processName to set
     */
    public void setProcessName(String processName) {
        this.processName = processName;
    }

    /**
     * @return the updateField
     */
    public Map<String, Serializable> getUpdateField() {
        return updateField;
    }

    /**
     * @param updateField the updateField to set
     */
    public void setUpdateField(Map<String, Serializable> updateField) {
        this.updateField = updateField;
    }

    /**
     * @return the filterField
     */
    public List<String> getFilterField() {
        return filterField;
    }

    /**
     * @param filterField the filterField to set
     */
    public void setFilterField(List<String> filterField) {
        this.filterField = filterField;
    }

    /**
     * @return the returnField
     */
    public List<String> getReturnField() {
        return returnField;
    }

    /**
     * @param returnField the returnField to set
     */
    public void setReturnField(List<String> returnField) {
        this.returnField = returnField;
    }

    /**
     * @return the orderClause
     */
    public List<String> getOrderClause() {
        return orderClause;
    }

    /**
     * @param orderClause the orderClause to set
     */
    public void setOrderClause(List<String> orderClause) {
        this.orderClause = orderClause;
    }

    /**
     * @return the processId
     */
    public String getProcessId() {
        return processId;
    }

    /**
     * @param processId the processId to set
     */
    public void setProcessId(String processId) {
        this.processId = processId;
    }

}
