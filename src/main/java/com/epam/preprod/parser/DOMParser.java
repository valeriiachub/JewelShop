package com.epam.preprod.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.epam.preprod.entity.enums.Role;

public class DOMParser {

    private static final String ROLE = "role";
    private static final String URL_PATTERN = "url-pattern";
    private static final String CONSTRAINT = "constraint";
    private String filePath;

    public DOMParser(String filePath) {
        this.filePath = filePath;
    }

    public Map<String, List<Role>> parseFile() throws ParserConfigurationException, IOException, SAXException {
        Map<String, List<Role>> roleUrls = new HashMap<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(new File(filePath));
        document.getDocumentElement().normalize();

        NodeList nodesList = document.getElementsByTagName(CONSTRAINT);
        parseElements(nodesList, roleUrls);

        return roleUrls;
    }

    private void parseElements(NodeList nodesList, Map<String, List<Role>> roleUrls) {
        for (int i = 0; i < nodesList.getLength(); i++) {
            Node node = nodesList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                String roleId = eElement.getElementsByTagName(ROLE).item(0).getTextContent();
                Role role = Role.values()[Integer.parseInt(roleId)];
                String urlConstraint = eElement.getElementsByTagName(URL_PATTERN).item(0).getTextContent();

                addRolesToList(role, urlConstraint, roleUrls);
            }
        }
    }

    private void addRolesToList(Role role, String urlConstraint, Map<String, List<Role>> roleUrls) {
        List<Role> roles = roleUrls.get(urlConstraint);
        if (Objects.isNull(roles)) {
            roles = new ArrayList<>();
            roles.add(role);
            roleUrls.put(urlConstraint, roles);
        } else {
            if (!roles.contains(role)) {
                roles.add(role);
            }
        }
    }
}


