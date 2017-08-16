package org.occiware.clouddesigner.google2occi.handlers.json.data;


import java.util.HashMap;
import java.util.Map;

import org.eclipse.cmf.occi.core.ArrayType;
import org.eclipse.cmf.occi.core.BooleanType;
import org.eclipse.cmf.occi.core.DataType;
import org.eclipse.cmf.occi.core.EObjectType;
import org.eclipse.cmf.occi.core.EnumerationType;
import org.eclipse.cmf.occi.core.NumericType;
import org.eclipse.cmf.occi.core.NumericTypeEnum;
import org.eclipse.cmf.occi.core.OCCIFactory;
import org.eclipse.cmf.occi.core.RecordType;
import org.eclipse.cmf.occi.core.StringType;
import org.occiware.clouddesigner.google2occi.handlers.json.KindsBuilder;

public class StringToDataType {
	
	public final static StringType typeModelString = OCCIFactory.eINSTANCE.createStringType();
	public final static BooleanType typeModelBoolean = OCCIFactory.eINSTANCE.createBooleanType();
	public final static NumericType typeModelByte = OCCIFactory.eINSTANCE.createNumericType();
	public final static NumericType typeModelDouble = OCCIFactory.eINSTANCE.createNumericType();
	public final static NumericType typeModelFloat = OCCIFactory.eINSTANCE.createNumericType();
	public final static NumericType typeModelInteger = OCCIFactory.eINSTANCE.createNumericType();
	public final static NumericType typeModelLong = OCCIFactory.eINSTANCE.createNumericType();
	public final static NumericType typeModelShort = OCCIFactory.eINSTANCE.createNumericType();
	public final static EnumerationType typeEnum = OCCIFactory.eINSTANCE.createEnumerationType();
	public final static ArrayType typeArray = OCCIFactory.eINSTANCE.createArrayType();
	public final static RecordType typeRecord = OCCIFactory.eINSTANCE.createRecordType();
	
	public final static EObjectType typeModelMap = OCCIFactory.eINSTANCE.createEObjectType();
	
	public final static Map<String, DataType> map = new HashMap<>();

	static {
		typeModelByte.setType(NumericTypeEnum.BYTE);
		typeModelByte.setName("byte");
		typeModelDouble.setType(NumericTypeEnum.DOUBLE);
		typeModelByte.setName("double");
		typeModelFloat.setType(NumericTypeEnum.FLOAT);
		typeModelByte.setName("float");
		typeModelInteger.setType(NumericTypeEnum.INTEGER);
		typeModelByte.setName("integer");
		typeModelLong.setType(NumericTypeEnum.LONG);
		typeModelByte.setName("long");
		typeModelShort.setType(NumericTypeEnum.SHORT);
		typeModelByte.setName("short");
		
		typeModelBoolean.setName("boolean");
		typeModelString.setName("string");
		typeEnum.setName("enum");
		typeArray.setName("array");
		typeRecord.setName("record");
		
		typeModelMap.setName("map");
		
		KindsBuilder.extension.getTypes().add(typeModelString);
		KindsBuilder.extension.getTypes().add(typeModelBoolean);
		KindsBuilder.extension.getTypes().add(typeModelByte);
		KindsBuilder.extension.getTypes().add(typeModelDouble);
		KindsBuilder.extension.getTypes().add(typeModelFloat);
		KindsBuilder.extension.getTypes().add(typeModelInteger);
		KindsBuilder.extension.getTypes().add(typeModelLong);
		KindsBuilder.extension.getTypes().add(typeModelShort);
		KindsBuilder.extension.getTypes().add(typeEnum);
		KindsBuilder.extension.getTypes().add(typeArray);
		KindsBuilder.extension.getTypes().add(typeRecord);
		
		KindsBuilder.extension.getTypes().add(typeModelMap);
		
		map.put("string", typeModelString);
		map.put("boolean", typeModelBoolean);	
		
		map.put("byte", typeModelByte);
		map.put("double", typeModelDouble);
		map.put("float", typeModelFloat);
		map.put("integer", typeModelInteger);
		map.put("number", typeModelInteger);
		map.put("uint32", typeModelInteger);
		map.put("long", typeModelLong);
		map.put("short", typeModelShort);
		
		map.put("enum", typeEnum);
		map.put("array", typeArray);
		map.put("record", typeRecord);
		
		map.put("map", typeModelMap);
	}
}
