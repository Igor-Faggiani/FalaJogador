package fala.jogador.desempenho;

public class UtilIMC {

    public double calcularIMC(double peso, double altura) {
        if (peso <= 0 || altura <= 0) {
            throw new IllegalArgumentException("Peso e altura devem ser positivos.");
        }
        double imc = peso / (altura * altura);
        return Math.round(imc * 100.0) / 100.0;
    }

    public String classificarIMC(double imc) {
        if (imc < 18.5) return "Abaixo do peso";
        if (imc < 25.0) return "Peso normal";
        if (imc < 30.0) return "Sobrepeso";
        return "Obesidade";
    }
}
