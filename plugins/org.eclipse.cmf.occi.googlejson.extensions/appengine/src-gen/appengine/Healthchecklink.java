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
 * A representation of the model object '<em><b>Healthchecklink</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Configures health checking for VM instances. Unhealthy instances are stopped and replaced with new instances. Only applicable for VM runtimes.Only returned in GET requests if view=FULL is set.
 * <!-- end-model-doc -->
 *
 *
 * @see appengine.AppenginePackage#getHealthchecklink()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='sourceConstraint targetConstraint'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot sourceConstraint='self.source.oclIsKindOf(appengine::Version)' targetConstraint='self.target.oclIsKindOf(appengine::Healthcheck)'"
 * @generated
 */
public interface Healthchecklink extends Link {
} // Healthchecklink
