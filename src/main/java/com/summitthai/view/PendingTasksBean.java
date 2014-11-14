/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.summitthai.view;

import com.summitthai.bonita.domain.User;
import com.summitthai.bonita.entity.Leave;
import com.summitthai.bonita.service.BPM;
import com.summitthai.bonita.service.LeaveServiceable;
import com.summitthai.sdd.sys.util.NumberUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Wittakarn
 */
@Slf4j
@Getter
@Setter
@ToString
@ViewScoped
@ManagedBean(name="pendingTasksBean")
public class PendingTasksBean implements Serializable{
    
    private Leave leave;
    private List<Map<String, Object>> hashs;
    private List<Leave> leaves;
    
    @ManagedProperty(value = "#{loginBean.user}")
    private User user;
    
    @EJB
    private BPM bpm;
    
    @EJB
    private LeaveServiceable leaveServiceable;
    
    public PendingTasksBean(){
        leave = new Leave();
        leaves = new ArrayList<Leave>();
    }
    
    @PostConstruct
    public void init(){
        search(null);
    }
    
    public void search(ActionEvent event){
        leave.setUserId(user.getName());
        leave.setPassword(user.getPassword());
        hashs = (List<Map<String, Object>>) bpm.searchTask(leave);
        PendingTasksBean.log.debug("hashs.size() = " + hashs.size());
        
        for (Map<String, Object> map : hashs) {
            PendingTasksBean.log.debug("map.get(\"leaveId\") = " + map.get("leaveId"));
            Leave l = leaveServiceable.searchByPrimaryKey(NumberUtils.toBigDecimal(map.get("leaveId").toString()));
            l.setTaskId(map.get("taskId").toString());
            leaves.add(l);
        }
    }
    
}
