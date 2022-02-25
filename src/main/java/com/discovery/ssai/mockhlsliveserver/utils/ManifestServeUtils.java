package com.discovery.ssai.mockhlsliveserver.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.springframework.util.ResourceUtils;

public class ManifestServeUtils {

  public static Map<String, String> buildCustomFileMap() {
    try {
      Map<String, String> fileMap = new HashMap<>();
      fileMap.put("master.m3u8", ResourceUtils.getFile("classpath:master.m3u8").getAbsolutePath());
      fileMap.put("prog_index.m3u8", ResourceUtils.getFile("classpath:prog_index.m3u8").getAbsolutePath());
      fileMap.put("audio_index.m3u8", ResourceUtils.getFile("classpath:audio_index.m3u8").getAbsolutePath());
      fileMap.put("subtitles_index.m3u8", ResourceUtils.getFile("classpath:subtitles_index.m3u8").getAbsolutePath());
      fileMap.put("iframe_index.m3u8", ResourceUtils.getFile("classpath:iframe_index.m3u8").getAbsolutePath());

      return fileMap;
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

}
