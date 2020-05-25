package li.bankfrick.informatik.reporting.csdr.properties;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

// Lädt Properties im Namespace "mapping" aus application.properties in Hashmap
@ConfigurationProperties("mapping")
public class MappingProperties {
	
	private Map<String, String> fininstr = new HashMap<>();
	private Map<String, String> txtype = new HashMap<>();
	private Map<String, String> clitype = new HashMap<>();
	
	// Laden des Namespace "mapping.fininstr" in Hashmap
    public Map<String, String> getFininstr() {
        return fininstr;
    }
	// Laden des Namespace "mapping.txtype" in Hashmap    
    public Map<String, String> getTxtype() {
        return txtype;
    }
 // Laden des Namespace "mapping.clitype" in Hashmap    
    public Map<String, String> getClitype() {
        return clitype;
    }
}

