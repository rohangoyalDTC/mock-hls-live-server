package com.discovery.ssai.mockhlsliveserver.service;

import com.discovery.ssai.mockhlsliveserver.utils.ManifestServeUtils;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

@Service("custom-manifest")
public class CustomBuiltManifestService implements ManifestService {

  private Map<String, String> fileMap;
  private Map<String, Integer> currentPointer;
  private Map<String, Long> lastReceivedAt;
  private static final int lastWindowPointer = 7;

  public CustomBuiltManifestService() {
    fileMap = ManifestServeUtils.buildCustomFileMap();
    currentPointer = new HashMap<>();
    lastReceivedAt = new HashMap<>();
    for(String fileName : fileMap.keySet()) {
      if(fileName.contains("master")) {
        currentPointer.put(fileName, 0);
        lastReceivedAt.put(fileName, 0L);
        continue;
      }
      String baseName = fileName.substring(0, fileName.lastIndexOf('_')) + fileName.substring(fileName.lastIndexOf('.'));
      currentPointer.put(baseName, -1);
      lastReceivedAt.put(baseName, 0L);
    }
  }

  @Override
  public Resource serve(String fileName) {
    if(!fileName.contains("master")) {
      long currentTime = System.currentTimeMillis();
      int currentPointerVal = currentPointer.get(fileName);
      if(currentPointerVal < lastWindowPointer && currentTime > lastReceivedAt.get(fileName) + 5000) {
        currentPointer.put(fileName, currentPointerVal + 1);
        lastReceivedAt.put(fileName, currentTime);
      }
      fileName = fileName.replace(".", "_" + currentPointer.get(fileName) + ".");
    }
    String filePathStr = fileMap.get(fileName);
    if(filePathStr == null) {
      throw new IllegalStateException("File not found: " + fileName);
    }
    try {
      Path filePath = Paths.get(filePathStr);
      Resource resource = new UrlResource(filePath.toUri());
      if(resource.exists()) {
        return resource;
      } else {
        throw new IllegalStateException("File not found: " + fileName);
      }
    } catch (MalformedURLException ex) {
      throw new IllegalStateException("File not found: " + fileName);
    }
  }
}
