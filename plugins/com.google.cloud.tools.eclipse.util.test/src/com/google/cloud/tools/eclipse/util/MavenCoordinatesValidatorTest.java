/*
 * Copyright 2016 Google Inc.
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

package com.google.cloud.tools.eclipse.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MavenCoordinatesValidatorTest {

  @Test
  public void testValidateArtifactId() {
    assertTrue(MavenCoordinatesValidator.validateArtifactId("foo"));
    assertTrue(MavenCoordinatesValidator.validateArtifactId("foo.bar"));
    assertTrue(MavenCoordinatesValidator.validateArtifactId("foo_bar"));
    assertFalse(MavenCoordinatesValidator.validateArtifactId(null));
    assertFalse(MavenCoordinatesValidator.validateArtifactId(""));
    assertFalse(MavenCoordinatesValidator.validateArtifactId("foo bar"));
  }

  @Test
  public void testValidateGroupId() {
    assertTrue(MavenCoordinatesValidator.validateGroupId("foo"));
    assertTrue(MavenCoordinatesValidator.validateGroupId("foo.bar"));
    assertTrue(MavenCoordinatesValidator.validateGroupId("foo_bar"));
    assertFalse(MavenCoordinatesValidator.validateGroupId(null));
    assertFalse(MavenCoordinatesValidator.validateGroupId(""));
    assertFalse(MavenCoordinatesValidator.validateGroupId("foo bar"));
  }

  @Test
  public void testValidateVersion() {
    assertTrue(MavenCoordinatesValidator.validateVersion("foo"));
    assertTrue(MavenCoordinatesValidator.validateVersion("foo.bar"));
    assertFalse(MavenCoordinatesValidator.validateVersion(null));
    assertFalse(MavenCoordinatesValidator.validateVersion(""));
  }
}
