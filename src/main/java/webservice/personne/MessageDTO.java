package webservice.personne;
public class MessageDTO {
    private String message;
    private int statut;

    public MessageDTO(String message,int statut) {
        this.message = message;
        this.statut=statut;
    }

    public String getMessage() {
        return message;
    }

	public int getStatut() {
		return statut;
	}

	public void setStatut(int statut) {
		this.statut = statut;
	}
}
