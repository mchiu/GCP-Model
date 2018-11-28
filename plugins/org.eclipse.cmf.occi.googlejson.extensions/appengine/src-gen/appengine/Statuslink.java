/**
 * Copyright (c) 2015-2017 Obeo, Inria
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 	
 * Contributors:
 * - William Piers <william.piers@obeo.fr>
 * - Philippe Merle <philippe.merle@inria.fr>
 * - Faiez Zalila <faiez.zalila@inria.fr>
 */
package appengine;

import org.eclipse.cmf.occi.core.Link;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Statuslink</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * The error result of the operation in case of failure or cancellation.
 * <!-- end-model-doc -->
 *
 *
 * @see appengine.AppenginePackage#getStatuslink()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='sourceConstraint targetConstraint'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot sourceConstraint='self.source.oclIsKindOf(appengine::Operation)' targetConstraint='self.target.oclIsKindOf(appengine::Status)'"
 * @generated
 */
public interface Statuslink extends Link {
} // Statuslink
