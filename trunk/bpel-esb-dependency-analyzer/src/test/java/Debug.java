import java.io.File;

import com.tomecode.soa.oracle10g.bpel.BpelProject;
import com.tomecode.soa.oracle10g.parser.BpelParser;
import com.tomecode.soa.oracle10g.parser.ServiceParserException;

public class Debug {

	public static final void main(String[] arg) throws ServiceParserException {

		// new MultiWorkspaceParser().parse(new
		// File("c:/ORACLE/projects/BPEL/samples/"));

		// WsdlParser parser = new WsdlParser();
		// Wsdl wsdl = parser.parseWsdl(new
		// File("c:/ORACLE/projects/BPEL/samples/BPELProcess1/bpel/BPELProcess1.wsdl"));
		// Wsdl wsdl = parser.parseWsdl(new
		// File("c:/ORACLE/projects/BPEL/samples/BPELProcess1/bpel/NotificationService.wsdl"));
		// wsdl.getFile();
		BpelParser bpelParser = new BpelParser();
		BpelProject bpelProject = bpelParser.parseBpelXml(new File("c:/ORACLE/projects/BPEL/samples/BPELProcess1/"));
		bpelProject.toString();

		// // EsbParser esbParser = new EsbParser();
		// EsbProject esb = esbParser.parse(new
		// File("C:/download/ORACLE/SOA/esbsamples/ESBSamples/OTNSamples/101.EndpointProperties/EndpointProperties-project/Samples_101FileIn_RS.esbsvc"));
		// esb.toString();
	}
}
