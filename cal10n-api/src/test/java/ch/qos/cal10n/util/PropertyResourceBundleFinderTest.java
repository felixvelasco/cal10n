/*
 * Copyright (c) 2009 QOS.ch All rights reserved.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS  IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package ch.qos.cal10n.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Test;

public class PropertyResourceBundleFinderTest {
  ResourceBundle rb;

  @Test
  public void smoke() throws IOException {
    rb = PropertyResourceBundleFinder.getBundle(this.getClass().getClassLoader(),
        "colors", Locale.FRENCH);
    assertEquals("les roses sont rouges", rb.getString("RED"));
  }

  @Test
  public void withCountry() throws IOException {
    rb = PropertyResourceBundleFinder.getBundle(this.getClass().getClassLoader(),
        "colors", Locale.FRENCH);
    assertEquals("les roses sont rouges", rb.getString("RED"));

    rb = PropertyResourceBundleFinder.getBundle(this.getClass().getClassLoader(),
        "colors", Locale.FRANCE);
    assertEquals("les roses sont rouges, et alors?", rb.getString("RED"));
  }

  @Test
  public void inDirectory() throws IOException {
    rb = PropertyResourceBundleFinder.getBundle(this.getClass().getClassLoader(),
        "foobar/sample", Locale.ENGLISH);
    assertEquals("A is the first letter of the alphabet", rb.getString("A"));

    rb = PropertyResourceBundleFinder.getBundle(this.getClass().getClassLoader(),
        "foobar.sample", Locale.ENGLISH);
    assertEquals("A is the first letter of the alphabet", rb.getString("A"));
  }
  
  @Test
  public void urlToFile() {
    ClassLoader classLoader = this.getClass().getClassLoader();
    String resourceCandidate =  "colors" + "_" + "en" + ".properties";
    URL url = classLoader.getResource(resourceCandidate);
    assertNotNull("the problem is in this test, not the code tested", url);

    File file =  PropertyResourceBundleFinder.urlToFile(url);
    assertNotNull(file);
  }
  
  @Test
  public void httpUrlToFile() throws MalformedURLException {
    URL url = new URL("http://www.xyz.com");
    File file =  PropertyResourceBundleFinder.urlToFile(url);
    assertNull(file);
  }
}