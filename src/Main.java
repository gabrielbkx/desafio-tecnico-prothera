import entities.Funcionario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1621.00");

    public static void main(String[] args) {

        List<Funcionario> funcionarios = new ArrayList<>(List.of(
                criarFuncionario("Maria", "18/10/2000", "2009.44", "Operador"),
                criarFuncionario("João", "12/05/1990", "2284.38", "Operador"),
                criarFuncionario("Caio", "02/05/1961", "9836.14", "Coordenador"),
                criarFuncionario("Miguel", "14/10/1988", "19119.88", "Diretor"),
                criarFuncionario("Alice", "05/01/1995", "2234.68", "Recepcionista"),
                criarFuncionario("Heitor", "19/11/1999", "1581.44", "Operador"),
                criarFuncionario("Arthur", "31/03/1993", "4071.84", "Contador"),
                criarFuncionario("Laura", "08/07/1994", "3017.45", "Gerente"),
                criarFuncionario("Heloísa", "24/05/2003", "1606.85", "Eletricista"),
                criarFuncionario("Helena", "02/09/1996", "2799.93", "Gerente")));

        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));
        funcionarios.forEach(System.out::println);
        funcionarios.forEach(funcionario -> funcionario.aumentarSalario(new BigDecimal("0.10")));
        System.out.println();


        Map<String,List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println(funcao + ":");
            lista.forEach(funcionario -> System.out.println("  " + funcionario));
        });


        System.out.println();
        System.out.println("Aniversariantes no mês 10 e 12:");
        funcionarios.stream()
                .filter(funcionario -> {
                    int mes = funcionario.getDataNascimento().getMonthValue();
                    return mes == 10 || mes == 12;
                })
                .forEach(System.out::println);

        System.out.println();
        System.out.println("Funcionários com a maior idade");
        funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .ifPresent(funcionario -> System.out.println(
                        funcionario.getNome() + " - " + funcionario.getIdade() + " anos"));


        System.out.println();
        System.out.println("Funcionários por ordem alfabética:");
        funcionarios.sort(Comparator.comparing(Funcionario::getNome));
        funcionarios.forEach(System.out::println);


        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println();
        System.out.println("Total salários" + Funcionario.formatarValor(totalSalarios));


        System.out.println();
        System.out.println("Salários minimos por funcionarios");
        funcionarios.forEach(funcionario -> {
            BigDecimal quantidade = funcionario.getSalario()
                    .divide(SALARIO_MINIMO, 2, RoundingMode.HALF_UP);
            System.out.println(funcionario.getNome() + " - " + Funcionario.formatarValor(quantidade) + " salários mínimos");
        });
    }

    // Metodo auxiliar para criar os funcionarios
    private static Funcionario criarFuncionario(String nome, String dataNascimento, String salario, String funcao) {
        return new Funcionario(nome, LocalDate.parse(dataNascimento, FORMATO_DATA), new BigDecimal(salario), funcao);
    }
}
