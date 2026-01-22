package webservice.personne;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.app.entity.Personne;

public class PersonneDTO {

    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String sexe;
    private String email;
    private String telephone;

    public PersonneDTO() {}

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public PersonneDTO(Personne p) {
        this.id = p.getId();
        this.nom = p.getNom();
        this.prenom = p.getPrenom();
        this.dateNaissance = p.getDateNaissance();
        this.sexe = p.getSexe();
        this.email = p.getEmail();
        this.telephone = p.getTelephone();
    }

    // getters & setters
}
