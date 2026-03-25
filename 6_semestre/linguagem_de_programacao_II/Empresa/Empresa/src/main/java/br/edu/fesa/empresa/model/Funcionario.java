/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.empresa.model;

import java.time.LocalDate;

/**
 *
 * @author pro21001967
 */
public abstract class Funcionario extends Pessoa {

    private static Integer controleDeMatricula = 0;
    private Integer matricula;
    private LocalDate contratacao;
    private Double salario = 1682.37;

    public Funcionario() {
        this.matricula = ++controleDeMatricula;
    }

    public Funcionario(String nome, LocalDate nascimento,
            LocalDate contratacao) {

        super(nome, nascimento);
        this.matricula = ++controleDeMatricula;
        this.contratacao = contratacao;
    }

    public static Integer getControleDeMatricula() {
        return controleDeMatricula;
    }

    public static void setControleDeMatricula(Integer controleDeMatricula) {
        Funcionario.controleDeMatricula = controleDeMatricula;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public LocalDate getContratacao() {
        return contratacao;
    }

    public void setContratacao(LocalDate contratacao) {
        this.contratacao = contratacao;
    }

    public Double getSalario() {
        return salario;
    }

    public abstract Double calcularSalario();
}
