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
package appengine.impl;

import appengine.AppenginePackage;
import appengine.AppengineTables;
import appengine.Resourceslink;

import java.lang.reflect.InvocationTargetException;

import java.util.Map;

import org.eclipse.cmf.occi.core.Resource;

import org.eclipse.cmf.occi.core.impl.LinkImpl;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.ocl.pivot.evaluation.Executor;

import org.eclipse.ocl.pivot.ids.IdResolver;
import org.eclipse.ocl.pivot.ids.TypeId;

import org.eclipse.ocl.pivot.internal.utilities.PivotUtilInternal;

import org.eclipse.ocl.pivot.library.oclany.OclAnyOclIsKindOfOperation;
import org.eclipse.ocl.pivot.library.oclany.OclComparableLessThanEqualOperation;

import org.eclipse.ocl.pivot.library.string.CGStringGetSeverityOperation;
import org.eclipse.ocl.pivot.library.string.CGStringLogDiagnosticOperation;

import org.eclipse.ocl.pivot.utilities.ValueUtil;

import org.eclipse.ocl.pivot.values.IntegerValue;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Resourceslink</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class ResourceslinkImpl extends LinkImpl implements Resourceslink {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ResourceslinkImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AppenginePackage.eINSTANCE.getResourceslink();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean sourceConstraint(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		/**
		 *
		 * inv sourceConstraint:
		 *   let severity : Integer[1] = 'Resourceslink::sourceConstraint'.getSeverity()
		 *   in
		 *     if severity <= 0
		 *     then true
		 *     else
		 *       let result : occi::Boolean[1] = self.source.oclIsKindOf(Version)
		 *       in
		 *         'Resourceslink::sourceConstraint'.logDiagnostic(self, null, diagnostics, context, null, severity, result, 0)
		 *     endif
		 */
		final /*@NonInvalid*/ Executor executor = PivotUtilInternal.getExecutor(this);
		final /*@NonInvalid*/ IdResolver idResolver = executor.getIdResolver();
		final /*@NonInvalid*/ IntegerValue severity_0 = CGStringGetSeverityOperation.INSTANCE.evaluate(executor, AppengineTables.STR_Resourceslink_c_c_sourceConstraint);
		final /*@NonInvalid*/ boolean le = OclComparableLessThanEqualOperation.INSTANCE.evaluate(executor, severity_0, AppengineTables.INT_0).booleanValue();
		/*@NonInvalid*/ boolean symbol_0;
		if (le) {
			symbol_0 = ValueUtil.TRUE_VALUE;
		}
		else {
			final /*@NonInvalid*/ org.eclipse.ocl.pivot.Class TYP_appengine_c_c_Version_0 = idResolver.getClass(AppengineTables.CLSSid_Version, null);
			final /*@NonInvalid*/ Resource source = this.getSource();
			final /*@NonInvalid*/ boolean result = OclAnyOclIsKindOfOperation.INSTANCE.evaluate(executor, source, TYP_appengine_c_c_Version_0).booleanValue();
			final /*@NonInvalid*/ boolean logDiagnostic = CGStringLogDiagnosticOperation.INSTANCE.evaluate(executor, TypeId.BOOLEAN, AppengineTables.STR_Resourceslink_c_c_sourceConstraint, this, (Object)null, diagnostics, context, (Object)null, severity_0, result, AppengineTables.INT_0).booleanValue();
			symbol_0 = logDiagnostic;
		}
		return Boolean.TRUE == symbol_0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean targetConstraint(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		/**
		 *
		 * inv targetConstraint:
		 *   let severity : Integer[1] = 'Resourceslink::targetConstraint'.getSeverity()
		 *   in
		 *     if severity <= 0
		 *     then true
		 *     else
		 *       let
		 *         result : occi::Boolean[1] = self.target.oclIsKindOf(Resources)
		 *       in
		 *         'Resourceslink::targetConstraint'.logDiagnostic(self, null, diagnostics, context, null, severity, result, 0)
		 *     endif
		 */
		final /*@NonInvalid*/ Executor executor = PivotUtilInternal.getExecutor(this);
		final /*@NonInvalid*/ IdResolver idResolver = executor.getIdResolver();
		final /*@NonInvalid*/ IntegerValue severity_0 = CGStringGetSeverityOperation.INSTANCE.evaluate(executor, AppengineTables.STR_Resourceslink_c_c_targetConstraint);
		final /*@NonInvalid*/ boolean le = OclComparableLessThanEqualOperation.INSTANCE.evaluate(executor, severity_0, AppengineTables.INT_0).booleanValue();
		/*@NonInvalid*/ boolean symbol_0;
		if (le) {
			symbol_0 = ValueUtil.TRUE_VALUE;
		}
		else {
			final /*@NonInvalid*/ org.eclipse.ocl.pivot.Class TYP_appengine_c_c_Resources = idResolver.getClass(AppengineTables.CLSSid_Resources, null);
			final /*@NonInvalid*/ Resource target = this.getTarget();
			final /*@NonInvalid*/ boolean result = OclAnyOclIsKindOfOperation.INSTANCE.evaluate(executor, target, TYP_appengine_c_c_Resources).booleanValue();
			final /*@NonInvalid*/ boolean logDiagnostic = CGStringLogDiagnosticOperation.INSTANCE.evaluate(executor, TypeId.BOOLEAN, AppengineTables.STR_Resourceslink_c_c_targetConstraint, this, (Object)null, diagnostics, context, (Object)null, severity_0, result, AppengineTables.INT_0).booleanValue();
			symbol_0 = logDiagnostic;
		}
		return Boolean.TRUE == symbol_0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case AppenginePackage.RESOURCESLINK___SOURCE_CONSTRAINT__DIAGNOSTICCHAIN_MAP:
				return sourceConstraint((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
			case AppenginePackage.RESOURCESLINK___TARGET_CONSTRAINT__DIAGNOSTICCHAIN_MAP:
				return targetConstraint((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
		}
		return super.eInvoke(operationID, arguments);
	}

} //ResourceslinkImpl
