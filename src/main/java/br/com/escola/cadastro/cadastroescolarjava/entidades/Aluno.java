package br.com.escola.cadastro.cadastroescolarjava.entidades;

public class Aluno extends Pessoa {
    private String matricula;
    private String serie;
    private int idTurma;
    private String tipoSanguineo;
    private String cpfDoResponsavel;
    private boolean aprovado;

    public Aluno() {}

    public Aluno(int id, String nome, String dataNascimento,
                 String matricula, String telefone, String celular,
                 String cpfDoResponsavel, String tipoSanguineo, String serie,
                 boolean aprovado, int idTurma) {
        super(id, nome, telefone, celular, dataNascimento);
        this.matricula = matricula;
        this.serie = serie;
        this.idTurma = idTurma;
        this.tipoSanguineo = tipoSanguineo;
        this.cpfDoResponsavel = cpfDoResponsavel;
        this.aprovado = aprovado;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(int idTurma) {
        this.idTurma = idTurma;
    }

    public String getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(String tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    public String getCpfDoResponsavel() {
        return cpfDoResponsavel;
    }

    public void setCpfDoResponsavel(String cpfDoResponsavel) {
        this.cpfDoResponsavel = cpfDoResponsavel;
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "id='" + getId() + '\'' +
                ", nome='" + getNome() + '\'' +
                ", dataNascimento='" + getDataNascimento() + '\'' +
                ", telefone='" + getTelefone() + '\'' +
                ", celular='" + getCelular() + '\'' +
                ", matricula='" + matricula + '\'' +
                ", serie='" + serie + '\'' +
                ", idTurma=" + idTurma +
                ", tipoSanguineo='" + tipoSanguineo + '\'' +
                ", cpfDoResponsavel='" + cpfDoResponsavel + '\'' +
                ", aprovado=" + aprovado +
                '}';
    }
}