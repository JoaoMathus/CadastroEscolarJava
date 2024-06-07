package br.com.escola.cadastro.cadastroescolarjava.entidades;

public class Turma {
    private int id;
    private String numeroDaSala;
    private String serie;
    private int capacidade;
    private int idProfessor;
    private String nomeProfessor;

    public Turma() {}

    public Turma(int id, int capacidade, String numeroDaSala, String serie, int idProfessor, String nomeProfessor) {
        this.id = id;
        this.capacidade = capacidade;
        this.numeroDaSala = numeroDaSala;
        this.serie = serie;
        this.idProfessor = idProfessor;
        this.nomeProfessor = nomeProfessor;
    }

    public Turma(String numeroDaSala, String serie, int idProfessor, String nomeProfessor) {
        this.numeroDaSala = numeroDaSala;
        this.serie = serie;
        this.capacidade = 30;
        this.idProfessor = idProfessor;
        this.nomeProfessor = nomeProfessor;
    }

    public Turma(int id, int capacidade, String numeroDaSala, String serie, int idProfessor) {
        this.id = id;
        this.capacidade = capacidade;
        this.numeroDaSala = numeroDaSala;
        this.serie = serie;
        this.idProfessor = idProfessor;
        this.nomeProfessor = "Sem professor";
    }

    public int getId() {
        return id;
    }

    public String getNumeroDaSala() {
        return numeroDaSala;
    }

    public void setNumeroDaSala(String numeroDaSala) {
        this.numeroDaSala = numeroDaSala;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(int idProfessor) {
        this.idProfessor = idProfessor;
    }

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }

    @Override
    public String toString() {
        return "Turma{" +
                "id=" + id +
                ", numeroDaSala='" + numeroDaSala + '\'' +
                ", serie='" + serie + '\'' +
                ", capacidade='" + capacidade + '\'' +
                ", idProfessor='" + idProfessor + '\'' +
                ", nomeProfessor='" + nomeProfessor + '\'' +
                '}';
    }
}
