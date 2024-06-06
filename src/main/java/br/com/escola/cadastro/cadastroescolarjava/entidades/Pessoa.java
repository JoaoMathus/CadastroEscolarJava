package br.com.escola.cadastro.cadastroescolarjava.entidades;

public abstract class Pessoa {
    private int id;
    private String nome;
    private String telefone;
    private String celular;
    private String cpf;
    private String dataNascimento;

    protected Pessoa() {}

    protected Pessoa(int id, String nome, String telefone, String celular, String cpf, String dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.celular = celular;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    // Construtor sem o ID da entidade
    public Pessoa(String nome, String telefone, String celular, String cpf, String dataNascimento) {
        this.nome = nome;
        this.telefone = telefone;
        this.celular = celular;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    public int getId(){
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
