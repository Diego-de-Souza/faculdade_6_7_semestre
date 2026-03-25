package br.edu.fesa.empresa.model;


import br.edu.fesa.empresa.model.Funcionario;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author pro21001967
 */
public class AuxiliarDeLimpeza extends Funcionario {

    private Double percentual = 1.72;

    @Override
    public Double calcularSalario() {
        return super.getSalario() * percentual;
    }
}
