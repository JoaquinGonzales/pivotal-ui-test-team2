package org.fundacionjala.pivotal.framework.selenium;

import java.net.MalformedURLException;
import java.net.URL;

import org.fundacionjala.pivotal.framework.util.PropertiesInfo;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.fundacionjala.pivotal.framework.util.Constants.CAPABILITY_NAME;
import static org.fundacionjala.pivotal.framework.util.Constants.HTTP_PROXY_HOST;
import static org.fundacionjala.pivotal.framework.util.Constants.HTTP_PROXY_PORT;

public class RemoteBrowser implements IDriver {

    private static final Logger LOGGER = Logger.getLogger(RemoteBrowser.class.getSimpleName());

    private static final PropertiesInfo PROPERTIES_INFO = PropertiesInfo.getInstance();

    public WebDriver initDriver() {
        System.getProperties().put(HTTP_PROXY_HOST, PROPERTIES_INFO.getProxyHost());
        System.getProperties().put(HTTP_PROXY_PORT, PROPERTIES_INFO.getProxyPort());
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(CapabilityType.BROWSER_NAME, PROPERTIES_INFO.getRemoteBrowser());
        caps.setCapability(CapabilityType.VERSION, PROPERTIES_INFO.getBrowserVersion());
        caps.setCapability(CapabilityType.PLATFORM, PROPERTIES_INFO.getPlatform());
        caps.setCapability(CAPABILITY_NAME, PROPERTIES_INFO.getRemoteTestName());
        final String sauceUrl = String.format("http://%s:%s@ondemand.saucelabs.com:80/wd/hub",
                PROPERTIES_INFO.getSauceUser(), PROPERTIES_INFO.getSauceAccessKey());
        URL url = null;
        try {
            url = new URL(sauceUrl);
        } catch (MalformedURLException e) {
            LOGGER.warn("The url is not correct" + e);
        }
        return new RemoteWebDriver(url, caps);
    }
}
