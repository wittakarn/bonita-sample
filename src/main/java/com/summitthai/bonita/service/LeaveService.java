/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.summitthai.bonita.service;

import com.summitthai.bonita.entity.Leave;
import com.summitthai.sdd.sys.util.NumberUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Wittakarn
 */
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
@Stateless(name = "LeaveService", mappedName = "leaveService")
public class LeaveService implements LeaveServiceable, Serializable {

    @PersistenceContext(name = "LEAVESCHEMADB")
    private EntityManager em;
    
    @EJB
    private BPM bpm;
    
    @Override
    public void create(Leave leave) {
        em.persist(leave);
        em.flush();
        Map<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("leaveId", NumberUtils.toLong(leave.getLeaveId()));
        leave.setUpdateField(map);
        bpm.initialTask(leave);
    }
    
    public Leave searchByPrimaryKey(BigDecimal leaveId){
        return em.find(Leave.class, leaveId);
    }

}
