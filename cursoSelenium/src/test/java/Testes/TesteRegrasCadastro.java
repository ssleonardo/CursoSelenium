package Testes;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import core.DSL;

@RunWith(Parameterized.class)
public class TesteRegrasCadastro {

    private WebDriver driver;
    private DSL dsl;
    private CampoTreinamentoPage page;

    @Parameter
    public String nome;
    @Parameter(value = 1)
    public String sobrenome;
    @Parameter(value = 2)
    public String sexo;
    @Parameter(value = 3)
    public List<String> comidas;
    @Parameter(value = 4)
    public String [] esportes;
    @Parameter(value = 5)
    public String msg;


    @Before
    public void iniciar(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("file://" + System.getProperty("user.dir") + "/src/resource/componentes.html");
        dsl = new DSL(driver);
        page = new CampoTreinamentoPage(driver);
    }

    @After
    public void finaliza(){
        driver.quit();
    }

    @Parameters
    public static Collection<Object[]> getCollection(){
        return Arrays.asList(new Object[][] {
            {"","","",Arrays.asList(),new String[]{},"Nome eh obrigatorio"},
            {"Leonardo","","",Arrays.asList(),new String[]{},"Sobrenome eh obrigatorio"},
            {"Leonardo","Santos","",Arrays.asList(),new String[]{},"Sexo eh obrigatorio"},
            {"Leonardo","Santos","Masculino",Arrays.asList("Carne", "Vegetariano"),new String[]{},"Tem certeza que voce eh vegetariano?"},
            {"Leonardo","Santos","Masculino",Arrays.asList("Carne"),new String[]{"Futebol","O que eh esporte?"},"Voce faz esporte ou nao?"},
        });
    }

    @Test
    public void deveValidarRegrasCadastro(){
        page.setNome(nome);
        page.setSobrenome(sobrenome);
        if(sexo.equals("Masculino")){
            page.setSexoMasculino();
        } 
        if(sexo.equals("Feminino")){
            page.setSexoFeminino();
        }
        if (comidas.contains("Carne")) page.setComidaCarne();
        if (comidas.contains("Frango")) page.setComidaFrango();
        if (comidas.contains("Pizaa")) page.setComidaPizza();;
        if (comidas.contains("Vegetariano")) page.setComidaVegetariano();
        page.setEsporte(esportes);
        page.cadastrar();
        System.out.println(msg);
        Assert.assertEquals(msg, dsl.alertaObterTextoEAceita());
    }
}