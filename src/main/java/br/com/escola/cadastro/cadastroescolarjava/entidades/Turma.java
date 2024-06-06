package br.com.escola.cadastro.cadastroescolarjava.entidades;

public class Turma {
    private int id;
    private String numeroDaSala;
    private String serie;
    private int capacidade;
    private int idProfessor;

    public Turma() {}

    public Turma(int id, int capacidade, String serie, String numeroDaSala, int idProfessor) {
        this.id = id;
        this.numeroDaSala = numeroDaSala;
        this.serie = serie;
        this.capacidade = capacidade;
        this.idProfessor = idProfessor;
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

    @Override
    public String toString() {
        return "Turma{" +
                "id=" + id +
                ", numeroDaSala='" + numeroDaSala + '\'' +
                ", serie='" + serie + '\'' +
                ", capacidade=" + capacidade +
                ", idProfessor=" + idProfessor +
                '}';
    }
}
