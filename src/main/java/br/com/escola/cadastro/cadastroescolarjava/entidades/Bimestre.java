package br.com.escola.cadastro.cadastroescolarjava.entidades;

public class Bimestre {
    private int id;
    private int numeroBimestre;
    private double teste;
    private double prova;
    private double pontoDeParticipacao;
    private int idDisciplina;

    public Bimestre() {}

    public Bimestre(int id, int numeroBimestre, double teste, double prova, double pontoDeParticipacao, int idDisciplina) {
        this.id = id;
        this.numeroBimestre = numeroBimestre;
        this.teste = teste;
        this.prova = prova;
        this.pontoDeParticipacao = pontoDeParticipacao;
        this.idDisciplina = idDisciplina;
    }

    public int getId() {
        return id;
    }

    public int getNumeroBimestre() {
        return numeroBimestre;
    }

    public void setNumeroBimestre(int numeroBimestre) {
        this.numeroBimestre = numeroBimestre;
    }

    public double getTeste() {
        return teste;
    }

    public void setTeste(double teste) {
        this.teste = teste;
    }

    public double getProva() {
        return prova;
    }

    public void setProva(double prova) {
        this.prova = prova;
    }

    public double getPontoDeParticipacao() {
        return pontoDeParticipacao;
    }

    public void setPontoDeParticipacao(double pontoDeParticipacao) {
        this.pontoDeParticipacao = pontoDeParticipacao;
    }

    public int getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(int idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    @Override
    public String toString() {
        return "Bimestre{" +
                "id=" + id +
                ", numeroBimestre=" + numeroBimestre +
                ", teste=" + teste +
                ", prova=" + prova +
                ", pontoDeParticipacao=" + pontoDeParticipacao +
                ", idDisciplina=" + idDisciplina +
                '}';
    }
}
