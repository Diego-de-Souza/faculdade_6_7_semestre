/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.empresa.model;

/**
 *
 * @author pro21001967
 */
public class Desenvolvedor extends Funcionario {

    private Double percentual = 13.60;

    @Override
    public Double calcularSalario() {
        return super.getSalario() * percentual;
    }
}
