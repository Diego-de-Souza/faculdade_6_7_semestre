/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package br.edu.fesa.empresa;

import br.edu.fesa.empresa.model.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author pro21001967
 */
public class Empresa {

    public static void main(String[] args) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MMMM/YYYY");

        ArrayList<Funcionario> funcionarios = new ArrayList();
        funcionarios.add(new Gerente());
        funcionarios.add(new AuxiliarDeLimpeza());
        funcionarios.add(new AuxiliarDeLimpeza());
        funcionarios.add(new Analista());
        funcionarios.add(new Analista());
        funcionarios.add(new Desenvolvedor());
        funcionarios.add(new Desenvolvedor());
        funcionarios.add(new Desenvolvedor());
        
        for (Funcionario funcionario : funcionarios) {
            System.out.println(funcionario.calcularSalario());
        }
    }
}
