package br.com.escola.cadastro.cadastroescolarjava.entidades;

public class Professor extends Pessoa {
    private String cpf;

    public Professor(int id, String nome, String telefone, String celular, String dataNascimento,
                     String cpf) {
        super (id, nome, telefone, celular, dataNascimento);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "cpf='" + cpf + '\'' +
                '}';
    }
}
