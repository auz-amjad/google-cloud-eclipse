/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.tools.eclipse.sdk;

import com.google.cloud.tools.appengine.cloudsdk.process.ProcessOutputLineListener;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;

/**
 * A {@link ProcessOutputLineListener} that processes output lines using a given line-processing
 * function. Drops lines for which the function returns {@code null}.
 */
public class ProcessingLineListener implements ProcessOutputLineListener {

  private final Function<String, String> lineProcessor;
  private final List<String> processedLines = new ArrayList<>();

  public ProcessingLineListener(Function<String, String> lineProcessor) {
    Preconditions.checkNotNull(lineProcessor, "line process is null");
    this.lineProcessor = lineProcessor;
  }

  @Override
  public void onOutputLine(String line) {
    String processed = lineProcessor.apply(line);
    if (processed != null) {
      processedLines.add(processed);
    }
  }

  public List<String> getProcessedLines() {
    return new ArrayList<>(processedLines);
  }
}
