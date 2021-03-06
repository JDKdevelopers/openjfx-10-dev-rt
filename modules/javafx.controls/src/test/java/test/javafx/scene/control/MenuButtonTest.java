/*
 * Copyright (c) 2010, 2017, Oracle and/or its affiliates. All rights reserved.
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

package test.javafx.scene.control;

import javafx.scene.control.skin.MenuButtonSkin;
import com.sun.javafx.tk.Toolkit;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.event.Event;
import javafx.event.EventHandler;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author lubermud
 */
public class MenuButtonTest {
    private MenuButton menuButton;

    @Before public void setup() {
        menuButton = new MenuButton();
    }

    @Test public void defaultConstructorShouldHaveNoGraphic() {
        assertNull(menuButton.getGraphic());
    }

    @Test public void defaultConstructorShouldHaveNullString() {
        assertEquals("", menuButton.getText());
    }

    @Test public void oneArgConstructorShouldHaveNoGraphic1() {
        MenuButton mb2 = new MenuButton(null);
        assertNull(mb2.getGraphic());
    }

    @Test public void oneArgConstructorShouldHaveNoGraphic2() {
        MenuButton mb2 = new MenuButton("");
        assertNull(mb2.getGraphic());
    }

    @Test public void oneArgConstructorShouldHaveNoGraphic3() {
        MenuButton mb2 = new MenuButton("Hello");
        assertNull(mb2.getGraphic());
    }

    @Test public void oneArgConstructorShouldHaveSpecifiedString1() {
        MenuButton mb2 = new MenuButton(null);
        assertEquals("", mb2.getText());
    }

    @Test public void oneArgConstructorShouldHaveSpecifiedString2() {
        MenuButton mb2 = new MenuButton("");
        assertEquals("", mb2.getText());
    }

    @Test public void oneArgConstructorShouldHaveSpecifiedString3() {
        MenuButton mb2 = new MenuButton("Hello");
        assertEquals("Hello", mb2.getText());
    }

    @Test public void twoArgConstructorShouldHaveSpecifiedGraphic1() {
        MenuButton mb2 = new MenuButton(null, null);
        assertNull(mb2.getGraphic());
    }

    @Test public void twoArgConstructorShouldHaveSpecifiedGraphic2() {
        Rectangle rect = new Rectangle();
        MenuButton mb2 = new MenuButton("Hello", rect);
        assertSame(rect, mb2.getGraphic());
    }

    @Test public void twoArgConstructorShouldHaveSpecifiedString1() {
        MenuButton mb2 = new MenuButton(null, null);
        assertEquals("", mb2.getText());
    }

    @Test public void twoArgConstructorShouldHaveSpecifiedString2() {
        Rectangle rect = new Rectangle();
        MenuButton mb2 = new MenuButton("Hello", rect);
        assertEquals("Hello", mb2.getText());
    }

    @Test public void getItemsDefaultNotNull() {
        assertNotNull(menuButton.getItems());
    }

    @Test public void getItemsDefaultSizeZero() {
        assertEquals(0, menuButton.getItems().size());
    }

    @Test public void getItemsAddable() {
        menuButton.getItems().add(new MenuItem());
        assertTrue(menuButton.getItems().size() > 0);
    }

    @Test public void getItemsClearable() {
        menuButton.getItems().add(new MenuItem());
        menuButton.getItems().clear();
        assertEquals(0, menuButton.getItems().size());
    }

    @Test public void defaultIsShowingFalse() {
        assertFalse(menuButton.isShowing());
    }

    @Test public void showIsShowingTrue() {
        menuButton.show();
        assertTrue(menuButton.isShowing());
    }

    @Test public void hideIsShowingFalse1() {
        menuButton.hide();
        assertFalse(menuButton.isShowing());
    }

    @Test public void hideIsShowingFalse2() {
        menuButton.show();
        menuButton.hide();
        assertFalse(menuButton.isShowing());
    }

    @Test public void getUnspecifiedShowingProperty1() {
        assertNotNull(menuButton.showingProperty());
    }

    @Test public void getUnspecifiedShowingProperty2() {
        MenuButton mb2 = new MenuButton("", null);
        assertNotNull(mb2.showingProperty());
    }

    @Test public void unsetShowingButNotNull() {
        menuButton.showingProperty();
        assertNotNull(menuButton.isShowing());
    }

    @Test public void menuButtonIsFiredIsNoOp() {
        menuButton.fire(); // should throw no exceptions, if it does, the test fails
    }

    @Test public void defaultPopupSide() {
        assertEquals(Side.BOTTOM, menuButton.getPopupSide());
        assertEquals(Side.BOTTOM, menuButton.popupSideProperty().get());
    }

    @Test public void setNullPopupSide() {
        menuButton.setPopupSide(null);
        assertNull(menuButton.getPopupSide());
    }

    @Test public void setSpecifiedPopupSide() {
        Side side = Side.TOP;
        menuButton.setPopupSide(side);
        assertSame(side, menuButton.getPopupSide());
    }

    @Test public void getUnspecifiedPopupSideProperty1() {
        assertNotNull(menuButton.popupSideProperty());
    }

    @Test public void getUnspecifiedPopupSideProperty2() {
        MenuButton mb2 = new MenuButton("", null);
        assertNotNull(mb2.popupSideProperty());
    }

    @Test public void unsetPopupSideButNotNull() {
        menuButton.popupSideProperty();
        assertNotNull(menuButton.getPopupSide());
    }

    @Test public void popupSideCanBeBound() {
        Side side = Side.TOP;
        SimpleObjectProperty<Side> other = new SimpleObjectProperty<Side>(menuButton, "popupSide", side);
        menuButton.popupSideProperty().bind(other);
        assertSame(side, menuButton.getPopupSide());
    }

    //TODO: test show()/isShowing() for disabled=true
    //TODO: test MenuButton.impl_getPsuedoClassState

    @Test
    public void test_RT_21894() {

        // Bug reproduces by setting opacity on the MenuButton
        // then moving focus on and off the MenuButton
        final MenuButton mb = new MenuButton();
        mb.setText("SomeText");

        MenuButtonSkin mbs = new MenuButtonSkin(mb);
        mb.setSkin(mbs);

        Button other = new Button("other");
        // Doesn't have to be done this way, but this more closely duplicates
        // the example code in the bug report.
        other.setOnAction(t -> {
            mb.setOpacity(.5);
        });

        VBox vbox = new VBox();
        vbox.getChildren().addAll(mb, other);
        Scene scene = new Scene(vbox, 300, 300);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.requestFocus();

        other.requestFocus();
        assertFalse(mb.isFocused());

        // set opacity on MenuButton
        other.fire();

        // focus on MenuButton
        mb.requestFocus();
        assertTrue(mb.isFocused());

        // give css a chance to run
        Toolkit.getToolkit().firePulse();

        // focus off the MenuButton
        other.requestFocus();
        assertFalse(mb.isFocused());

        // give css a chance to run
        Toolkit.getToolkit().firePulse();

        // MenuButton should still be 50%
        assertEquals(.5, mb.getOpacity(), 0.00001);

    }

    @Test public void testSetContentDisplayGraphicOnly() {
        Button btn = new Button("1234");

        MenuButton mb1 = new MenuButton("Sample Text", btn);
        mb1.setStyle("-fx-label-padding:0;");
        mb1.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        MenuButton mb2 = new MenuButton("Sample Text", btn);
        mb2.setStyle("-fx-label-padding:100;");
        mb2.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        Scene scene = new Scene(new Group(mb1, mb2), 400, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        Toolkit.getToolkit().firePulse();

        // label-padding should not affect GRAPHIC_ONLY MenuButton size
        assertEquals(mb1.getWidth(), mb2.getWidth(), 0.00001);
        assertEquals(mb1.getHeight(), mb2.getHeight(), 0.00001);
    }

    boolean onShowingPass;
    boolean onShownPass;
    boolean onHidingPass;
    boolean onHiddenPass;
    @Test public void test_jdk_8175963_showHideEvents() {
        MenuItem it1 = new MenuItem("1");
        MenuButton mbtn = new MenuButton("MenuButton", null, it1);

        mbtn.addEventHandler(MenuButton.ON_SHOWING, event -> {
            assertEquals("event is not of type MenuButton.ON_SHOWING",
                event.getEventType(), MenuButton.ON_SHOWING);
            onShowingPass = true;
        });
        mbtn.addEventHandler(MenuButton.ON_SHOWN, event -> {
            assertEquals("event is not of type MenuButton.ON_SHOWN",
                event.getEventType(), MenuButton.ON_SHOWN);
            onShownPass = true;
        });
        mbtn.addEventHandler(MenuButton.ON_HIDING, event -> {
            assertEquals("event is not of type MenuButton.ON_HIDING",
                event.getEventType(), MenuButton.ON_HIDING);
            onHidingPass = true;
        });
        mbtn.addEventHandler(MenuButton.ON_HIDDEN, event -> {
            assertEquals("event is not of type  MenuButton.ON_HIDDEN",
                event.getEventType(), MenuButton.ON_HIDDEN);
            onHiddenPass = true;
        });

        mbtn.show();
        mbtn.hide();

        assertTrue("OnShowing event not received", onShowingPass);
        assertTrue("onShown event not received", onShownPass);
        assertTrue("onHiding event not received", onHidingPass);
        assertTrue("onHidden event not received", onHiddenPass);
    }
}
