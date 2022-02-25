package com.discovery.ssai.mockhlsliveserver.service;

import com.discovery.ssai.mockhlsliveserver.utils.ManifestServeUtils;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

@Service("custom-manifest")
public class CustomBuiltManifestService implements ManifestService {

  private Map<String, String> fileMap;

  public CustomBuiltManifestService() {
    fileMap = ManifestServeUtils.buildCustomFileMap();
  }

  @Override
  public Resource serve(String fileName) {
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
