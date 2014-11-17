/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.summitthai.bonita.service;

import com.summitthai.bonita.domain.WorkItem;
import com.summitthai.bonita.exception.CancelClaimTaskException;
import com.summitthai.bonita.exception.ClaimTaskException;
import com.summitthai.bonita.exception.CompleteTaskException;
import com.summitthai.bonita.exception.CountTaskException;
import com.summitthai.bonita.exception.InitialTaskException;
import com.summitthai.bonita.exception.SearchTaskException;
import com.summitthai.bonita.exception.UpdateTaskException;
import com.summitthai.bonita.util.BonitaWrapper;
import com.summitthai.sdd.sys.util.NumberUtils;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 *
 * @author wittakarn
 */
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
@Stateless(name = "BonitaBPMImpl", mappedName = "bonitaBPMImpl")
public class BonitaBPMImpl implements BPM {

    private static final long serialVersionUID = 1L;

    public Object initialTask(WorkItem workItem) throws InitialTaskException {
        
        try {
            BonitaWrapper.instantiateProcess(workItem.getUserId(), workItem.getPassword(), workItem.getUpdateField());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SearchTaskException(e);
        }
    }

    public Object countTask(WorkItem workItem) throws CountTaskException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object searchPendingTask(WorkItem workItem) throws SearchTaskException {
        try {
            return BonitaWrapper.listPendingTasks(workItem.getUserId(), workItem.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            throw new SearchTaskException(e);
        }
    }
    
    public Object searchAssignedTask(WorkItem workItem) throws SearchTaskException {
        try {
            return BonitaWrapper.listAssignedTasks(workItem.getUserId(), workItem.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            throw new SearchTaskException(e);
        }
    }


    public Object updateTask(WorkItem workItem) throws UpdateTaskException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object completeTask(WorkItem workItem) throws CompleteTaskException {
        try {
            Long taskId = Long.valueOf(workItem.getTaskId());
            BonitaWrapper.executeATask(workItem.getUserId(), workItem.getPassword(), taskId, workItem.getUpdateField());
            return null;
        } catch (Exception e) {
            throw new CompleteTaskException(e);
        }
    }

    public Object claimTask(WorkItem workItem) throws ClaimTaskException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object cancelClaimTask(WorkItem workItem) throws CancelClaimTaskException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object searchTaskByTaskId(WorkItem workItem) throws SearchTaskException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object updateTask(List<WorkItem> workItem) throws UpdateTaskException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object completeTask(List<WorkItem> workItem) throws CompleteTaskException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
