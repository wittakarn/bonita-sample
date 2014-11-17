/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.summitthai.view;

import com.summitthai.bonita.domain.User;
import com.summitthai.bonita.entity.Leave;
import com.summitthai.bonita.service.LeaveServiceable;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
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
@ManagedBean(name = "approvalLeaveBean")
public class ApprovalLeaveBean implements Serializable {

    private Leave leaveEdit;

    @EJB
    private LeaveServiceable leaveServiceable;
    
    @ManagedProperty(value = "#{pendingTasksBean}")
    private PendingTasksBean pendingTasksBean;

    @ManagedProperty(value = "#{loginBean.user}")
    private User user;

    public ApprovalLeaveBean() {
        leaveEdit = new Leave();
    }

    @PostConstruct
    public void init() {
    }
    
    public void approve(Boolean approve) {
        leaveEdit.setUserId(user.getName());
        leaveEdit.setPassword(user.getPassword());
        leaveEdit.setApprove(approve);
        
        leaveServiceable.approve(leaveEdit);
        pendingTasksBean.searchPending();
    }

}
