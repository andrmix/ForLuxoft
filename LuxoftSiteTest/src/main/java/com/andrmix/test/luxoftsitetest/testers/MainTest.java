package com.andrmix.test.luxoftsitetest.testers;

import com.andrmix.test.luxoftsitetest.utils.FileSearcher;
import com.andrmix.test.luxoftsitetest.utils.PdfScaner;
import com.andrmix.test.luxoftsitetest.utils.TestUnit;
import com.andrmix.test.luxoftsitetest.utils.XlsParser;
import com.andrmix.test.luxoftsitetest.utils.exceptions.CheckTitleException;
import com.andrmix.test.luxoftsitetest.utils.exceptions.DownloadImgException;
import com.andrmix.test.luxoftsitetest.utils.exceptions.NotFoundElementException;
import com.andrmix.test.luxoftsitetest.utils.report.Report;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Класс теста
 *
 * @author andrmix
 */
public class MainTest {

    private WebDriver driver;
    private List<TestUnit> testUnits;
    private Report report;
    private Date start;
    private String orgName;
    private int imgNumber = 0;

    /**
     * Подготовка к тесту
     */
    @BeforeMethod
    public void prepareTest() {
        String userDir = System.getProperty("user.dir");

        //подключение драйвера Chrome
        System.setProperty("webdriver.chrome.driver", userDir + "\\browser_drivers\\chromedriver.exe");

        //создание папки для отчета
        String downloadFilepath = "target\\report\\";
        File folder = new File(downloadFilepath);
        if (folder.exists()) {
            for (File f : folder.listFiles()) {
                f.delete();
            }
            folder.delete();
            folder.mkdir();
        } else {
            folder.mkdir();
        }

        //отключение плагина PDF Viewer
        Map<String, Object> plugin = new HashMap<String, Object>();
        plugin.put("enabled", false);
        plugin.put("name", "Chrome PDF Viewer");

        //задание папки для загрузок по умолчанию
        Map<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", folder.getAbsolutePath());
        chromePrefs.put("plugins.plugins_list", Arrays.asList(plugin));

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("--test-type");

        //приминение настроек профиля
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(ChromeOptions.CAPABILITY, options);

        //создание драйвера, задание таймаутов
        driver = new ChromeDriver(cap);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);

        //парсинг XLS-файла параметров
        System.out.println("Parsing XLS...");
        testUnits = XlsParser.parse(userDir + "\\params\\test_params.xls");

        //создание пустого отчета
        report = new Report();
    }

    /**
     * Запуск теста
     */
    @Test
    public void startTest() {
        String att = "";
        String cause = "";

        //окно на весь экран
        driver.manage().window().maximize();

        //обработка элементов теста
        for (TestUnit testUnit : testUnits) {
            try {
                //задание времени начала теста элемента
                start = new Date();
                switch (testUnit.getOperation()) {
                    //открыть сайт
                    case "open_site":
                        driver.get(testUnit.getParam());
                        break;

                    //проверить сайт
                    case "check_title":
                        if (!driver.getTitle().contains(testUnit.getParam())) {
                            throw new CheckTitleException();
                        }
                        break;

                    //нажатие по элементу
                    case "click":
                        findElementWithSleep(By.xpath(testUnit.getCode()), Integer.parseInt(testUnit.getWait())).click();
                        break;

                    //нажатие по элементу (с параметрами)
                    case "click_param":
                        findElementWithSleep(By.xpath(testUnit.getCode().replace("param", testUnit.getParam())), Integer.parseInt(testUnit.getWait())).click();
                        break;

                    //отправка данных формы
                    case "submit":
                        findElementWithSleep(By.xpath(testUnit.getCode()), Integer.parseInt(testUnit.getWait())).submit();
                        break;

                    //отправка данных формы (с параметрами)
                    case "submit_param":
                        findElementWithSleep(By.xpath(testUnit.getCode().replace("param", testUnit.getParam())), Integer.parseInt(testUnit.getWait())).submit();
                        break;

                    //ввод символов
                    case "enter_simbols":
                        findElementWithSleep(By.xpath(testUnit.getCode()), Integer.parseInt(testUnit.getWait())).sendKeys(testUnit.getParam());
                        break;

                    //загрузка картинки
                    case "download_img":
                        int param = Integer.parseInt(testUnit.getParam()) + 1;
                        String src = findElementWithSleep(By.xpath(testUnit.getCode().replace("param", String.valueOf(param))), Integer.parseInt(testUnit.getWait())).getAttribute("src");
                        att = downloadImage(src);
                        break;

                    //поиск текста в PDF
                    case "search_in_pdf":
                        String path = verifyFileInDir("target\\report\\", Integer.parseInt(testUnit.getWait()));
                        if (new PdfScaner().haveString(path, testUnit.getParam())) {
                            cause = "Строка присутствует";
                        } else {
                            cause = "Строка отсутствует";
                        }
                        break;
                }

                //запись в отчет
                report.addLine(start, new Date(), testUnit, "OK", cause, att);
                att = "";
                cause = "";

            } catch (NotFoundElementException e) {
                //не найден элемент
                stopOnError(testUnit, e.getMESSAGE());
                if (testUnit.getActionOnError().equals("stop")) {
                    break;
                }

            } catch (CheckTitleException e) {
                //проверка сайта не прошла
                stopOnError(testUnit, e.getMESSAGE());
                if (testUnit.getActionOnError().equals("stop")) {
                    break;
                }

            } catch (DownloadImgException e) {
                //ошибка при загрузке картинки
                stopOnError(testUnit, e.getMESSAGE());
                if (testUnit.getActionOnError().equals("stop")) {
                    break;
                }

            } catch (WebDriverException e) {
                //ошибка WebDriver'а
                stopOnError(testUnit, "Элемент не найден");
                if (testUnit.getActionOnError().equals("stop")) {
                    break;
                }
            }
        }
    }

    /**
     * Окончание теста
     */
    @AfterMethod
    public void finishTest() {
        //генерация отчета
        report.generate("target\\report\\report_" + this.getClass().getName() + ".html");

        //открытие отчета
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(new File("target\\report\\report_" + this.getClass().getName() + ".html"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Создание скриншота
     *
     * @return путь к снимку экрана
     */
    private String makeScreenshot() {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File jpgFile = null;
        int i = 0;
        try {
            jpgFile = new File("target\\report\\scrn_" + ++imgNumber + ".jpg");
            FileUtils.copyFile(scrFile, jpgFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jpgFile.getAbsolutePath();
    }

    /**
     * Поиск элемента на странице с задержкой в милисекундах
     *
     * @param by критерий поиска
     * @param sleep задержка в милисекундах
     * @return найденный элемент
     * @throws NotFoundElementException
     */
    private WebElement findElementWithSleep(By by, int sleep) throws NotFoundElementException {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (driver.findElements(by).isEmpty()) {
            throw new NotFoundElementException();
        }
        return driver.findElement(by);
    }

    /**
     * Загрузка картинки
     *
     * @param url URL картинки
     * @return путь к сохраненной картинке
     * @throws DownloadImgException
     */
    private String downloadImage(String url) throws DownloadImgException {
        BufferedImage img = null;
        File outputfile = null;
        try {
            img = ImageIO.read(new URL(url));
            if (img == null) {
                throw new DownloadImgException();
            }
            outputfile = new File("target\\report\\" + ++imgNumber + ".jpg");
            ImageIO.write(img, "jpg", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputfile.getAbsolutePath();
    }

    /**
     * Остановка теста при ошибке
     *
     * @param testUnit элемент теста
     * @param cause причина
     */
    private void stopOnError(TestUnit testUnit, String cause) {
        String att;
        //создание скриншота
        att = makeScreenshot();

        //если в параметрах элемента теста в действиях при ошибке
        //указано "stop" - остановка теста, иначе продолжать
        if (testUnit.getActionOnError().equals("stop")) {
            driver.close();
            driver.quit();
        }

        //запись данных о ошибке в отчет
        report.addLine(start, new Date(), testUnit, "ERROR", cause, att);
    }

    /**
     * Проверка на существование PDF-файла
     *
     * @param dirPath директория поиска
     * @param sleep задержка в милисекундах
     * @return путь к PDF-файлу
     * @throws NotFoundElementException
     */
    private String verifyFileInDir(String dirPath, int sleep) throws NotFoundElementException {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        File file = new File(dirPath);
        String lst[] = file.list(new FileSearcher(".pdf"));
        if (lst.length > 0) {
            return dirPath + lst[0];
        } else {
            throw new NotFoundElementException();
        }
    }
}
