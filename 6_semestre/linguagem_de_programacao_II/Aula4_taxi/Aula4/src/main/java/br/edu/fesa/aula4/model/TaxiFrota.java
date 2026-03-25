package br.edu.fesa.aula4.model;

/**
 *
 * @author Diego_de_Souza
 */
public class TaxiFrota implements Frota {

    private int numero;

    public TaxiFrota() {
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public double aluguel() {
        return 100.0;
    }

    
}
