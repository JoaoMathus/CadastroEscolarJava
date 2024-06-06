package br.com.escola.cadastro.cadastroescolarjava.entidades;

public class Aluno extends Pessoa {
    private String matricula;
    private int idTurma;
    private String tipoSanguineo;
    private String cpfDoResponsavel;

    public Aluno() {}

    public Aluno(int id, String nome, String dataNascimento, String matricula,
                 String telefoneDoResponsavel, String celularDoResponsavel,
                 String cpf, String cpfDoResponsavel, String tipoSanguineo, int idTurma) {
        super(id, nome, telefoneDoResponsavel, celularDoResponsavel, cpf, dataNascimento);
        this.matricula = matricula;
        this.idTurma = idTurma;
        this.tipoSanguineo = tipoSanguineo;
        this.cpfDoResponsavel = cpfDoResponsavel;
    }

    public Aluno(String nome, String telefone, String celular, String cpf,
                 String dataNascimento, String matricula, int idTurma, String tipoSanguineo,
                 String cpfDoResponsavel) {
        super(nome, telefone, celular, cpf, dataNascimento);
        this.matricula = matricula;
        this.idTurma = idTurma;
        this.tipoSanguineo = tipoSanguineo;
        this.cpfDoResponsavel = cpfDoResponsavel;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
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

    @Override
    public String toString() {
        return "Aluno{" +
                "id='" + getId() + '\'' +
                ", nome='" + getNome() + '\'' +
                ", dataNascimento='" + getDataNascimento() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                ", telefoneDoResponsavel='" + getTelefone() + '\'' +
                ", celularDoResponsavel='" + getCelular() + '\'' +
                ", matricula='" + matricula + '\'' +
                ", idTurma=" + idTurma +
                ", tipoSanguineo='" + tipoSanguineo + '\'' +
                ", cpfDoResponsavel='" + cpfDoResponsavel + '\'' +
                '}';
    }
}