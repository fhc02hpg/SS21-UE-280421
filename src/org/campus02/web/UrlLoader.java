package org.campus02.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlLoader {

  public static WebPage loadWebPage(String url) throws UrlLoaderException {
    try {
      URL weburl = new URL(url);
      try(BufferedReader br = new BufferedReader(
          new InputStreamReader(weburl.openStream()))) {
        String content = "";
        String line;
        while((line=br.readLine())!=null) {
          content+=line;
        }
        return new WebPage(url,content);
      } catch (IOException e) {
        throw new UrlLoaderException("error loading url",e);
      }
    } catch (MalformedURLException e) {
      throw new UrlLoaderException("error loading url",e);
    }

  }

}
