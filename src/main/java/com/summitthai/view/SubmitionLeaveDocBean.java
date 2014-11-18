/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.summitthai.view;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.XfaForm;
import com.summitthai.bonita.domain.User;
import com.summitthai.bonita.entity.Leave;
import com.summitthai.bonita.service.BPM;
import com.summitthai.bonita.service.LeaveServiceable;
import com.summitthai.pdf.util.DataExtraction;
import com.summitthai.sdd.sys.main.SystemConfig;
import com.summitthai.sdd.sys.util.FileUtils;
import com.summitthai.sdd.sys.util.NumberUtils;
import com.summitthai.sdd.sys.util.StringUtils;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Wittakarn
 */
@Slf4j
@Getter
@Setter
@ToString
@ViewScoped
@ManagedBean(name = "submitionLeaveDocBean")
public class SubmitionLeaveDocBean implements Serializable {

    private static final String PDF_TYPE = "application/pdf";
    private UploadedFile file;
    private Leave leaveCreate;

    @EJB
    private LeaveServiceable leaveServiceable;

    @EJB
    private BPM bpm;

    @ManagedProperty(value = "#{loginBean.user}")
    private User user;

    public SubmitionLeaveDocBean() {
        leaveCreate = new Leave();
    }

    @PostConstruct
    public void init() {
        leaveCreate.setUserId(user.getName());
        leaveCreate.setPassword(user.getPassword());
    }

    public void upload() {
        if (file != null) {
            try {
                fileExtractionProcess(file);
                leaveServiceable.create(leaveCreate);
                leaveCreate = new Leave();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void fileExtractionProcess(UploadedFile uploadFile) throws Exception {
        SubmitionLeaveDocBean.log.info("uploadFile : " + uploadFile);
        XfaForm xfa;
        if (uploadFile != null) {
            if (uploadFile.getInputstream() != null && uploadFile.getInputstream().available() != 0) {
                xfa = extractFormContent(uploadFile.getInputstream());
                SubmitionLeaveDocBean.log.debug("XML of XFA Form = " + DataExtraction.convertXFAToXMLString(xfa.getDatasetsNode()));
                setToLeave(extractNodeContent(xfa));
            }
            SubmitionLeaveDocBean.log.debug("leaveCreate : " + leaveCreate);
        }
    }

    private void fileUploadProcess(UploadedFile uploadFile) throws Exception {
        if (!uploadFile.getContentType().equals(PDF_TYPE)) {
            throw new Exception();
        }
        FileUtils.writeFile(uploadFile.getInputstream(), SystemConfig.getString(SystemConfig.CONF_UPLOAD).concat(uploadFile.getFileName()));
    }

    private XfaForm extractFormContent(InputStream ins) throws Exception {
        PdfReader reader;
        AcroFields form;
        try {
            reader = new PdfReader(ins);
            form = reader.getAcroFields();
            return form.getXfa();
        } finally {
            reader = null;
            form = null;
        }
    }

    private HashMap<String, String> extractNodeContent(XfaForm xfa) throws Exception {
        HashMap<String, String> hash = new HashMap<String, String>();
        try {
            DataExtraction.extractToHashMap(xfa.getDatasetsNode(), hash);
            return hash;
        } finally {
            hash = null;
        }
    }

    private void setToLeave(HashMap<String, String> hash) throws Exception {
        leaveCreate.setLeaveId(NumberUtils.toBigDecimal(hash.get("leaveId")));

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        leaveCreate.setBeginDate(formatter.parse(hash.get("beginDate")));
        leaveCreate.setEndDate(formatter.parse(hash.get("endDate")));
        leaveCreate.setReason(hash.get("reason"));
    }

//    private void setToLeave(HashMap<String, String> hash) throws Exception {
//        Method methodSet;
//        Method[] methods = leaveCreate.getClass().getMethods();
//        String methodName;
//        String fieldValue;
//        String fieldName;
//        SubmitionLeaveDocBean.log.debug("hash = " + hash);
//        for (Method method : methods) {
//            methodName = method.getName();
//            if (methodName.indexOf("get") == 0) {
//                fieldName = methodName.substring(3, 4).toLowerCase().concat(methodName.substring(4, methodName.length()));
//                SubmitionLeaveDocBean.log.debug("fieldName = " + fieldName);
//                fieldValue = hash.get(fieldName);
//                SubmitionLeaveDocBean.log.debug("fieldValue = " + fieldValue);
//                if (!StringUtils.isNullOrEmpty(fieldValue)) {
//                    methodSet = leaveCreate.getClass().getMethod("set".concat(methodName.substring(3, methodName.length())), String.class);
//                    methodSet.invoke(leaveCreate, fieldValue);
//                }
//            }
//        }
//    }
}
