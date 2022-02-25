package com.discovery.ssai.mockhlsliveserver.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service("proxy-manifest")
public class ProxyManifestService implements ManifestService {

  @Override
  public Resource serve(String fileName) {
    return null;
  }
}
