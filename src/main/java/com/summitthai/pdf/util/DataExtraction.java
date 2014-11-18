package com.summitthai.pdf.util;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.fileupload.FileItemStream;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.AcroFields.Item;
import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.XfaForm;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

@Slf4j
public class DataExtraction implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public static ByteArrayOutputStream writePDFFile(FileItemStream item, String fileName) throws Exception {
        OutputStream output = null;
        int bytesRead = 0;
        byte[] buffer = null;
        String savePath = "B:/output/out.pdf";
        try {
            InputStream stream = item.openStream();

            output = new FileOutputStream(savePath);

            buffer = new byte[8192]; // 8192

            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

            while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
                output.write(buffer, 0, bytesRead);
                byteStream.write(buffer, 0, bytesRead);
            }
            byteStream.flush();

            output.close();

            return byteStream;
        } catch (Exception ex) {
            throw ex;
        } finally {
            output = null;
            buffer = null;
            savePath = null;
        }
    }
	
	public static ByteArrayOutputStream writePDFFile(InputStream ins) throws Exception {
        OutputStream output = null;
        byte[] buffer = null;
        String savePath = "B:/output/out.pdf";
        try {
            output = new FileOutputStream(savePath);
            buffer = new byte[8192]; // 8192

            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

            buffer = new byte[1024];

			for (int length = 0; (length = ins.read(buffer)) > -1;){
                output.write(buffer, 0, length);
                byteStream.write(buffer, 0, length);
            }
            byteStream.flush();

            output.close();

            return byteStream;
        } catch (Exception ex) {
            throw ex;
        } finally {
            output = null;
            buffer = null;
            savePath = null;
        }
    }

    public static void printAcroFormData(ByteArrayOutputStream byteStream) throws Exception {
        PdfReader reader;
        AcroFields fields;
        Map<String, Item> mapValue;
        Set<?> set;
        Iterator<?> it;
        try {
            reader = new PdfReader(byteStream.toByteArray());
            fields = reader.getAcroFields();
            mapValue = fields.getFields();

            set = mapValue.entrySet();
            it = set.iterator();

            while (it.hasNext()) {
                // key=value separator this by Map.Entry to get key and value
                Map.Entry m = (Map.Entry) it.next();

                DataExtraction.log.debug("key: " + m.getKey().toString()
                        + "  value: " + fields.getField(m.getKey().toString()));
            }

        } finally {
            reader = null;
            fields = null;
        }
    }
    
    public static void printXfaData(InputStream ins) throws Exception {
        PdfReader reader;
        AcroFields form;
        XfaForm xfa;
        Node node;
        NodeList list;
        try {
            reader = new PdfReader(ins);
            form = reader.getAcroFields();
            xfa = form.getXfa();
            
            DataExtraction.log.debug(xfa.isXfaPresent() ? "XFA form" : "AcroForm");
            
            node = xfa.getDatasetsNode();
            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.transform(new DOMSource(node), new StreamResult("B:/output/output.xfa"));
            
            //toHashMap(node);
            printNote(node.getChildNodes());
        } finally {
        	reader = null;
            form = null;
            xfa = null;
        }
    }
    
    public static void printXfaData(ByteArrayOutputStream byteStream) throws Exception {
        PdfReader reader;
        AcroFields form;
        XfaForm xfa;
        Node node;
        NodeList list;
        try {
            reader = new PdfReader(byteStream.toByteArray());
            form = reader.getAcroFields();
            xfa = form.getXfa();
            
            DataExtraction.log.debug(xfa.isXfaPresent() ? "XFA form" : "AcroForm");
            
            node = xfa.getDatasetsNode();
            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.transform(new DOMSource(node), new StreamResult("B:/output/output.xfa"));
            
            //toHashMap(node);
            printNote(node.getChildNodes());
        } finally {
        	reader = null;
            form = null;
            xfa = null;
        }
    }
    
    public static String convertXFAToXMLString(Node node) throws Exception{
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
    	Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        tf.transform(new DOMSource(node), new StreamResult(out));
        return new String(out.toByteArray(), "UTF-8");
    }
    
    public static void extractToHashMap(Node node, HashMap<String, String> hash){
    	NodeList list = node.getChildNodes();
    	int poi = 0;
    	
		while(list != null && list.getLength() > 0 && list.getLength() > poi){
			node = list.item(poi++);
			
			if(node.getChildNodes() != null && node.getChildNodes().getLength() > 0)
				extractToHashMap(node, hash);
			
			if (node.getNodeType() == Node.ELEMENT_NODE) {
	            DataExtraction.log.debug("type/name/value = " + node.getNodeType() + "/" + node.getNodeName() + "/" + node.getTextContent());
	            hash.put(node.getNodeName(), node.getTextContent());
			}
    	}
    }
    
	private static void printNote(NodeList nodeList) {

		for (int count = 0; count < nodeList.getLength(); count++) {

			Node tempNode = nodeList.item(count);

			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

				// get node name and value
				DataExtraction.log.debug("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
				DataExtraction.log.debug("Node Value =" + tempNode.getTextContent());

				if (tempNode.hasAttributes()) {

					// get attributes names and values
					NamedNodeMap nodeMap = tempNode.getAttributes();

					for (int i = 0; i < nodeMap.getLength(); i++) {

						Node node = nodeMap.item(i);
						DataExtraction.log.debug("attr name : " + node.getNodeName());
						DataExtraction.log.debug("attr value : " + node.getNodeValue());

					}

				}

				if (tempNode.hasChildNodes()) {

					// loop again if has child nodes
					printNote(tempNode.getChildNodes());

				}

				DataExtraction.log.debug("Node Name =" + tempNode.getNodeName() + " [CLOSE]");

			}

		}

	}

    /**
     * Extracts attachments from an existing PDF.
     * @param byteStream   ByteArrayOutputStream of PDF
     * @throws IOException
     */
    public static void extractAttachments(ByteArrayOutputStream byteStream) throws IOException {
        PdfReader reader = new PdfReader(byteStream.toByteArray());
        PdfArray array;
        PdfDictionary annot;
        PdfDictionary fs;
        PdfDictionary refs;
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            array = reader.getPageN(i).getAsArray(PdfName.ANNOTS);
            
            if (array == null) continue;
            for (int j = 0; j < array.size(); j++) {
                annot = array.getAsDict(j);
                
                if (PdfName.FILEATTACHMENT.equals(annot.getAsName(PdfName.SUBTYPE))) {   
                    fs = annot.getAsDict(PdfName.FS);
                    refs = fs.getAsDict(PdfName.EF);
                    
                    for (PdfName name : refs.getKeys()) {
                        FileOutputStream fos = new FileOutputStream(String.format("B:/output/%s", fs.getAsString(name).toString()));
                        fos.write(PdfReader.getStreamBytes((PRStream)refs.getAsStream(name)));
                        fos.flush();
                        fos.close();
                    }
                }
            }
        }
        
        reader.close();
    }
    
    public static void extractDocLevelAttachments(ByteArrayOutputStream byteStream) throws IOException {
        PdfReader reader = new PdfReader(byteStream.toByteArray());
        PdfDictionary root = reader.getCatalog();
        PdfDictionary names = root.getAsDict(PdfName.NAMES);
        PdfDictionary embedded = names.getAsDict(PdfName.EMBEDDEDFILES);
        
        if(embedded != null){
	        PdfArray filespecs = embedded.getAsArray(PdfName.NAMES);
	        for (int i = 0; i < filespecs.size();) {
	            filespecs.getAsString(i++);
	            extractAttachment(reader, filespecs.getAsDict(i++));
	        }
        }
    }

    protected static void extractAttachment(PdfReader reader, PdfDictionary filespec) throws IOException {
        PRStream stream;
        FileOutputStream fos;
        String filename;
        PdfDictionary refs = filespec.getAsDict(PdfName.EF);
        for (PdfName key : refs.getKeys()) {
            stream = (PRStream) PdfReader.getPdfObject(refs.getAsIndirectObject(key));
            filename = filespec.getAsString(key).toString();
            fos = new FileOutputStream(String.format("B:/output/%s", filename));
            fos.write(PdfReader.getStreamBytes(stream));
            fos.flush();
            fos.close();
        }
    }
    
    public void parsePdf(String pdf, String txt) throws IOException {
        PdfReader reader = new PdfReader(pdf);
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        PrintWriter out = new PrintWriter(new FileOutputStream(txt));
        TextExtractionStrategy strategy;
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
            out.println(strategy.getResultantText());
        }
        out.flush();
        out.close();
        reader.close();
    }
}
