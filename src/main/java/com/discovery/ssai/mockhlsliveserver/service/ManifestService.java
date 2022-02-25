package com.discovery.ssai.mockhlsliveserver.service;

import org.springframework.core.io.Resource;

public interface ManifestService {

  Resource serve(String fileName);
}
