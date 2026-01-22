package com.app.auth;


import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

public final class UiMessage {

    private UiMessage() {
        // utilitaire
    }

    /* =========================
       FacesMessage
       ========================= */

    public static void info(String message) {
        add(FacesMessage.SEVERITY_INFO, "Information", message);
    }

    public static void warn(String message) {
        add(FacesMessage.SEVERITY_WARN, "Attention", message);
    }

    public static void error(String message) {
        add(FacesMessage.SEVERITY_ERROR, "Erreur", message);
    }

    public static void fatal(String message) {
        add(FacesMessage.SEVERITY_FATAL, "Erreur critique", message);
    }

    public static void add(FacesMessage.Severity severity, String title, String message) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(severity, title, message));
    }

    /* =========================
       SweetAlert2 (PrimeFaces)
       ========================= */

    public static void swalSuccess(String title, String message) {
        execSwal("success", title, message);
    }

    public static void swalError(String title, String message) {
        execSwal("error", title, message);
    }

    public static void swalWarning(String title, String message) {
        execSwal("warning", title, message);
    }

    public static void swalInfo(String title, String message) {
        execSwal("info", title, message);
    }

    public static void swalConfirm(String title, String message, String onConfirmJs) {
        PrimeFaces.current().executeScript(
            "Swal.fire({"
            + "title: '" + escape(title) + "',"
            + "text: '" + escape(message) + "',"
            + "icon: 'question',"
            + "showCancelButton: true,"
            + "confirmButtonText: 'Oui',"
            + "cancelButtonText: 'Annuler'"
            + "}).then((result) => {"
            + " if (result.isConfirmed) { " + onConfirmJs + "; }"
            + "});"
        );
    }

    public static void swalSuccessAndReload(String title, String message) {
        PrimeFaces.current().executeScript(
            "Swal.fire({"
            + "icon: 'success',"
            + "title: '" + escape(title) + "',"
            + "text: '" + escape(message) + "'"
            + "}).then(() => {"
            + "  location.reload();"  // recharge la page après OK
            + "});"
        );
    }
    public static void swalSuccessAndRedirect(String title, String message, String page) {
        String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        String url = contextPath + page;  // Concatène le contexte
        PrimeFaces.current().executeScript(
            "Swal.fire({"
            + "icon: 'success',"
            + "title: '" + escape(title) + "',"
            + "text: '" + escape(message) + "'"
            + "}).then(() => {"
            + "  window.location.href = '" + url + "';"
            + "});"
        );
    }



    
    
    private static void execSwal(String type, String title, String message) {
        PrimeFaces.current().executeScript(
            "Swal.fire({"
            + "icon: '" + type + "',"
            + "title: '" + escape(title) + "',"
            + "text: '" + escape(message) + "'"
            + "});"
        );
    }

    private static String escape(String value) {
        return value == null ? "" : value.replace("'", "\\'");
    }
}
