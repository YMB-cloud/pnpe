package com.app.auth;

import java.util.List;
import java.util.Set;

import com.app.entity.ProfilDTO;
import com.app.entity.Profile;
import com.app.entity.Role;
import com.app.entity.RoleDTO;

public interface ProfilService {

    Profile creer(ProfilDTO dto);
    
    Role creerRole(RoleDTO dto);

    Profile modifier(ProfilDTO dto);

    void desactiver(Long profilId);

    Profile trouverParId(Long id);

    List<Profile> lister();
    
    List<Role> listerRole();
    
    Role modifierRole(RoleDTO dto);
    
    void desactiverRole(Long roleId);
    
    Profile findById(Long id);
    
    void ajouterChildRole(RoleDTO parent,RoleDTO child);
    
    void ajouterRoles(Long profileId,  Set<Role> roleList);
    
}
