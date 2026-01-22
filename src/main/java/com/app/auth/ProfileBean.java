package com.app.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.app.entity.ProfilDTO;
import com.app.entity.Profile;
import com.app.entity.Role;
import com.app.entity.RoleDTO;

@Named
@ViewScoped
public class ProfileBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProfilService profilService;
    
	private RoleDTO role = new RoleDTO();
	private RoleDTO childRole = new RoleDTO();
    private ProfilDTO profile = new ProfilDTO();
    private List<Profile> profiles;
    private List<Role> roles;
    private List<Long> selectedRoleIds;
    
    private TreeNode<Role> root;
    private TreeNode<Role>[] selectedNodes;
    
    @PostConstruct
    public void init() {
    	setProfiles(profilService.lister());
    	setRoles(profilService.listerRole());
    }
    
    private void buildTree(TreeNode<Role> parent, List<Role> roles) {
        for (Role role : roles) {
            TreeNode<Role> node = new DefaultTreeNode<>(role, parent);

            /*if (profile.getRoles().contains(role)) {
                node.setSelected(true);
            }*/

            if (role.getChildren() != null && !role.getChildren().isEmpty()) {
                buildTree(node, new ArrayList<>(role.getChildren()));
            }
        }
    }
    
    private void buildTreeForUpdate(TreeNode<Role> parent, List<Role> roles) {
        for (Role role : roles) {
            TreeNode<Role> node = new DefaultTreeNode<>(role, parent);
            System.out.println("----"+role.getId() + " "+role.getLibelle());
           /* if (profile.getRoles().contains(role)) {
                node.setSelected(true);
                System.out.println("---- existe");
            }*/
            
            for(Role ir : profile.getRoles()) {
            	if(ir.getId().equals(role.getId())) {
                    node.setSelected(true);
            	}
            }

            if (role.getChildren() != null && !role.getChildren().isEmpty()) {
            	buildTreeForUpdate(node, new ArrayList<>(role.getChildren()));
            }
        }
    }
    
    public void initProfile() {
    	profile = new ProfilDTO();
    	profile.setActif(true);
    	selectedRoleIds = new ArrayList<Long>();
    	
        root = new DefaultTreeNode<>();
        buildTree(root, roles);
    }
    
    public void initRole() {
    	role = new RoleDTO();
    	role.setActif(true);
    }
	
    public String versProfiles() {
    	//initRole();
    	//initProfile();
    	childRole = new RoleDTO();
    	childRole.setActif(true);
    	return "/secured/auth/profiles.xhtml?faces-redirect=true";
    }
    
    public void enregistrer() {
    	Profile newProfile = profilService.creer(profile);
    	 Set<Role> listRoles = new HashSet<Role>();

        if (selectedNodes != null) {
            for (TreeNode<Role> node : selectedNodes) {
            	listRoles.add(node.getData());
            }
        }

        profilService.ajouterRoles(newProfile.getId(), listRoles);
        setProfiles(profilService.lister());
        UiMessage.swalSuccess("Création de profile", "Profile enregistré");
    }
    
    public void modifierProfile() {
    	Profile p = profilService.modifier(profile);
        Set<Role> listRoles = new HashSet<Role>();

        if (selectedNodes != null) {
            for (TreeNode<Role> node : selectedNodes) {
            	listRoles.add(node.getData());
            }
        }

        profilService.ajouterRoles(p.getId(), listRoles);
        setProfiles(profilService.lister());
        UiMessage.swalSuccess("Modification de profile", "Profile modifié");
    }
    
    public void enregistrerRole() {
    	profilService.creerRole(role);
    	setRoles(profilService.listerRole());
    	UiMessage.swalSuccess("Création de role", "Role enregistré");
    }
    
    public void modifierRole() {
    	profilService.modifierRole(role);
    	setRoles(profilService.listerRole());
    	UiMessage.swalSuccess("Modification de role", "Role Modifié");
    }
    
    public void retirerRole(Role r) {
    	profilService.desactiverRole(r.getId());
    	setRoles(profilService.listerRole());
    	UiMessage.swalSuccess("Désactivation de role", "Role désactivé");
    }
    
    public void chargerProfileToUpdate(Profile p) {
    	
    	profile.setId(p.getId());
    	profile.setLibelle(p.getLibelle());
    	profile.setCode(p.getCode());
    	profile.setActif(p.getActif());
    	profile.setRoles(p.getRoles());
    	
    	System.out.println("liste de roles : "+profile.getRoles().size());
    	
    	root = new DefaultTreeNode<>();
    	buildTreeForUpdate(root, roles);
    }
    
    public void chargerRoleToUpdate(Role p) {
    	
    	role.setId(p.getId());
    	role.setLibelle(p.getLibelle());
    	role.setCode(p.getCode());
    	role.setRedirection(p.getRedirection());
    	role.setActif(p.getActif());
    	if(p.getParent() != null) {
    		role.setRoleParentId(p.getParent().getId());
    	}
    	
    }
    
    public void chargerRoleToAddChild(Role p) {
    	
    	role.setId(p.getId());
    	role.setLibelle(p.getLibelle());
    	role.setCode(p.getCode());
    	
    	childRole = new RoleDTO();
    	childRole.setActif(true);
    }
    
    public void enregistrerChildRole() {
    	profilService.ajouterChildRole(role, childRole);
    	setRoles(profilService.listerRole());
    	UiMessage.swalSuccess("Création de sous role", "Sous role enregistré");
    }


	public ProfilDTO getProfile() {
		return profile;
	}


	public void setProfile(ProfilDTO profile) {
		this.profile = profile;
	}


	public List<Profile> getProfiles() {
		return profiles;
	}


	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public RoleDTO getRole() {
		return role;
	}

	public void setRole(RoleDTO role) {
		this.role = role;
	}

	public RoleDTO getChildRole() {
		return childRole;
	}

	public void setChildRole(RoleDTO childRole) {
		this.childRole = childRole;
	}

	public List<Long> getSelectedRoleIds() {
		return selectedRoleIds;
	}

	public void setSelectedRoleIds(List<Long> selectedRoleIds) {
		this.selectedRoleIds = selectedRoleIds;
	}

	public TreeNode<Role> getRoot() {
		return root;
	}

	public void setRoot(TreeNode<Role> root) {
		this.root = root;
	}

	public TreeNode<Role>[] getSelectedNodes() {
		return selectedNodes;
	}

	public void setSelectedNodes(TreeNode<Role>[] selectedNodes) {
		this.selectedNodes = selectedNodes;
	}

}
