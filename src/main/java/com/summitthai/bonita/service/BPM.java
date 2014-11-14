package com.summitthai.bonita.service;

import com.summitthai.bonita.domain.WorkItem;
import com.summitthai.bonita.exception.CancelClaimTaskException;
import com.summitthai.bonita.exception.ClaimTaskException;
import com.summitthai.bonita.exception.CompleteTaskException;
import com.summitthai.bonita.exception.CountTaskException;
import com.summitthai.bonita.exception.InitialTaskException;
import com.summitthai.bonita.exception.SearchTaskException;
import com.summitthai.bonita.exception.UpdateTaskException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface BPM extends Serializable {

    public Object initialTask(WorkItem workItem) throws InitialTaskException;

    public Object countTask(WorkItem workItem) throws CountTaskException;

    public Object searchTask(WorkItem workItem) throws SearchTaskException;

    public Object updateTask(WorkItem workItem) throws UpdateTaskException;

    public Object completeTask(WorkItem workItem) throws CompleteTaskException;

    public Object claimTask(WorkItem workItem) throws ClaimTaskException;

    public Object cancelClaimTask(WorkItem workItem) throws CancelClaimTaskException;

    public Object searchTaskByTaskId(WorkItem workItem) throws SearchTaskException;

    public Object updateTask(List<WorkItem> workItem) throws UpdateTaskException;

    public Object completeTask(List<WorkItem> workItem) throws CompleteTaskException;
}
