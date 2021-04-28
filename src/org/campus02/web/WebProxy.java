package org.campus02.web;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class WebProxy {

  PageCache cache;
  int numCacheHits = 0;
  int numCacheMisses = 0;

  public WebProxy() {
    cache = new PageCache();
  }

  public WebProxy(PageCache cache) {
    this.cache = cache;
  }

  public WebPage fetch(String url) throws UrlLoaderException {
    WebPage page = null;
    try {
      page = cache.readFromCache(url);
      numCacheHits++;
    } catch (CacheMissException e) {
      numCacheMisses++;
      page = UrlLoader.loadWebPage(url);
      cache.writeToCache(page);
    }
    return page;
  }

  public String statsHits() {
    return "stats hits: "+numCacheHits;
  }

  public String statsMisses() {
    return "stats misses: "+ numCacheMisses;
  }

  public boolean writePageCacheToFile(String pathToFile) {
    try(BufferedWriter bw = new BufferedWriter(new FileWriter(pathToFile))) {
      for(Map.Entry<String,WebPage> e : cache.getCache().entrySet()) {
        bw.write(e.getKey()+";"+e.getValue().getContent());
        bw.newLine();
      }
      bw.flush();
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

}
