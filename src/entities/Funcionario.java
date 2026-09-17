package entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class Funcionario extends Pessoa{

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DecimalFormat FORMATO_SALARIO =
            new DecimalFormat("#,##0.00", new DecimalFormatSymbols(Locale.of("pt", "BR")));

    BigDecimal salario;
    String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public void aumentarSalario(BigDecimal percentual) {
        salario = salario.multiply(BigDecimal.ONE.add(percentual)).setScale(2, RoundingMode.HALF_UP);
    }

    public String getDataNascimentoFormatada() {
        return dataNascimento.format(FORMATO_DATA);
    }

    public String getSalarioFormatado() {
        return formatarValor(salario);
    }

    public static String formatarValor(BigDecimal valor) {
        return FORMATO_SALARIO.format(valor);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Funcionario that = (Funcionario) o;
        return Objects.equals(salario, that.salario) && Objects.equals(funcao, that.funcao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), salario, funcao);
    }

    @Override
    public String toString() {
        return nome + " - " + getDataNascimentoFormatada() + " - " + getSalarioFormatado() + " - " + funcao;
    }
}
