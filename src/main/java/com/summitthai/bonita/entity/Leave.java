/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.summitthai.bonita.entity;

import com.summitthai.bonita.domain.WorkItem;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Wittakarn
 */
@Entity
@Table(name = "sample_leave")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Leave.findAll", query = "SELECT l FROM Leave l"),
    @NamedQuery(name = "Leave.findByLeaveId", query = "SELECT l FROM Leave l WHERE l.leaveId = :leaveId"),
    @NamedQuery(name = "Leave.findByBeginDate", query = "SELECT l FROM Leave l WHERE l.beginDate = :beginDate"),
    @NamedQuery(name = "Leave.findByEndDate", query = "SELECT l FROM Leave l WHERE l.endDate = :endDate")})
public class Leave extends WorkItem implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "leaveId")
    private BigDecimal leaveId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "beginDate")
    @Temporal(TemporalType.DATE)
    private Date beginDate;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "endDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "reason")
    private String reason;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "remark")
    private String remark;
    
    @Transient
    private Boolean approve;

    public Leave() {
    }

    public Leave(BigDecimal leaveId) {
        this.leaveId = leaveId;
    }

    public Leave(BigDecimal leaveId, Date beginDate, Date endDate) {
        this.leaveId = leaveId;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public BigDecimal getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(BigDecimal leaveId) {
        this.leaveId = leaveId;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean isApprove() {
        return approve;
    }

    public void setApprove(Boolean approve) {
        this.approve = approve;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (leaveId != null ? leaveId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Leave)) {
            return false;
        }
        Leave other = (Leave) object;
        if ((this.leaveId == null && other.leaveId != null) || (this.leaveId != null && !this.leaveId.equals(other.leaveId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.summitthai.bonita.entity.Leave[ leaveId=" + leaveId + " ]";
    }
    
}
