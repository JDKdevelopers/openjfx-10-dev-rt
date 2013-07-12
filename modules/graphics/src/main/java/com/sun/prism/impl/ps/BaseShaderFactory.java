/*
 * Copyright (c) 2009, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.prism.impl.ps;

import com.sun.prism.impl.BaseResourceFactory;
import com.sun.prism.impl.shape.BasicEllipseRep;
import com.sun.prism.impl.shape.BasicRoundRectRep;
import com.sun.prism.impl.shape.BasicShapeRep;
import com.sun.prism.ps.ShaderFactory;
import com.sun.prism.shape.ShapeRep;
import com.sun.prism.impl.PrismSettings;

public abstract class BaseShaderFactory extends BaseResourceFactory
    implements ShaderFactory
{
    public ShapeRep createPathRep(boolean needs3D) {
        if (needs3D || PrismSettings.tessShapes) {
            return PrismSettings.tessShapesAA ?
                new AATessShapeRep() : new TessShapeRep();
        } else {
            return PrismSettings.cacheComplexShapes ?
                new CachingShapeRep() : new BasicShapeRep();
        }
    }

    public ShapeRep createRoundRectRep(boolean needs3D) {
        if (needs3D || PrismSettings.tessShapes) {
            return PrismSettings.tessShapesAA ?
                new AATessRoundRectRep() : new TessRoundRectRep();
        } else {
            return PrismSettings.cacheSimpleShapes ?
                new CachingRoundRectRep() : new BasicRoundRectRep();
        }
    }

    public ShapeRep createEllipseRep(boolean needs3D) {
        if (needs3D || PrismSettings.tessShapes) {
            return PrismSettings.tessShapesAA ?
                new AATessEllipseRep() : new TessEllipseRep();
        } else {
            return PrismSettings.cacheSimpleShapes ?
                new CachingEllipseRep() : new BasicEllipseRep();
        }
    }

    public ShapeRep createArcRep(boolean needs3D) {
        if (needs3D || PrismSettings.tessShapes) {
            return PrismSettings.tessShapesAA ?
                new AATessArcRep() : new TessArcRep();
        } else {
            return PrismSettings.cacheComplexShapes ?
                new CachingShapeRep() : new BasicShapeRep();
        }
    }
}