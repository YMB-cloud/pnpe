package com.app.auth;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;

import com.app.entity.Role;

@Named
@SessionScoped // ou @SessionScoped si menu dépend du user
public class MenuBean implements Serializable {

    private MenuModel model;

    @Inject
    private ProfilService profilService;

    @Inject
    private SecurityContext securityContext;

    @PostConstruct
    public void init() {
        model = new DefaultMenuModel();
        List<Role> rootRoles = profilService.listerRole();

        for (Role role : rootRoles) {
            model.getElements().add(buildMenu(role));
        }
    }
    
    private MenuElement buildMenu(Role role) {

        // CAS 1 : rôle avec sous-rôles → submenu
        //if (role.getChildren() != null && !role.getChildren().isEmpty()) {
    	if (role.getParent() == null) {

            DefaultSubMenu subMenu = DefaultSubMenu.builder()
                    .label(role.getLibelle())
                    //.icon("pi pi-folder")
                    .expanded(true)
                    .rendered(securityContext.hasRole(role.getCode()) && role.getActif())
                    .build();

            for (Role child : role.getChildren()) {
                subMenu.getElements().add(buildMenu(child));
            }

            return subMenu;
        }

        // CAS 2 : rôle feuille → menuitem
        DefaultMenuItem item = DefaultMenuItem.builder()
                .value(role.getLibelle())
                //.icon("pi pi-circle-on")
                .command("#{"+role.getRedirection()+"}") // ex: "/secured/users/list.xhtml"
                .rendered(securityContext.hasRole(role.getCode()) && role.getActif())
                .build();

        return item;
    }
    
    


    public MenuModel getModel() {
        return model;
    }
    
    
}
