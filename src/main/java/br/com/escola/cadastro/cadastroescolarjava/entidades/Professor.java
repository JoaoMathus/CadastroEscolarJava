package br.com.escola.cadastro.cadastroescolarjava.entidades;

public class Professor extends Pessoa {
    public Professor(int id, String nome, String telefone, String celular, String dataNascimento,
                     String cpf) {
        super (id, nome, telefone, celular, cpf, dataNascimento);
    }

    public Professor(String nome, String telefone, String celular, String cpf, String dataNascimento) {
        super(nome, telefone, celular, cpf, dataNascimento);
    }

    @Override
    public String toString() {
        return "Professor{" +
                "nome='" +getNome() + '\'' +
                ", telefone='" + getTelefone() + '\'' +
                ", celular='" + getCelular() + '\'' +
                ", dataNascimento='" + getDataNascimento() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                '}';
    }
}
