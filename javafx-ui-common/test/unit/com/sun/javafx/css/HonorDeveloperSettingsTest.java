/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package com.sun.javafx.css;

import com.sun.javafx.css.parser.CSSParser;
import com.sun.javafx.tk.Toolkit;
import java.io.IOException;
import java.net.URL;
import static org.junit.Assert.*;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * AKA: RT-7401. Tests that the pattern used works by testing opacity
 * specifically. Tests for font and text-fill should be done in the tests
 * for Label and Labeled.
 */
public class HonorDeveloperSettingsTest {

    private Scene scene;
    private Rectangle rect;
    private Text text;

    // Scene must have a Window for CSS to load the stylesheet.
    // And Window must have a Scene for StyleManager to find the right scene
    static class TestWindow extends Window {
        @Override public void setScene(Scene value) {
            super.setScene(value);
        }
    }

    @Before
    public void setUp() {
        rect = new Rectangle();
        rect.setId("rectangle");

        text = new Text();
        text.setId("text");

        Group group = new Group();
        group.getChildren().addAll(rect, text);

        scene = new Scene(group);/* {
            TestWindow window;
            {
                window = new TestWindow();
                window.setScene(HonorDeveloperSettingsTest.this.scene);
                impl_setWindow(window);
            }
        };*/
        
        System.setProperty("binary.css", "false");
        String url = getClass().getResource("HonorDeveloperSettingsTest_UA.css").toExternalForm();
        StyleManager.getInstance().setDefaultUserAgentStylesheet(url);
        
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testOpacityIsSetByCSSByDefault() {
        rect.impl_processCSS(true);
        assertEquals(.76, rect.getOpacity(), 0.01);
    }

    @Test
    public void testOpacityWithInitializedValueSameAsDefaultValueIsIgnoredByCSS() {
        rect.setOpacity(1.0);
        rect.impl_processCSS(true);
        assertEquals(1.0, rect.getOpacity(), 0.01);
    }

    @Test
    public void testOpacityWithInitializedValueIsIgnoredByCSS() {
        rect.setOpacity(0.535);
        rect.impl_processCSS(true);
        assertEquals(0.535, rect.getOpacity(), 0.01);
    }

    @Test
    public void testOpacityWithManuallyChangedValueIsIgnoredByCSS() {
        rect.impl_processCSS(true);
        assertEquals(.76, rect.getOpacity(), 0.01);
        rect.setOpacity(.873);
        rect.impl_processCSS(true);
        assertEquals(.873, rect.getOpacity(), 0.01);
    }

    @Test
    public void testOpacityWithManuallyChangedValueAndAuthorStyleIsSetToAuthorStyle() {
        rect.impl_processCSS(true);
        assertEquals(.76, rect.getOpacity(), 0.01);
        rect.setStyle("-fx-opacity: 42%;");
        rect.setOpacity(.873);
        rect.impl_processCSS(true);
        assertEquals(.42, rect.getOpacity(), 0.01);
    }

    @Test
    public void testCursorIsSetByCSSByDefault() {
        rect.impl_processCSS(true);
        assertEquals(Cursor.HAND, rect.getCursor());
    }

    @Test
    public void testCursorWithInitializedValueSameAsDefaultValueIsIgnoredByCSS() {
        rect.setCursor(null);
        rect.impl_processCSS(true);
        assertEquals(null, rect.getCursor());
    }

    @Test
    public void testCursorWithInitializedValueIsIgnoredByCSS() {
        rect.setCursor(Cursor.WAIT);
        rect.impl_processCSS(true);
        assertEquals(Cursor.WAIT, rect.getCursor());
    }

    @Test
    public void testCursorWithManuallyChangedValueIsIgnoredByCSS() {
        rect.impl_processCSS(true);
        assertEquals(Cursor.HAND, rect.getCursor());
        rect.setCursor(Cursor.WAIT);
        rect.impl_processCSS(true);
        assertEquals(Cursor.WAIT, rect.getCursor());
    }

//    public void testEffectIsSetByCSSByDefault() {
//        final Rectangle rect = Rectangle { id: "rectangle" }
//        scene.stylesheets = "{__DIR__}HonorDeveloperSettingsTest.css";
//        scene.content = rect;
//        rect.impl_processCSS(true);
//        assertNotNull(rect.effect);
//    }
//
//    public void testEffectWithInitializedValueSameAsDefaultValueIsIgnoredByCSS() {
//        final Rectangle rect = Rectangle {
//            id: "rectangle"
//            effect: null
//        }
//        scene.stylesheets = "{__DIR__}HonorDeveloperSettingsTest.css";
//        scene.content = rect;
//        rect.impl_processCSS(true);
//        assertNull(rect.effect);
//    }
//
//    public void testEffectWithInitializedValueIsIgnoredByCSS() {
//        DropShadow shadow = DropShadow { }
//        final Rectangle rect = Rectangle {
//            id: "rectangle"
//            effect: shadow
//        }
//        scene.stylesheets = "{__DIR__}HonorDeveloperSettingsTest.css";
//        scene.content = rect;
//        rect.impl_processCSS(true);
//        assertSame(shadow, rect.effect);
//    }
//
//    public void testEffectWithManuallyChangedValueIsIgnoredByCSS() {
//        DropShadow shadow = DropShadow { }
//        final Rectangle rect = Rectangle { id: "rectangle" }
//        scene.stylesheets = "{__DIR__}HonorDeveloperSettingsTest.css";
//        scene.content = rect;
//        rect.impl_processCSS(true);
//        assertNotSame(shadow, rect.effect);
//        rect.effect = shadow;
//        rect.impl_processCSS(true);
//        assertSame(shadow, rect.effect);
//    }

    @Test
    public void testFontIsSetByCSSByDefault() {
        text.impl_processCSS(true);
        assertNotSame(Font.getDefault(), text.getFont());
    }

    @Test
    public void testFontWithInitializedValueSameAsDefaultValueIsIgnoredByCSS() {
        text.setFont(Font.getDefault());
        text.impl_processCSS(true);
        assertSame(Font.getDefault(), text.getFont());
    }

    @Test
    public void testFontWithInitializedValueIsIgnoredByCSS() {
        Font f = Font.font(Font.getDefault().getFamily(), 54.0);
        text.setFont(f);
        text.impl_processCSS(true);
        assertSame(f, text.getFont());
    }

    @Test
    public void testFontWithManuallyChangedValueIsIgnoredByCSS() {
        Font f = Font.font(Font.getDefault().getFamily(), 54.0);
        text.impl_processCSS(true);
        assertNotSame(f, text.getFont());
        text.setFont(f);
        text.impl_processCSS(true);
        assertSame(f, text.getFont());
    }
    
    @Test
    public void testUseInheritedFontSizeFromStylesheetForEmSize() {
        
        String url = getClass().getResource("HonorDeveloperSettingsTest_AUTHOR.css").toExternalForm();
        scene.getStylesheets().add(url);
        Toolkit.getToolkit().firePulse();
        assertEquals(20, rect.getStrokeWidth(), 0.00001);
        
    }
    
    @Test
    public void testInhertWithNoStyleDoesNotOverrideUserSetValue() {
        Font font = Font.font("Amble", 14);
        text.setFont(font);
        
        String url = getClass().getResource("HonorDeveloperSettingsTest_AUTHOR.css").toExternalForm();
        scene.getStylesheets().add(url);
           
        Toolkit.getToolkit().firePulse();
        //
        // Stroke width is set to 1em in the author stylesheet. If 
        // RT-20145 is not working, then the code will pick up the 20px
        // font size.
        //
        assertEquals(14, text.getStrokeWidth(), 0.00001);
        
    }
    
    @Test
    public void testInlineStyleInheritedFromParentApplies() {
        
        // must remove the id so we don't pick up the ua style
        text.setId(null);
        text.setStyle("-fx-stroke-width: 1em;");
        
        scene.getRoot().setStyle("-fx-font: 18 Amble;");
        
        Toolkit.getToolkit().firePulse();
        
        //
        // If RT-20513 is not working, then the code will _not_ 
        // pick up the inline style
        //        
        assertEquals(18, text.getStrokeWidth(), 0.00001);
        
    }
    
    @Test
    public void testInlineStyleNotInheritedFromParentWhenUserSetsFont() {
        
        // must remove the id so we don't pick up the ua style
        text.setId(null);
        text.setStyle("-fx-stroke-width: 1em;");
        
        Font font = Font.font("Amble", 14);
        text.setFont(font);
        
        scene.getRoot().setStyle("-fx-font: 18 Amble;");
        
        Toolkit.getToolkit().firePulse();
                
        assertEquals(14, text.getStrokeWidth(), 0.00001);
        
    }
    
}
