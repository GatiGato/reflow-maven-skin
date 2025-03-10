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
package org.devacfr.maven.skins.reflow.context;

import static org.hamcrest.Matchers.isA;

import java.io.StringReader;

import org.apache.maven.doxia.site.decoration.DecorationModel;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.Xpp3DomBuilder;
import org.devacfr.maven.skins.reflow.ISkinConfig;
import org.devacfr.maven.skins.reflow.model.Footer;
import org.devacfr.maven.skins.reflow.model.NavSideMenu;
import org.devacfr.maven.skins.reflow.model.Navbar;
import org.devacfr.maven.skins.reflow.model.ScrollTop;
import org.devacfr.maven.skins.reflow.model.Toc;
import org.devacfr.maven.skins.reflow.model.TocSidebar;
import org.devacfr.maven.skins.reflow.model.TocTopBar;
import org.devacfr.testing.MockitoTestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class ContextTest extends MockitoTestCase {

    @Mock
    private ISkinConfig config;

    @Before
    public void setup() {
        final MavenProject project = new MavenProject();
        project.setName("reflow");
        project.setArtifactId("reflow-artifact");
        when(config.getProject()).thenReturn(project);

        final DecorationModel decoration = new DecorationModel();
        when(config.getDecoration()).thenReturn(decoration);
    }

    /**
     * test the page context is the default context when any type is defined.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void shouldBuildPageContext() {
        when(config.getAttributeValue(any(String.class), any(String.class), any(Class.class), any(Object.class)))
                .then(invocation -> invocation.getArguments()[3]);
        when(config.getPropertyValue(any(String.class), any(Class.class), any(Object.class)))
                .then(invocation -> invocation.getArguments()[2]);

        when(config.getPropertyValue(Toc.COMPONENT, String.class, null)).thenReturn("top");

        final Context<?> context = Context.buildContext(config);
        assertThat((PageContext) context, isA(PageContext.class));

        final PageContext pageContext = (PageContext) context;

        final Toc<?> toc = pageContext.getToc();
        assertNotNull("toc should be exist", toc);
        assertEquals(true, toc.isEnabled());
        assertThat((TocTopBar) pageContext.getToc(), isA(TocTopBar.class));
        final TocTopBar tocTopBar = (TocTopBar) toc;
        assertEquals("m-toc-top-enabled", tocTopBar.getCssOptions());
        assertEquals("navbar-light bg-light", tocTopBar.getCssClass());

        final Footer footer = pageContext.getFooter();
        assertNotNull("footer should be exist", footer);
        assertEquals("footer-light bg-light", footer.getCssClass());
        assertEquals("", footer.getCssOptions());

        final Navbar navbar = pageContext.getNavbar();
        assertNotNull("Navbar should be exist", navbar);
        assertEquals("navbar-light bg-light", navbar.getCssClass());
        assertEquals("", navbar.getCssOptions());

        final ScrollTop scrollTop = pageContext.getScrollTop();
        assertNotNull("ScrollTop should be exist", scrollTop);
        assertEquals("", scrollTop.getCssClass());
        assertEquals(true, scrollTop.isSmooth());
        assertEquals("scrolltop-smooth-enabled", scrollTop.getCssOptions());

        assertEquals("scrolltop-smooth-enabled m-toc-top-enabled", pageContext.getCssOptions());
        assertEquals("", pageContext.getCssClass());
        assertEquals("page", pageContext.getType());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldBuildPageContextWithTocDisabled() {
        when(config.getAttributeValue(any(String.class), any(String.class), any(Class.class), any(Object.class)))
                .then(invocation -> invocation.getArguments()[3]);
        when(config.getPropertyValue(any(String.class), any(Class.class), any(Object.class)))
                .then(invocation -> invocation.getArguments()[2]);

        final Context<?> context = Context.buildContext(config);
        assertThat((PageContext) context, isA(PageContext.class));

        final PageContext pageContext = (PageContext) context;

        final Toc<?> toc = pageContext.getToc();
        assertNotNull("toc should be exist", toc);
        assertEquals(false, toc.isEnabled());

    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldBuildDocumentContext() throws Exception {
        when(config.getAttributeValue(any(String.class), any(String.class), any(Class.class), any(Object.class)))
                .then(invocation -> invocation.getArguments()[3]);
        when(config.getPropertyValue(any(String.class), any(Class.class), any(Object.class)))
                .then(invocation -> invocation.getArguments()[2]);

        final Xpp3Dom pageProperties = Xpp3DomBuilder.build(new StringReader("<document type=\"doc\"></document>"));
        when(config.getPageProperties()).thenReturn(pageProperties);

        final Context<?> context = Context.buildContext(config);
        assertThat((DocumentContext) context, isA(DocumentContext.class));

        final DocumentContext documentContext = (DocumentContext) context;

        final NavSideMenu navSideMenu = documentContext.getNavSideMenu();
        assertNotNull("NavSideMenu should be exist", navSideMenu);
        assertEquals("m-sidenav-enabled", navSideMenu.getCssOptions());

        assertEquals("scrolltop-smooth-enabled m-sidenav-enabled", documentContext.getCssOptions());
        assertEquals("", documentContext.getCssClass());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldBuildFrameContext() throws Exception {
        when(config.getAttributeValue(any(String.class), any(String.class), any(Class.class), any(Object.class)))
                .then(invocation -> invocation.getArguments()[3]);
        when(config.getPropertyValue(any(String.class), any(Class.class), any(Object.class)))
                .then(invocation -> invocation.getArguments()[2]);

        final String xml = "<reflowSkin><pages>" + //
                "<development-documentation type=\"doc\">" + //
                "          <menu name=\"Development Documentation\">" + //
                "            <item name=\"Contribute\" href=\"dev/contribute.html\" />" + //
                "            <item name=\"Code Conventions\" href=\"dev/code-conventions.html\"/>" + //
                "            <item name=\"Release Management\" href=\"dev/release-management.html\"/>" + //
                "          </menu>" + //
                "        </development-documentation>" + //
                "</pages></reflowSkin>";
        final Xpp3Dom globalProperties = Xpp3DomBuilder.build(new StringReader(xml));
        when(config.getPageProperties()).thenReturn(new Xpp3Dom("dev-contribute"));
        when(config.getGlobalProperties()).thenReturn(globalProperties);
        when(config.getFileId()).thenReturn("dev-contribute");
        when(config.getNamespace()).thenReturn("");

        final Context<?> context = Context.buildContext(config);
        assertThat((FrameContext) context, isA(FrameContext.class));
        final FrameContext frameContext = (FrameContext) context;

        assertEquals("development-documentation", frameContext.getDocumentParent());

        final Toc<?> toc = frameContext.getToc();
        assertNotNull("toc should be exist", toc);
        assertThat((TocSidebar) frameContext.getToc(), isA(TocSidebar.class));

        final TocSidebar tocSidebar = (TocSidebar) toc;
        assertEquals(Integer.MAX_VALUE, tocSidebar.getLevel());
        assertEquals("sidebar-light bg-light", tocSidebar.getCssClass());
        assertEquals("m-toc-sidebar-enabled m-toc-sidebar-expanded m-toc-sidebar-autoexpandable toc-sidebar-fixed",
            tocSidebar.getCssOptions());

        final Footer footer = frameContext.getFooter();
        assertNotNull("footer should be exist", footer);
        assertEquals("footer-light bg-light", footer.getCssClass());
        assertEquals("", footer.getCssOptions());

        final Navbar navbar = frameContext.getNavbar();
        assertNotNull("Navbar should be exist", navbar);
        assertEquals("navbar-light bg-light", navbar.getCssClass());
        assertEquals("", navbar.getCssOptions());

        final ScrollTop scrollTop = frameContext.getScrollTop();
        assertNotNull("ScrollTop should be exist", scrollTop);
        assertEquals("", scrollTop.getCssClass());
        assertEquals(true, scrollTop.isSmooth());
        assertEquals("scrolltop-smooth-enabled", scrollTop.getCssOptions());

    }
}
