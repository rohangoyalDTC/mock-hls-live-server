package com.discovery.ssai.mockhlsliveserver.controller;

import com.discovery.ssai.mockhlsliveserver.service.ManifestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ManifestController {

  private static Logger LOGGER = LoggerFactory.getLogger(ManifestController.class);

  private ManifestService customManifestService;
  private ManifestService proxyManifestService;

  public ManifestController(
      @Qualifier("custom-manifest") ManifestService customManifestService,
      @Qualifier("proxy-manifest") ManifestService proxyManifestService
  ) {
    this.customManifestService = customManifestService;
    this.proxyManifestService = proxyManifestService;
  }

  @GetMapping("/custom/{fileName}")
  public ResponseEntity<Resource> getCustomManifest(@PathVariable("fileName") String fileName) {
    Resource resource = customManifestService.serve(fileName);

    String contentType = "application/x-mpegURL";

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(contentType))
        .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "https://players.akamai.com")
        .header(
            HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "master.m3u8" + "\"")
        .body(resource);

  }

}
