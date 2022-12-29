package com.truemega.solfa.view.utils;
//import com.egabi.bpmapplication.bpm.utils.ApplicationLogger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import oracle.jbo.domain.Date;

public class DateConverter implements Converter {
    public DateConverter() {
    }

    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String string) {
        //ApplicationLogger.log("GetAsObject : String = "+string, ApplicationLogger.DEBUG);
        if (facesContext == null || uiComponent == null) {
            throw new NullPointerException("FacesContext and UIComponent can not be null");
        }
        if (string == null) {

            return null;
        }
        try {
            Date convertHijriToJboGro = DateUtils.convertHijriToJboGro(string);
            return convertHijriToJboGro;
        } catch (Exception ex) {
			ex.printStackTrace();
            //ResourceBundle bundle = ResourceBundle.getBundle("view.ViewControllerBundle", Locale.getDefault());
            //String saveConfirmed = bundle.getString("date.converter.error");
            //final String message = String.format("Unable to convert gregorian value \"%s\" into a Hijri", string);
            final String message = "1432/09/23";
            throw new ConverterException(message, ex);
        }
    }


    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
       if (facesContext == null || uiComponent == null) {
            throw new NullPointerException("FacesContext and UIComponent can not be null");

        }
//        ApplicationLogger.log("Value = " + object.toString(), ApplicationLogger.DEBUG);
        java.util.Date utilDate = (java.util.Date) (object);

        String dateMethod = DateUtils.convertJboGroToHijri(DateUtils.getJboDateFromUtilDate(utilDate));

        //McitAMImpl mcitAM = (McitAMImpl) ADFUtils.getApplicationModuleForDataControl("McitAMDataControl");

        /*
        oracle.jbo.domain.Date myDate = new oracle.jbo.domain.Date(object.toString());
        ApplicationLogger.log("Hijri Date = "+myDate, ApplicationLogger.DEBUG);
        String dateMethod = mcitAM.hijriDateMethod(myDate);
        */
        return dateMethod;
    }
   public static int  compareDate(oracle.jbo.domain.Date d1 ,oracle.jbo.domain.Date d2 )
      {
        if(d1 == null || d2==null )
          return 0;
       
        if(d1.compareTo(d2) > 0)
          return 1;
      
        return -1;
      }
}
