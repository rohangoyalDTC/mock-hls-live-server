package com.discovery.ssai.mockhlsliveserver.utils;

import java.util.HashMap;
import java.util.Map;
import org.springframework.util.ResourceUtils;

public class ManifestServeUtils {

  public static Map<String, String> buildCustomFileMap() {
    try {
      Map<String, String> fileMap = new HashMap<>();
      fileMap.put("master.m3u8",
          ResourceUtils.getFile("classpath:master.m3u8").getAbsolutePath());
      for (int i = 0; i <= 7; ++i) {
        fileMap.put(getFilePath("prog_index", i),
            ResourceUtils.getFile(getFileClassPath("video", "prog_index", i)).getAbsolutePath());
        fileMap.put(getFilePath("audio_index", i),
            ResourceUtils.getFile(getFileClassPath("audio", "audio_index", i)).getAbsolutePath());
        fileMap.put(getFilePath("subtitles_index", i),
            ResourceUtils.getFile(getFileClassPath("subtitles", "subtitles_index", i))
                .getAbsolutePath());
        fileMap.put(getFilePath("iframe_index", i),
            ResourceUtils.getFile(getFileClassPath("iframes", "iframe_index", i)).getAbsolutePath());
      }
      return fileMap;
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  private static String getFileClassPath(String folder, String fileBaseName, int index) {
    return "classpath:" + folder + "/" + getFilePath(fileBaseName, index);
  }

  private static String getFilePath(String fileBaseName, int index) {
    return fileBaseName + "_" + index + ".m3u8";
  }

}
