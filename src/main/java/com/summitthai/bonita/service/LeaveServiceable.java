/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.summitthai.bonita.service;

import com.summitthai.bonita.entity.Leave;
import java.math.BigDecimal;
import javax.ejb.Remote;

/**
 *
 * @author Wittakarn
 */
@Remote
public interface LeaveServiceable {
    public void create(Leave leave);
    public void update(Leave leave);
    public void approve(Leave leave);
    public Leave searchByPrimaryKey(BigDecimal leaveId);
}
