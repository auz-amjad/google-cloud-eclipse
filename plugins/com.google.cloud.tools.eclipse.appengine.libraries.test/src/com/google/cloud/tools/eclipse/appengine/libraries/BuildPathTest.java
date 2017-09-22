/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.tools.eclipse.appengine.libraries;

import com.google.cloud.tools.eclipse.appengine.libraries.model.Library;
import com.google.cloud.tools.eclipse.test.util.project.TestProjectCreator;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jst.common.project.facet.core.JavaFacet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class BuildPathTest {

  @Rule public TestProjectCreator projectCreator = new TestProjectCreator()
      .withFacetVersions(JavaFacet.VERSION_1_7);

  private final IProgressMonitor monitor = new NullProgressMonitor();
  private IJavaProject project;
  private int initialClasspathSize;

  @Before
  public void setUp() throws JavaModelException {
    project = projectCreator.getJavaProject();
    initialClasspathSize = project.getRawClasspath().length;
  }

  @Test
  public void testAddMavenLibraries_emptyList() throws CoreException {
    IProject project = null;
    List<Library> libraries = new ArrayList<>();
    BuildPath.addMavenLibraries(project, libraries, monitor);
  }

  @Test
  public void testAddNativeLibrary() throws CoreException {
    Library library = new Library("libraryId");
    IClasspathEntry[] result = BuildPath.addNativeLibrary(project, library, monitor);
    Assert.assertEquals(1, result.length);
    Assert.assertEquals(initialClasspathSize + 1, project.getRawClasspath().length);
  }

  @Test
  public void testAddLibraries_noDuplicates() throws CoreException {
    Library library = new Library("libraryId");
    IClasspathEntry[] setup = BuildPath.addNativeLibrary(project, library, monitor);
    Assert.assertEquals(1, setup.length);

    IClasspathEntry[] result = BuildPath.addNativeLibrary(project, library, monitor);
    Assert.assertEquals(0, result.length);
    Assert.assertEquals(initialClasspathSize + 1, project.getRawClasspath().length);
  }

  @Test
  public void testAddLibraries_withDuplicates() throws CoreException {
    Library library1 = new Library("library1");
    IClasspathEntry[] setup = BuildPath.addNativeLibrary(project, library1, monitor);
    Assert.assertEquals(1, setup.length);

    Library library2 = new Library("library2");
    IClasspathEntry[] result = BuildPath.addNativeLibrary(project, library2, monitor);

    Assert.assertEquals(1, result.length);
    Assert.assertTrue(result[0].getPath().toString()
        .endsWith("com.google.cloud.tools.eclipse.appengine.libraries/library2"));
    Assert.assertEquals(initialClasspathSize + 2, project.getRawClasspath().length);
  }

}
