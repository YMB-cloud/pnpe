package com.app.auth;


import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.mindrot.jbcrypt.BCrypt;

import com.app.entity.Utilisateur;

@Named
@ViewScoped
public class ChangePasswordBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String currentPassword;
    private String newPassword;
    private String confirmPassword;

    @Inject
    private UtilisateurService utilisateurService;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private AuditService auditService;

    public void updatePassword() {

        Utilisateur user = securityContext.getUtilisateur();

        if (user == null) {
            throw new SecurityException("Session expirée");
        }

        if (!BCrypt.checkpw(currentPassword, user.getPassword())) {
            throw new IllegalStateException("Mot de passe actuel incorrect");
        }

        if (currentPassword.equals(newPassword)) {
            throw new IllegalStateException("Le nouveau mot de passe doit être différent");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalStateException("La confirmation ne correspond pas");
        }

        String hashed = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        utilisateurService.updatePassword(user.getId(), hashed);

        auditService.log(
            "CHANGE_PASSWORD", user,"Modification du mot de passe"
        );

        UiMessage.swalSuccess(
            "Mise à jour du mot de passe",
            "Mot de passe mis à jour avec succès"
        );

        clear();
    }

    private void clear() {
        currentPassword = null;
        newPassword = null;
        confirmPassword = null;
    }

    /* getters & setters */

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
