package tests;

import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import pages.LoginPages;
import suporte.Web;

import static org.junit.Assert.*;

@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "InformacoesUsuarioPageObjectTest.csv")
public class InformacoesUsuarioPageObjectTest {
    private WebDriver navegador;

    @Before
    public void setUp() {
        navegador = Web.createChrome();
    }

    @Test
    public void testAddUmaInformacaoAdicionalDoUsuario(
             @Param(name="login")String login,
             @Param(name="senha")String senha,
             @Param(name="tipo")String tipo,
             @Param(name="contato")String contato,
             @Param(name="mensagem")String mensagemEsperada
    ) {
        String textoToast = new LoginPages(navegador)
                .clickSignIn()
                .fazerLogin(login, senha)
                .clickMe()
                .clickAbaMoreDataAboutYou()
                .clickBotaoAddMoreDataAboutYou()
                .adicionarContato(tipo, contato)
                .capturarTextoToast();

        assertEquals(mensagemEsperada, textoToast);
    }

    @After
    public void tearDown() {
        navegador.quit();
    }
}
