package br.com.escola.cadastro.cadastroescolarjava.entidades;

public class Disciplina {
    private int id;
    private String nome;
    private String anoLetivo;
    private boolean aprovado;
    private int idAluno;

    public Disciplina(int id, String nome, String anoLetivo,
                      boolean aprovado, int idAluno) {
        this.id = id;
        this.nome = nome;
        this.anoLetivo = anoLetivo;
        this.aprovado = aprovado;
        this.idAluno = idAluno;
    }

    public Disciplina() {}

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public String getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(String anoLetivo) {
        this.anoLetivo = anoLetivo;
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
    }

    public float calcularMedia(float nota1, float nota2,
                               float nota3, float nota4) {
        return (nota1 + nota2 + nota3 + nota4) / 4;
    }

    @Override
    public String toString() {
        return "Disciplina{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", anoLetivo='" + anoLetivo + '\'' +
                ", aprovado=" + aprovado +
                ", idAluno=" + idAluno +
                '}';
    }
}
