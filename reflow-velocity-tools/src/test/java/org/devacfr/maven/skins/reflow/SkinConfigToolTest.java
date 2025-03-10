/*
 * Copyright 2012-2019 Christophe Friederich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.devacfr.maven.skins.reflow;

import java.io.InputStream;

import org.apache.maven.doxia.site.decoration.DecorationModel;
import org.apache.maven.doxia.site.decoration.io.xpp3.DecorationXpp3Reader;
import org.apache.maven.project.MavenProject;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.generic.RenderTool;
import org.apache.velocity.tools.generic.ValueParser;
import org.devacfr.testing.MockitoTestCase;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

public class SkinConfigToolTest extends MockitoTestCase {

    private ToolContext velocityContext;

    private ValueParser valueParser;

    private SkinConfigTool skinConfig;

    private DecorationModel decorationModel;

    @Before
    public void setup() throws Exception {
        velocityContext = new ToolContext();
        final DecorationXpp3Reader reader = new DecorationXpp3Reader();

        try (final InputStream in = getResource("default.site.xml").openBufferedStream()) {
            decorationModel = reader.read(in);
        }
        valueParser = new ValueParser(
                ImmutableMap.<String, Object> builder().put("velocityContext", velocityContext).build());

        final MavenProject maven = new MavenProject();
        maven.setArtifactId("maven-reflow-plugin");
        maven.setUrl("https://devacfr.github.io/reflow-maven-skin/");

        velocityContext.put("project", maven);
        velocityContext.put("decoration", decorationModel);
        velocityContext.put("render", new RenderTool());
        velocityContext.put("currentFileName", "summary.html");
        velocityContext.put("alignedFileName", "summary.html");
        skinConfig = new SkinConfigTool();
        skinConfig.configure(valueParser);
    }

    @Test
    public void testIs() {
        assertEquals(true, skinConfig.is("localResources"));
        assertEquals(false, skinConfig.is("markPageHeader"));
    }

    @Test
    public void testNot() {
        assertEquals(true, skinConfig.not("markPageHeader"));
        assertEquals(false, skinConfig.not("localResources"));
    }

    @Test
    public void testIsValue() {
        assertEquals(true, skinConfig.isValue("localResources", "true"));
        assertEquals(false, skinConfig.isValue("localResources", "value"));
    }

    @Test
    public void testIsActiveLink() {

        velocityContext.put("alignedFileName", "index.html");
        assertEquals("should be active", true, skinConfig.isActiveLink("."));
        assertEquals("should be active", true, skinConfig.isActiveLink(""));

        velocityContext.put("alignedFileName", "summary.html");
        assertEquals("should be note active", false, skinConfig.isActiveLink(null));
        assertEquals("should be note active", false, skinConfig.isActiveLink("."));
        assertEquals("should be note active", false, skinConfig.isActiveLink("index.html"));
    }

    @Test
    public void testIsExternalLink() {

        assertEquals("should be external", true, skinConfig.isExternalLink("http://foo.com"));
        assertEquals("should be external", true, skinConfig.isExternalLink("https://foo.com"));
        assertEquals("should be external", true, skinConfig.isExternalLink("ftp://foo.com"));
        assertEquals("should be external", true, skinConfig.isExternalLink("mailto:john@foo.com"));
        assertEquals("should be external", true, skinConfig.isExternalLink("file://foo.com"));

        assertEquals("should be internal", false, skinConfig.isExternalLink(null));
        assertEquals("should be internal", false, skinConfig.isExternalLink("summary.html"));
        assertEquals("should be internal",
            false,
            skinConfig.isExternalLink("https://devacfr.github.io/reflow-maven-skin/dev/summary.html"));
    }

    @Test
    public void testSlugFilename() {
        assertEquals("dev-release-management", SkinConfigTool.slugFilename("dev/release-management.html"));
        assertEquals("dev-develop_guide", SkinConfigTool.slugFilename("dev/develop_guide.html"));
        assertEquals("dev-develop_guide", SkinConfigTool.slugFilename("dev/develop_guide"));
    }

}
