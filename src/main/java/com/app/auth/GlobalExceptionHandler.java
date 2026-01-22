package com.app.auth;


import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.faces.context.ExternalContext;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;

import org.primefaces.PrimeFaces;

public class GlobalExceptionHandler extends ExceptionHandlerWrapper {

    private final ExceptionHandler wrapped;

    public GlobalExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }

    @Override
    public void handle() throws FacesException {

        Iterator<ExceptionQueuedEvent> iterator =
                getUnhandledExceptionQueuedEvents().iterator();

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();

        while (iterator.hasNext()) {

            ExceptionQueuedEvent event = iterator.next();
            ExceptionQueuedEventContext eventContext =
                    (ExceptionQueuedEventContext) event.getSource();

            Throwable exception = eventContext.getException();

            try {
                handleException(exception, context, externalContext);
            } finally {
                iterator.remove(); // très important
            }
        }

        getWrapped().handle();
    }

    private void handleException(Throwable exception,
                                 FacesContext context,
                                 ExternalContext externalContext) {

      //  Throwable root = getRootCause(exception);
        
        Throwable root = unwrap(exception);

        if (root instanceof IllegalStateException) {
            showError("Erreur métier", root.getMessage());
            return;
        }

        if (root instanceof SecurityException) {
            showError("Accès refusé", "Vous n’êtes pas autorisé à effectuer cette action");
            return;
        }

        // Exception technique
        showError(
            "Erreur technique",
            "Une erreur inattendue est survenue. Veuillez contacter l’administrateur."
        );

        // 👉 ici tu peux logger / auditer
        root.printStackTrace();
    }

    private void showError(String title, String message) {
        PrimeFaces.current().executeScript(
            "Swal.fire({"
            + "icon:'error',"
            + "title:'" + escape(title) + "',"
            + "text:'" + escape(message) + "'"
            + "});"
        );
    }



    private String escape(String value) {
        return value == null ? "" : value.replace("'", "\\'");
    }
    
    private Throwable unwrap(Throwable exception) {

        Throwable result = exception;

        while (result != null) {

            if (result instanceof IllegalStateException
                    || result instanceof SecurityException) {
                return result;
            }

            Throwable cause = result.getCause();

            if (cause == null || cause == result) {
                break;
            }

            result = cause;
        }

        return exception;
    }

}
