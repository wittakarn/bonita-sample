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
import java.io.Serializable;
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
@ManagedBean(name = "submitionLeaveBean")
public class SubmitionLeaveBean implements Serializable {

    private Leave leaveCreate;

    @EJB
    private LeaveServiceable leaveServiceable;
    
    @EJB
    private BPM bpm;
    
    @ManagedProperty(value = "#{pendingTasksBean}")
    private PendingTasksBean pendingTasksBean;

    @ManagedProperty(value = "#{loginBean.user}")
    private User user;

    public SubmitionLeaveBean() {
        leaveCreate = new Leave();
    }

    @PostConstruct
    public void init() {
        leaveCreate.setUserId(user.getName());
        leaveCreate.setPassword(user.getPassword());
    }

    public void createLeave(ActionEvent event) {
        leaveServiceable.create(leaveCreate);
        leaveCreate = new Leave();
    }
    
    public void submitLeave(Leave leave) {
        leave.setUserId(user.getName());
        leave.setPassword(user.getPassword());
        bpm.completeTask(leave);
        pendingTasksBean.search(null);
    }

}
