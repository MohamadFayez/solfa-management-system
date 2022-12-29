package com.truemega.solfa.view.beans;

import com.truemega.solfa.view.utils.ADFUtil;
import com.truemega.solfa.view.utils.JSFUtils;
import com.truemega.solfa.view.utils.ExcelUtils;

import com.truemega.solfa.model.SolfaAppModuleImpl;

import com.truemega.solfa.model.util.ApplicationLogger;
import com.truemega.solfa.model.view.SolfaEmployeesViewImpl;
import com.truemega.solfa.model.view.SolfaEmployeesViewRowImpl;

import java.io.IOException;
import java.io.Serializable;

import java.sql.SQLException;

import java.text.ParseException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;

import org.apache.myfaces.trinidad.model.UploadedFile;


public class UploadEmpolyeeBean extends ExcelUtils implements Serializable {
    @SuppressWarnings("compatibility:4381144201264713158")
    private static final long serialVersionUID = -6254156899626771566L;
    //Component Binding of af:table
    private RichTable empTable;
    private RichInputText bindInput;
    private RichInputFile uploadedFile;
    private RichPopup p1;
    private RichPopup p2;
    private String loading;

 
    public String handleUpload() {
        UploadedFile file = (UploadedFile) uploadedFile.getValue();
        boolean doRollback = false;
        loading = "true";


        try {
            if (file == null) {
                throw new RuntimeException("Kindly choose Excel File");
            }
            ADFUtil.findOperation("insertTrackData").execute();

            readExcelFile(file.getInputStream(), file.getContentType(), "new");
            ADFUtil.findOperation("Commit").execute();
        } catch (Exception e) {
			e.printStackTrace();
            FacesMessage msg = new FacesMessage(e.getMessage());
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            doRollback = true;

        } finally {
            if (doRollback) {
                ADFUtil.findOperation("Rollback").execute();
                ADFUtil.findIterator("SolfaEmployeesViewIterator").clearForRecreate();
            }
        }
        Boolean isDirty = (Boolean) ADFUtil.findOperation("isDirty").execute();
        if (!isDirty & !doRollback) {
            loading = "false";
            uploadedFile.resetValue();
            ADFUtil.findOperation("closeOpenedRequest").execute();
            String msg = JSFUtils.getStringFromBundle("solfa.uploadedSuccess");
            JSFUtils.addFacesInformationMessage(msg);
        }
        if (loading.equalsIgnoreCase("false"))
            AdfFacesContext.getCurrentInstance().addPartialTarget(empTable);
        uploadedFile.resetValue();
        return "";
    }


    public String save() {
        // Add event code here...
        ADFUtil.findOperation("Commit").execute();
        RichPopup p1 = (RichPopup) ADFUtil.findComponentInRoot("p1");
        p1.hide();
        RichPopup p2 = (RichPopup) ADFUtil.findComponentInRoot("p2");
        p2.hide();
        ADFUtil.findIterator("SolfaEmployeesViewIterator").executeQuery();
        return null;
    }


    public String cancel() {
        // Add event code here...
        ADFUtil.findOperation("Rollback").execute();
        RichPopup p1 = (RichPopup) ADFUtil.findComponentInRoot("p1");
        p1.hide();
        RichPopup p2 = (RichPopup) ADFUtil.findComponentInRoot("p2");
        p2.hide();
        return null;
    }

    public String editEmployee() throws SQLException {
        // Add event code here...
        java.lang.String bindStaffId = (java.lang.String) JSFUtils.resolveExpression("#{row.StaffId}");
        SolfaEmployeesViewImpl vo =
            (SolfaEmployeesViewImpl) ADFUtil.findIterator("EmployeesViewIterator").getViewObject();
      //  vo.setbindStaffId(bindStaffId);
        vo.executeQuery();
        p2.show(new RichPopup.PopupHints());
        return null;
    }


    /**Method to get Binding Container of current view port
     * @return
     */
    public BindingContainer getBindingsCont() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /**
     * Generic Method to execute operation
     * */
    public OperationBinding executeOperation(String operation) {
        OperationBinding createParam = getBindingsCont().getOperationBinding(operation);
        return createParam;

    }

    public void setBindInput(RichInputText bindInput) {
        this.bindInput = bindInput;
    }

    public RichInputText getBindInput() {
        return bindInput;
    }

    public void setUploadedFile(RichInputFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public RichInputFile getUploadedFile() {
        return uploadedFile;
    }

    public void setEmpTable(RichTable empTable) {
        this.empTable = empTable;
    }

    public RichTable getEmpTable() {
        return empTable;
    }

    public void setP1(RichPopup p1) {
        this.p1 = p1;
    }

    public RichPopup getP1() {
        return p1;
    }

    public void setP2(RichPopup p2) {
        this.p2 = p2;
    }

    public RichPopup getP2() {
        return p2;
    }

    public String deleteEmployee() {
        // Add event code here...
        ADFUtil.findOperation("Delete").execute();
        p1.show(new RichPopup.PopupHints());
        return null;
    }


    public void setLoading(String loading) {
        this.loading = loading;
    }

    public String getLoading() {
        return loading;
    }
}
