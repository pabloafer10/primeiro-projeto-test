package tests;

import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import suporte.Generator;
import suporte.Screenshot;
import suporte.Web;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "InformacoesUsuarioTest.csv")
public class InformacoesUsuarioTest {
    private WebDriver navegador;

    @Rule
    public TestName test = new TestName();

    @Before
    public void setUp(){
        navegador = Web.createChrome();

        //Abrindo o navegador
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\pablo\\drivers\\chromedriver.exe");
        navegador = new ChromeDriver();
        navegador.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //Navegando para abrir a página taskit
        navegador.get("http://www.juliodelima.com.br/taskit");

        //Clicar no link que possui textio "Sign in"
        navegador.findElement(By.linkText("Sign in")).click();

        //Identicar formulário de login
        WebElement formularioSignInBox = navegador.findElement(By.id("signinbox"));

        //Digitar no campo com name "login" que está dentro do formulário id "Signinbox" o texto "Nome do Usuário"
        formularioSignInBox.findElement(By.name("login")).sendKeys("pabloafer10");

        //Digitar no campo com name "password" que está dentro do formulário id "Signinbox" o texto "Senha de Usuário"
        formularioSignInBox.findElement(By.name("password")).sendKeys("101010");

        //Clicar no link com name "SIGN IN"
        navegador.findElement(By.linkText("SIGN IN")).click();

        //Clicar em link que possui a class "me"
        navegador.findElement(By.className("me")).click();

        //Clicar em link que possui o texto "MORE DATA ABOUT YOU"
        navegador.findElement(By.linkText("MORE DATA ABOUT YOU")).click();
    }

    @Test
    public void testAdicionarUmaInformacaoAdicionalDoUsuario(@Param(name = "tipo")String tipo, @Param(name = "contato")String contato, @Param(name = "mensagem")String mensagemEsperada) {
        //Clicar no botão através do seu xpath //button[@data-target="addmoredata"]
        navegador.findElement(By.xpath("//button[@data-target=\"addmoredata\"]")).click();

        //Identificar a popup onde está o formulário de id addmoredata
        WebElement popupAddMoreData = navegador.findElement(By.id("addmoredata"));

        //No combo de name = "type" escolha a opção "Phone"
        WebElement campoType = popupAddMoreData.findElement(By.name("type"));
        new Select(campoType).selectByVisibleText(tipo);

        //No campo de name "contact" digitar "Número de telefone"
        popupAddMoreData.findElement(By.name("contact")).sendKeys(contato);

        //No link de text "SAVE" que está na popup
        popupAddMoreData.findElement(By.linkText("SAVE")).click();

        //Na mensagem de id "toast-container" validar que o texto "Your contact has been added!"
        WebElement mensagemPop = navegador.findElement(By.id("toast-container"));
        String mensagem = mensagemPop.getText();
        assertEquals(mensagemEsperada, mensagem);
    }

    @Test
    public void removerUmContatoDeUmUsuario() {
        //Clicar no elemento pelo seu xpath //span[text()+5511999998855]/following-sibling::a
        navegador.findElement(By.xpath("//span[text()=\"+551123455432\"]/following-sibling::a")).click();

        //Confirmar a janela javascript
        navegador.switchTo().alert().accept();

        //Validar que a mensagem apresentada foi Rest in peace, dear phone!
        WebElement mensagemPop = navegador.findElement(By.id("toast-container"));
        String mensagem = mensagemPop.getText();
        assertEquals("Rest in peace, dear phone!", mensagem);

        //Tirando print do test
        String screenshotArquivo = "C:\\Users\\pablo\\test-report\\taskit\\" + Generator.dataHoraParaArquivo() + test.getMethodName() +".png";
        Screenshot.tirar(navegador, screenshotArquivo);

        //Aguardar até 10 segubdos para que janela desapareça
        WebDriverWait aguardar = new WebDriverWait(navegador, 10);
        aguardar.until(ExpectedConditions.stalenessOf(mensagemPop));

        //Clicar no link com texto "logout"
        navegador.findElement(By.linkText("Logout")).click();
    }

    @After
    public void tearDown() {
        //Fechar navegador
        navegador.quit();
    }
}