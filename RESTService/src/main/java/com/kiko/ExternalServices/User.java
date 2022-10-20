/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kiko.ExternalServices;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.xml.XMLConstants;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class User {
    
    public static final String usersXMLFile = "./webapps/DATA/users.xml";
    
    public static boolean checkUserID(String id, String pass) throws IOException, JDOMException 
    {   
        String fileName = usersXMLFile;
        String childrenName = "user";
        
        boolean loadUser = false;
        
        SAXBuilder sax = new SAXBuilder();

        // prevent xxe attacks
        sax.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        sax.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

        Document doc = sax.build(new File(fileName));
        Element rootNode = doc.getRootElement();
        
        //Get Children
        List<Element> listChildrenNode = rootNode.getChildren(childrenName);
                 
        // Loop Elements
        for (Element element : listChildrenNode) 
        {
            String userIDs = element.getAttribute("id").getValue();
            String password = element.getChildText("pass");
            
            if(userIDs.equals(id))
            {
                if(password.equals(pass))
                {
                    loadUser = true;
                }             
            }
        }
                
        return loadUser;
    }
}
