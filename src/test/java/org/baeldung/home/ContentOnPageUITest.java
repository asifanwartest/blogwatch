package org.baeldung.home;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.baeldung.config.MainConfig;
import org.baeldung.site.base.SitePage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { MainConfig.class })
public class ContentOnPageUITest {

    @Autowired
    SitePage page;

    @Test
    public final void whenPageLoads_thenContentDivExists() {
        Stream<String> URLs = null;
        try {
            
            File file = new File(getClass().getClassLoader().getResource("url-list-to-check-content.txt").getPath());
            URLs = Files.lines(Paths.get(file.getAbsolutePath()));
            URLs.forEach(URL -> {
                page.setPageURL(page.getBaseURL() + URL);
                page.openNewWindowAndLoadPage();
                assertTrue(page.findContentDiv().isDisplayed());
                page.quiet();
            });
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            page.quiet();
            Assert.fail();           
        } finally {
            if (null != URLs) {
                URLs.close();
            }
        }
    }

}
