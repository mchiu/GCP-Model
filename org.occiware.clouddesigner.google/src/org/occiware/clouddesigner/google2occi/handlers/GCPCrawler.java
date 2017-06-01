package org.occiware.clouddesigner.google2occi.handlers;

import java.util.List;

import static org.occiware.clouddesigner.google2occi.handlers.GCPCrawler.extension;
import static org.occiware.clouddesigner.google2occi.handlers.GCPCrawler.linkKind;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.cmf.occi.core.Action;
import org.eclipse.cmf.occi.core.Annotation;
import org.eclipse.cmf.occi.core.Attribute;
import org.eclipse.cmf.occi.core.BooleanType;
import org.eclipse.cmf.occi.core.Configuration;
import org.eclipse.cmf.occi.core.DataType;
import org.eclipse.cmf.occi.core.EObjectType;
import org.eclipse.cmf.occi.core.EnumerationLiteral;
import org.eclipse.cmf.occi.core.EnumerationType;
import org.eclipse.cmf.occi.core.NumericTypeEnum;
import org.eclipse.cmf.occi.core.Extension;
import org.eclipse.cmf.occi.core.Kind;
import org.eclipse.cmf.occi.core.Link;
import org.eclipse.cmf.occi.core.NumericType;
import org.eclipse.cmf.occi.core.OCCIFactory;
import org.eclipse.cmf.occi.core.OCCIPackage;
import org.eclipse.cmf.occi.core.RecordType;
import org.eclipse.cmf.occi.core.StringType;
import org.eclipse.cmf.occi.core.util.OcciHelper;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xml.type.util.XMLTypeUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GCPCrawler {
	public static final char separator = ';';
	public static final String noneToken = "None";
	private Resource resource;
	public static Extension extension;
	public static Extension coreExtension;
	private static Kind resourceKind;
	public static Kind linkKind;
	// type emf ecore.
	public final static StringType typeModelString = OCCIFactory.eINSTANCE.createStringType();
	public final static EObjectType typeModelDatetime = OCCIFactory.eINSTANCE.createEObjectType();
	public final static EObjectType typeModelMap = OCCIFactory.eINSTANCE.createEObjectType();
	public final static EObjectType typeModelUrl = OCCIFactory.eINSTANCE.createEObjectType();
	public final static BooleanType typeModelBoolean = OCCIFactory.eINSTANCE.createBooleanType();
	public final static NumericType typeModelByte = OCCIFactory.eINSTANCE.createNumericType();
	public final static NumericType typeModelInteger = OCCIFactory.eINSTANCE.createNumericType();
	public final static NumericType typeModelDouble = OCCIFactory.eINSTANCE.createNumericType();
	public final static NumericType typeModelFloat = OCCIFactory.eINSTANCE.createNumericType();
	public final static NumericType typeModelLong = OCCIFactory.eINSTANCE.createNumericType();
	public final static NumericType typeModelUnsignedLong = OCCIFactory.eINSTANCE.createNumericType();
	public final static Map<String, DataType> complexDatatypes = new HashMap<>();

	static {
		typeModelDatetime.setName("DateTime");

		typeModelMap.setName("Map");
		typeModelUrl.setName("URL");

		typeModelInteger.setType(NumericTypeEnum.INTEGER);
		typeModelDouble.setType(NumericTypeEnum.DOUBLE);
		typeModelFloat.setType(NumericTypeEnum.FLOAT);
		typeModelLong.setType(NumericTypeEnum.LONG);
		typeModelUnsignedLong.setType(NumericTypeEnum.LONG);
	}

	public GCPCrawler() {
		createOCCIResource();
	}

	private void createOCCIResource() {
		// Enregistrer l'extension ".xmi" comme devant �tre ouverte �
		// l'aide d'un objet "XMIResourceFactoryImpl"
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("xmi", new XMIResourceFactoryImpl());

		// Cr�er un objet resourceSetImpl qui contiendra une ressource EMF (le
		// mod�le)
		ResourceSet resSet = new ResourceSetImpl();

		// D�finir la ressource (le mod�le)
		URI modelURI = URI.createURI("file:/C:/Users/schallit-adm/runtime-EclipseApplication/models/GCP.occie");
		resource = resSet.createResource(modelURI);

		// La fabrique pour fabriquer les �l�ments de SimplePDL

		// Cr�er un �l�ment extension
		extension = OCCIFactory.eINSTANCE.createExtension();
		extension.setDescription("Mon extension GCP");
		extension.setScheme("http://gcp/occi#");
		extension.setName("GCP");
		resource.getContents().add(extension);

		// load core extension to get linkKind and resourceKind.
		coreExtension = OcciHelper.loadExtension("http://schemas.ogf.org/occi/core#");
		if (coreExtension == null) {
			throw new RuntimeException("Cannot load OCCI core extension!");
		}
		List<Kind> coreKinds = coreExtension.getKinds();
		for (Kind coreKind : coreKinds) {
			if (coreKind.getTerm().equals("link")) {
				linkKind = coreKind;
			}
			if (coreKind.getTerm().equals("resource")) {
				resourceKind = coreKind;
			}
		}
	}

	protected void saveOCCIResource() {
		try {
			resource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	protected static void crawler(Document doc) throws IOException {
		// To get the title from GCP website
		String title = doc.title();
		System.out.println("Title:" + title);

		// To get the list of all resources
		Elements divBodies1 = doc.getElementsByAttributeValue("itemprop", "articleBody");
		Element divBody1;
		divBody1 = divBodies1.get(0);
		Elements resources = divBody1.getElementsByTag("a");
		Elements resourcesNames = divBody1.select("code");

		for (int indexOfResource = 0; indexOfResource < resources.size() - 2; indexOfResource++) {
			Element resource = resources.get(indexOfResource);
			String kind = resourcesNames.get(indexOfResource).text();
			readResource(kind, resource, doc);
		}
		buildAbstractClasses();
	}

	private static void buildAbstractClasses() {
		Map<Attribute, List<Integer>> indexOfKindPerAttribute = new HashMap<>();
		for (int i = 0; i < extension.getKinds().size(); i++) {
			Kind kind = extension.getKinds().get(i);
			for (Attribute attribute : kind.getAttributes()) {
				if (!containsTheKey(indexOfKindPerAttribute.keySet(), attribute)) {
					indexOfKindPerAttribute.put(attribute, new ArrayList<Integer>());
				}
				get(indexOfKindPerAttribute, attribute).add(extension.getKinds().indexOf(kind));
			}
		}
		List<Attribute> keys = new ArrayList<>(indexOfKindPerAttribute.keySet());
		Collections.sort(keys, new Comparator<Attribute>() {
			public int compare(Attribute key1, Attribute key2) {
				return -(get(indexOfKindPerAttribute, key1).size() - get(indexOfKindPerAttribute, key2).size());
			}
		});
		int k = 0;
		List<List<Integer>> indicesAddedToAbstraction = new ArrayList<List<Integer>>();
		for (int i = 0; i < keys.size() - 1; i++) {
			Attribute key = keys.get(i);
			List<Integer> currentList = get(indexOfKindPerAttribute, key);
			if (currentList.size() < 2) {
				break;
			}
			if (!indicesAddedToAbstraction.contains(currentList)) {
				List<Attribute> commonAttributes = new ArrayList<>();
				commonAttributes.add(key);
				for (int j = i + 1; j < keys.size(); j++) {
					Attribute key2 = keys.get(j);
					if (currentList.equals(get(indexOfKindPerAttribute, key2))) {
						commonAttributes.add(key2);
					}
				}
				if (commonAttributes.size() > 15) {
					Kind abstractKind = OCCIFactory.eINSTANCE.createKind();
					abstractKind.setName("AbstractKind" + k++);
					for (Attribute commonAttribute : commonAttributes) {
						abstractKind.getAttributes().add(commonAttribute);
					}
					String descr = "";
					for (Integer index : get(indexOfKindPerAttribute, commonAttributes.get(0))) {
						descr += extension.getKinds().get(index).getName() + " ";
					}
					abstractKind.setTitle(descr);
					extension.getKinds().add(abstractKind);
				}
				indicesAddedToAbstraction.add(get(indexOfKindPerAttribute, commonAttributes.get(0)));
			}
		}
	}

	private static List<Integer> get(Map<Attribute, List<Integer>> indexOfKindPerAttribute, Attribute a) {
		for (Entry<Attribute, List<Integer>> entry : indexOfKindPerAttribute.entrySet()) {
			if (isSameAttribute(entry.getKey(), a)) {
				return entry.getValue();
			}
		}
		return null;
	}

	private static boolean containsTheKey(Set<Attribute> keys, Attribute a) {
		for (Attribute key : keys) {
			if (isSameAttribute(key, a)) {
				return true;
			}
		}
		return false;
	}

	// TODO getType return null, complete attribute types
	private static boolean isSameAttribute(Attribute a1, Attribute a2) {
		return a1.getName().equals(a2.getName())
				&& ((a1.getType() == null && a2.getType() == null) || (a1.getType() != null && a2.getType() != null
						&& a1.getType().getName().equals(a2.getType().getName())));
	}

	/**
	 * Read the kinds of resources
	 * 
	 * @param kind
	 * @param resource
	 *            block of a kind in html page.
	 * @param currentEnumtype
	 * @return
	 * @throws IOException
	 */
	protected static StringBuilder readResource(String kind, Element resource, Document kindsDoc) throws IOException {
		// Builder on enums
		StringBuilder esb = new StringBuilder();

		String res = resource.attr("href");
		if (res.length() > 0 && !res.contains("https://cloud.google.com/deployment-manager/docs/")) {
			if (res.length() < 4)
				res = kindsDoc.baseUri() + res.substring(1);
			else if (!res.substring(0, 4).equals("http"))
				res = kindsDoc.baseUri() + res.substring(1);
		} else {
			// Go to the next resource
			return new StringBuilder();
		}

		Document kindDoc = null;
		while (kindDoc == null) {
			try {
				kindDoc = Jsoup.connect(res).get();
			} catch (SocketTimeoutException e) {
				kindDoc = null;
			}
		}
		if (!kindDoc.select("table*[id=Version.FIELDS-table]").isEmpty())
			return readResourcePage(kindDoc, kind, esb, res, "table*[id=Version.FIELDS-table]");
		else
			return readResourcePage(kindDoc, kind, esb, res, "table*[id=properties]");
	}

	public static StringBuilder readResourcePage(Document kindDoc, String kind, StringBuilder esb, String res,
			String filter) throws IOException {
		// To get the list of all attributes from a resource
		List<Attribute> attributesModel = new LinkedList<Attribute>();
		List<Action> actionsModel = new LinkedList<Action>();
		List<EnumerationType> enumsModel = new LinkedList<EnumerationType>();

		GCPAttributeReader.readAttributes(kindDoc, kind, filter, esb, attributesModel, enumsModel);

		// To get all the actions
		// Elements attributeFilters1 =
		// kindDoc.select("table*[id=Version.FIELDS-table]");
		Elements actionsFilters1 = kindDoc.select("table*[id=METHODS_SUMMARY-table]");

		readActions(kindDoc, kind, "table*[id=METHODS_SUMMARY-table]", actionsModel);
		readActions(kindDoc, kind, "div*[itemprop=articleBody]", actionsModel);

		// save documentation format
		return saveDocumentationFormat(res, filter, actionsFilters1.isEmpty(), kind, actionsModel, attributesModel,
				enumsModel);
	}

	/**
	 * Read the actions of each kind
	 * 
	 * @param filter
	 * @param kind
	 *            name of the kind
	 * @return
	 */
	protected static StringBuilder readActions(final Document kindDoc, final String kind, final String filter,
			List<Action> actionsModel) {
		Elements actionFilters = kindDoc.select(filter);
		if ("table*[id=METHODS_SUMMARY-table]".equals(filter)) {
			return readActionsFilter1(kind, actionFilters, actionsModel);
		} else if ("div*[itemprop=articleBody]".equals(filter)) {
			return readActionsFilter2(kind, actionFilters, actionsModel);
		} else {
			throw new RuntimeException("Unknown filter");
		}
	}

	/**
	 * 
	 * @param kind
	 * @param actionFilters
	 * @param actionsModel
	 * @return
	 */
	protected static StringBuilder readActionsFilter1(final String kind, final Elements actionFilters,
			List<Action> actionsModel) {
		StringBuilder action = new StringBuilder();
		Elements actionRows = actionFilters.select("tbody").select("tr");
		for (Element actionRow : actionRows) {
			Elements actionInfos = actionRow.select("td");
			String currentActionName = "";
			String currentActionDescription = "";
			if (actionInfos != null && !actionInfos.isEmpty()) {
				Element actionName = actionInfos.select("h3").get(0);
				Element actionDescription = actionInfos.get(1);
				currentActionName = actionName.text();
				currentActionDescription = actionDescription.text();
				System.out.println("ActionKind:" + kind + ", ActionName:" + currentActionName + ", Description:"
						+ currentActionDescription);
				action.append(actioncsv(kind, currentActionName, currentActionDescription));

				Action ac = OCCIFactory.eINSTANCE.createAction();
				ac.setScheme("http://gcp/occi/" + currentActionName + "#");
				ac.setName(currentActionName);
				ac.setTitle(currentActionDescription);
				actionsModel.add(ac);
			}
		}
		return action;
	}

	/**
	 * 
	 * @param kind
	 * @param actionFilters
	 * @param actionsModel
	 * @return
	 */
	protected static StringBuilder readActionsFilter2(final String kind, final Elements actionFilters,
			List<Action> actionsModel) {
		StringBuilder action = new StringBuilder();
		Elements actionRows = actionFilters.select("dl");
		for (Element actionRow : actionRows) {
			Elements actionInfos = actionRow.select("dt").select("a");
			Elements actionDescription = actionRow.select("dd");
			String currentActionName = "";
			String currentActionDescription = "";
			if (actionInfos != null && !actionInfos.isEmpty()) {
				for (Element actionName : actionInfos) {
					currentActionName = actionName.text();
					currentActionDescription = actionDescription.get(actionInfos.indexOf(actionName)).text();
					System.out.println("ActionKind:" + kind + ", ActionName:" + currentActionName + ", Description:"
							+ currentActionDescription);
					action.append(actioncsv(kind, currentActionName, currentActionDescription));
					Action ac = OCCIFactory.eINSTANCE.createAction();
					ac.setName(currentActionName);
					ac.setTitle(currentActionDescription);
					actionsModel.add(ac);
				}
			}
		}
		return action;
	}

	protected static StringBuilder saveDocumentationFormat(String res, String attributeFormatUsed,
			boolean isActionFormat, String kind, List<Action> actions, List<Attribute> attributes,
			List<EnumerationType> enums) {
		if (!res.contains("https://cloud.google.com/deployment-manager/docs/")
				&& !res.contains("https://cloud.google.com/sdk/gcloud/reference/deployment-manager/types/list")) {
			String attributeFormat = "";
			String actionFormat = "";
			if (attributeFormatUsed.startsWith("table*[id=") && attributeFormatUsed.endsWith(".FIELDS-table]")) {
				attributeFormat = "attributesFilters1";
			} else {
				attributeFormat = "attributesFilters2";
			}
			if (isActionFormat) {
				actionFormat = "actionsFilters1";
			} else {
				actionFormat = "actionsFilters2";
			}
			System.out.println("Resource:" + kind + ", Documentation Format: " + attributeFormat + ";" + actionFormat);

			Kind k = null;
			for (Kind registerKind : extension.getKinds()) {
				if (kind.replace(".", "").equals(registerKind.getName())) {
					k = registerKind;
					break;
				}
			}

			if (k == null) {
				k = OCCIFactory.eINSTANCE.createKind();
				k.setScheme(extension.getScheme());
				k.setName(kind.replace(".", ""));
				k.setParent(resourceKind);
				extension.getKinds().add(k);
			}

			Annotation annotation = OCCIFactory.eINSTANCE.createAnnotation();
			annotation.setKey("attributeFilter");
			annotation.setValue(attributeFormat);
			k.getAnnotations().add(annotation);

			Annotation annotation2 = OCCIFactory.eINSTANCE.createAnnotation();
			annotation2.setKey("actionFilter");
			annotation2.setValue(actionFormat);
			k.getAnnotations().add(annotation2);

			// k.setTitle(attributeFormat + "," + actionFormat);
			k.getActions().addAll(actions);
			k.getAttributes().addAll(attributes);

			return resourcecsv(kind, attributeFormat + "_" + actionFormat);
		}
		return new StringBuilder();
	}

	public static StringBuilder attributecsv(String res, String currentconcept, String currenttype,
			String currentdescription) {

		StringBuilder sbRet = new StringBuilder();
		sbRet.append(res).append(separator).append(currentconcept).append(separator).append(currenttype)
				.append(separator).append(currentdescription).append(System.getProperty("line.separator"));
		return sbRet;
	}

	public static StringBuilder resourcecsv(String kind, String documentationformat) {

		StringBuilder sbRet = new StringBuilder();
		sbRet.append(kind).append(separator).append(documentationformat).append(System.getProperty("line.separator"));
		return sbRet;
	}

	public static StringBuilder actioncsv(String res, String name, String description) {

		StringBuilder sbRet = new StringBuilder();
		sbRet.append(res).append(separator).append(name).append(separator).append(description)
				.append(System.getProperty("line.separator"));
		return sbRet;
	}

	public static StringBuilder enumcsv(String res, String type, String literal, String description) {
		StringBuilder sbRet = new StringBuilder();
		sbRet.append(res).append(separator).append(type).append(separator).append(literal).append(separator)
				.append(description).append(System.getProperty("line.separator"));
		return sbRet;
	}

}
