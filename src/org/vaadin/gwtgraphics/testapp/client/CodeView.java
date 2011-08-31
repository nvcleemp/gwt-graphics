package org.vaadin.gwtgraphics.testapp.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.vaadin.gwtgraphics.client.VectorObject;

import com.google.gwt.user.client.ui.HTML;

public class CodeView extends HTML {

	private Metadata metadata;

	private Map<VectorObject, Map<String, String>> map = new LinkedHashMap<VectorObject, Map<String, String>>();

	private Map<VectorObject, Map<String, Integer>> addCountMap = new HashMap<VectorObject, Map<String, Integer>>();

	public CodeView(Metadata metadata) {
		super("");
		this.metadata = metadata;
		setStyleName("source-code");
	}

	public void addVectorObject(VectorObject vo, String name, String params) {
		if (!map.containsKey(vo)) {
			map.put(vo, new LinkedHashMap<String, String>());
		}

		map.get(vo).put(" = new " + getClassName(vo), params);
		refresh(vo);
	}

	private String getClassName(VectorObject vo) {
		String[] tmp = vo.getClass().getName().split("[.]");
		return tmp[tmp.length - 1];
	}

	public void addMethodCall(VectorObject vo, String name, String params,
			boolean addQuotes) {
		Map<String, String> m = map.get(vo);
		if (name.substring(0, 3).equals("set")) {
			name = "." + name;
		} else {
			name = "." + getAddName(vo, name);
		}
		if (params == null) {
			params = "null";
		} else if (addQuotes) {
			params = "\"" + params + "\"";
		}
		if (m != null) {
			m.remove(name);
			m.put(name, params);
			refresh(vo);
		}
	}

	private String getAddName(VectorObject vo, String name) {
		Map<String, Integer> map = addCountMap.get(vo);
		if (map == null) {
			map = new HashMap<String, Integer>();
			addCountMap.put(vo, map);
		}
		int count = -1;
		if (map.containsKey(name)) {
			count = map.get(name);
		}
		map.put(name, ++count);
		String countString = ((count < 10) ? "0" : "") + count;
		return "_" + countString + name;
	}

	public void addMethodCall(VectorObject vo, String name, String params) {
		addMethodCall(vo, name, params, true);
	}

	public void addMethodCall(VectorObject vo, String name, int param) {
		addMethodCall(vo, name, "" + param, false);
	}

	public void addMethodCall(VectorObject vo, String name, double param) {
		addMethodCall(vo, name, "" + param, false);
	}

	public void addMethodCall(VectorObject vo, String name) {
		addMethodCall(vo, name, "", false);
	}

	public void removeMethodCalls(VectorObject vo, String name) {
		Map<String, String> m = map.get(vo);
		if (m == null) {
			return;
		}
		if (name.substring(0, 3).equals("add")) {
			Map<String, Integer> map = addCountMap.get(vo);
			int count = 0;
			if (map != null && map.get(name) != null) {
				count = map.get(name);
				map.remove(name);
			}
			for (int i = 0; i <= count; i++) {
				String n = "._" + (((i < 10) ? "0" : "")) + i + name;
				m.remove(n);
			}

		} else {
			m.remove(name);
		}
	}

	public void refresh(VectorObject vo) {
		StringBuilder html = new StringBuilder();
		if (vo != null && map.containsKey(vo)) {
			String name = metadata.getName(vo);
			Iterator<Entry<String, String>> it = map.get(vo).entrySet()
					.iterator();
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String methodName = e.getKey();
				if (methodName.substring(0, 2).equals("._")) {
					methodName = "." + methodName.substring(4);
				}
				html.append(name).append(methodName).append("(")
						.append(e.getValue()).append(");<br>");
			}
		}
		setHTML(html.toString());
	}
}
